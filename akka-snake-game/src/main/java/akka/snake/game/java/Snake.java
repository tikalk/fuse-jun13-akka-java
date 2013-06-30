/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.snake.game.java;

//#imports

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.event.EventStream;
import akka.snake.game.java.actors.GameMaster;
import akka.snake.game.java.messages.MoveSnake;
import akka.snake.game.java.messages.Register;
import akka.snake.game.java.messages.SnakePosition;
import akka.snake.game.java.messages.StartGame;
import akka.snake.game.java.messages.Tick;
import akka.snake.game.java.messages.UnRegister;

//#app
public class Snake implements SnakeApi {

	ActorSystem system;
	ActorRef master;
	ActorRef coordinator;
	private final SnakeCallback callback;

	public static void main(final String[] args) throws InterruptedException {
		final Snake snake = new Snake(null);
		snake.init();

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


	public Snake(final SnakeCallback callback) {
		this.callback = callback;
		init();
	}


	private void shutdown() {
		system.shutdown();
	}

	private void finish() {
		system.stop(master);
	}

	// init master and initial user-actors
	public void init() {
		// Create an Akka system
		system = ActorSystem.create("SnakeGame");
		// system scheduler
		final Scheduler scheduler = system.scheduler();
		// get event stream
		final EventStream eventStream = system.eventStream();
		// create coordinator actor
//		coordinator = system.actorOf(Props.create(new ShutdownCoordinator.CoordinatorCreator(system)), "coordinator");
		// create the master

		master = system.actorOf(Props.create(new GameMaster.MasterCreator(scheduler, eventStream, callback)), "master");

		// resigter event bus master messages
		eventStream.subscribe(master, Register.class);
		eventStream.subscribe(master, StartGame.class);
		eventStream.subscribe(master, Tick.class);
	}

	private void moveSnake(final String user, final MoveSnake.Direction direction) {
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
	public void move(final String user, final String direction) {
		moveSnake(user, MoveSnake.Direction.valueOf(direction));
	}

	@Override
	public void startGame() {
		init();
		master.tell(new StartGame(), master);
	}

	@Override
	public void endGame() {
		finish();
		shutdown();
	}

	public static SnakeApi registerUICallback(final SnakeCallback callback) {
		return new Snake(callback);
	}
}
// #app
