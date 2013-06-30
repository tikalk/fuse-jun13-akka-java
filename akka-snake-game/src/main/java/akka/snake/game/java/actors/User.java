package akka.snake.game.java.actors;

import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.EventStream;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import akka.snake.game.java.messages.*;

import java.awt.*;

public class User extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public static final int SNAKE_GROWTH_RATE = 3000;

    private final Register register;
    private final EventStream eventStream;

    private MoveSnake.Direction direction;
    private Point location;
    private int snakeLength;
    private long lastTick;

    public User(Register register, EventStream eventStream) {
        this.register = register;
        this.eventStream = eventStream;
        this.location = new Point();
        this.snakeLength = 1;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("user " + getSelf().path() + " receive message [" + message + "] ");
        if (message instanceof MoveSnake.Direction) {
            this.direction = (MoveSnake.Direction) message;
        } else if (message instanceof TerminateUser) {
            //todo check uuid against registered user
            // Stops this actor and all its supervised children
            getContext().stop(getSelf());
        } else if (message instanceof Tick) {
//            handleSnakeSize((Tick) message);

            eventStream.publish(new SnakePosition(register,location,snakeLength));
/*
            getSender().tell(direction, getSelf());
            direction = null;
*/
        } else if (message instanceof Terminated) {
            getContext().stop(getSelf());
            log.info("user " + getSelf().path() + " Terminated ");
        } else {
            unhandled(message);
        }
    }

    private void handleSnakeSize(Tick tick) {
        if(lastTick- tick.getTime() >  SNAKE_GROWTH_RATE) {
            snakeLength++;
            lastTick = tick.getTime();
        }
    }

    public Point getLocation() {
        return location;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    //actor factory
    static class UserCreator implements Creator<User> {
        Register register;
        EventStream eventStream;

        public UserCreator(Register register, EventStream eventStream) {
            this.register = register;
            this.eventStream = eventStream;
        }

        @Override public User create() {
            return new User(register,eventStream);
        }
    }}
