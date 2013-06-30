package akka.snake.game.java.actors;

import java.util.concurrent.TimeUnit;

import scala.collection.parallel.ParSeqLike.Updated;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.EventStream;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import akka.snake.game.java.SnakeCallback;
import akka.snake.game.java.SnakeLogic;
import akka.snake.game.java.messages.Register;
import akka.snake.game.java.messages.SnakePosition;
import akka.snake.game.java.messages.StartGame;
import akka.snake.game.java.messages.Tick;
import akka.snake.game.java.messages.UnRegister;

public class GameMaster extends UntypedActor {// #master
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

//	private final int nrOfCustomers;
	private long start;
	private long end;

	private Cancellable cancellable;
	private final Scheduler scheduler;
	private final EventStream eventStream;
	private SnakeLogic snakeLogic;


	private final SnakeCallback callback;


	public GameMaster(final EventStream eventStream, final Scheduler scheduler,SnakeCallback callback) {
//		this.nrOfCustomers = nrOfCustomers;
		this.eventStream = eventStream;
		this.scheduler = scheduler;
		this.callback = callback;

//		for (int i = 0; i < nrOfCustomers; i++) {
//			createUser(new Register("user-" + i, "user-" + i + ""));
//		}
	}

	private void createUser(final Register register) {
		final Props props3 = Props.create(new User.UserCreator(register, eventStream));
		final ActorRef ref = this.getContext().actorOf(props3, register.getName());
		// subscribe to event stream
		eventStream.subscribe(ref, StartGame.class);
		eventStream.subscribe(ref, Tick.class);
	}

	private void deleteUser(final UnRegister message) {
		final ActorRef ref = getContext().actorFor("user/master/" + message.getName());
		getContext().stop(ref);
	}

	// #master-receive
	@Override
	public void onReceive(final Object message) {
		log.info("Master " + " receive message [" + message + "] from " + getSender().path());
		// #handle-messages
		if (message instanceof StartGame) {
			start = System.currentTimeMillis();
			eventStream.publish(message);
//			for (int start = 0; start < nrOfCustomers; start++) {
//				// workerRouter.tell(new Work(start, nrOfElements), getSelf());
//				eventStream.publish(message);
//			}
			// schedule game tick heart beat
			//TODO: Call Scala Start Game
//			snakeLogic.init(players)
			scheduleTick();
		} else if (message instanceof Register) {
			createUser((Register) message);
		} else if (message instanceof UnRegister) {
			deleteUser((UnRegister) message);
		} else if (message instanceof SnakePosition) {
			// callback.handleData(data);
			// todo handle the message
		} else if (message instanceof Terminated) {
			finish();
//		} else if (message instanceof Result) {
//			try {
//				// todo some final snake grid calculation
//				getSender().tell(new Result("shutdown", true), getSelf());
//			} catch (final Exception e) {
//				getSender().tell(new akka.actor.Status.Failure(e), getSelf());
//			}
		} else {
			unhandled(message);
		}
		// #handle-messages
	}

	private void scheduleTick() {
		// This will schedule to send the Tick-message
		cancellable = scheduler.schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(500, TimeUnit.MILLISECONDS), new Runnable() {
			@Override
			public void run() {
				eventStream.publish(new Tick());
			}
		}, getContext().dispatcher());
	}

	private void finish() {
		end = System.currentTimeMillis();
		// This cancels further Ticks to be sent
		cancellable.cancel();

		// Stops this actor and all its supervised children
		getContext().stop(getSelf());
	}

	// #master-receive
	public static class MasterCreator implements Creator<GameMaster> {
		private final Scheduler scheduler;
		private final EventStream eventStream;
		private final SnakeCallback callback;

		public MasterCreator(final Scheduler scheduler, final EventStream eventStream, final SnakeCallback callback) {
			this.scheduler = scheduler;
			this.eventStream = eventStream;
			this.callback = callback;
		}

		/**
		 * This method must return a different instance upon every call.
		 */
		@Override
		public GameMaster create() throws Exception {
			return new GameMaster(eventStream, scheduler, callback);
		}
	}
}
