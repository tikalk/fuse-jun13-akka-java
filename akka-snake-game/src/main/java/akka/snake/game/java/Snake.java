/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.snake.game.java;

//#imports

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.event.EventStream;
import akka.snake.game.java.actors.ShutdownCoordinator;
import akka.snake.game.java.actors.GameMaster;
import akka.snake.game.java.messages.*;
import scala.concurrent.Future;

import java.util.ArrayList;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

//#app
public class Snake {

    ActorSystem system;
    ActorRef master;
    ActorRef coordinator;

    public static void main(String[] args) throws InterruptedException {
        Snake snake = new Snake();
        snake.init(0);

        //API tests
        for (int i = 0; i < 5; i++) {
            snake.register("User-" + i, "user" + i + "@tikalk.com");
        }

        snake.start();
        Thread.sleep(1000);
        snake.moveSnake("User-0", MoveSnake.Direction.DOWN);
        Thread.sleep(1000);
        snake.moveSnake("User-3", MoveSnake.Direction.LEFT);
        Thread.sleep(1000);
        snake.finish();
        Thread.sleep(1000);
//        snake.shutdownGracefully();
        snake.shutdown();
        Thread.sleep(1000);
    }


    private void shutdownGracefully() {
        final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
        //array of actors that can veto the shutdown decision
        futures.add(ask(master, new Result("shutdownGracefully"), 3000)); // using 1000ms timeout

        //sequence the futures
        Future<Iterable<Object>> aggregate = Futures.sequence(futures,system.dispatcher());

        //aggregate multiple results into once decision
        final Future<Result> transformed = aggregate.map(
                new Mapper<Iterable<Object>, Result>() {
                    public Result apply(Iterable<Object> coll) {
                        for (Object aColl : coll) {
                            Result next = (Result) aColl;
                            if (!next.isShutdown())
                                return new Result("shutdown", false);
                        }
                        return new Result("shutdown", true);
                    }
                }, system.dispatcher());

        //pip aggregated result to the coordinator
        pipe(transformed, system.dispatcher()).to(coordinator);
    }

    private void shutdown() {
        system.shutdown();
    }

    private void finish() {
        system.stop(master);
    }

    //init master and initial user-actors
    public void init(final int nrOfWorkers) {
        // Create an Akka system
        system = ActorSystem.create("SnakeGame");
        //system scheduler
        final Scheduler scheduler = system.scheduler();
        //get event stream
        final EventStream eventStream = system.eventStream();
        //create coordinator actor
        coordinator = system.actorOf(Props.create(new ShutdownCoordinator.CoordinatorCreator(system)), "coordinator");
        // create the master
        master = system.actorOf(Props.create(new GameMaster.MasterCreator(nrOfWorkers, scheduler, eventStream)), "master");

        //resigter event bus master messages
        eventStream.subscribe(master, Register.class);
        eventStream.subscribe(master, StartGame.class);
        eventStream.subscribe(master, SnakePosition.class);
    }

    public void moveSnake(String user, MoveSnake.Direction direction) {
        findActor(user).tell(direction, master);
    }

    //locate actor by name
    private ActorRef findActor(String name) {
        return system.actorFor("user/master/" + name);
    }

    // start the game
    public void start() {
        master.tell(new StartGame(), master);
    }

    //register new user
    public void register(String user, String email) {
        master.tell(new Register(user, email), master);
    }
}
//#app
