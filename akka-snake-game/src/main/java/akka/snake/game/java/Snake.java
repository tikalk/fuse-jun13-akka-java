/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.snake.game.java;

//#imports

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import java.util.ArrayList;

import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.event.EventStream;
import akka.snake.game.java.actors.GameMaster;
import akka.snake.game.java.actors.ShutdownCoordinator;
import akka.snake.game.java.messages.MoveSnake;
import akka.snake.game.java.messages.Register;
import akka.snake.game.java.messages.Result;
import akka.snake.game.java.messages.SnakePosition;
import akka.snake.game.java.messages.StartGame;
import akka.snake.game.java.messages.UnRegister;

//#app
public class Snake implements SnakeApi {

	ActorSystem system;
	ActorRef master;
	ActorRef coordinator;

	public static void main(final String[] args) throws InterruptedException {
		final Snake snake = new Snake();
		snake.init(0);

		// API tests
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
		// snake.shutdownGracefully();
		snake.shutdown();
		Thread.sleep(1000);
	}

	private void shutdownGracefully() {
		final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
		// array of actors that can veto the shutdown decision
		futures.add(ask(master, new Result("shutdownGracefully"), 3000)); // using
																			// 1000ms
																			// timeout

		// sequence the futures
		final Future<Iterable<Object>> aggregate = Futures.sequence(futures, system.dispatcher());

		// aggregate multiple results into once decision
		final Future<Result> transformed = aggregate.map(new Mapper<Iterable<Object>, Result>() {
			@Override
			public Result apply(final Iterable<Object> coll) {
				for (final Object aColl : coll) {
					final Result next = (Result) aColl;
					if (!next.isShutdown()) {
						return new Result("shutdown", false);
					}
				}
				return new Result("shutdown", true);
			}
		}, system.dispatcher());

		// pip aggregated result to the coordinator
		pipe(transformed, system.dispatcher()).to(coordinator);
	}

	private void shutdown() {
		system.shutdown();
	}

	private void finish() {
		system.stop(master);
	}

	// init master and initial user-actors
	public void init(final int nrOfWorkers) {
		// Create an Akka system
		system = ActorSystem.create("SnakeGame");
		// system scheduler
		final Scheduler scheduler = system.scheduler();
		// get event stream
		final EventStream eventStream = system.eventStream();
		// create coordinator actor
		coordinator = system.actorOf(Props.create(new ShutdownCoordinator.CoordinatorCreator(system)), "coordinator");
		// create the master
		master = system.actorOf(Props.create(new GameMaster.MasterCreator(nrOfWorkers, scheduler, eventStream)), "master");

		// resigter event bus master messages
		eventStream.subscribe(master, Register.class);
		eventStream.subscribe(master, StartGame.class);
		eventStream.subscribe(master, SnakePosition.class);
	}

	public void moveSnake(final String user, final MoveSnake.Direction direction) {
		findActor(user).tell(direction, master);
	}

	// locate actor by name
	private ActorRef findActor(final String name) {
		return system.actorFor("user/master/" + name);
	}

	// start the game
	public void start() {
		master.tell(new StartGame(), master);
	}

	// register new user
	@Override
	public void register(final String user, final String email) {
		master.tell(new Register(user, email), master);
	}

	@Override
	public void unregister(final String user) {
		master.tell(new UnRegister(user), master);
	}

	@Override
	public void move(final String direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

	}
}
// #app
