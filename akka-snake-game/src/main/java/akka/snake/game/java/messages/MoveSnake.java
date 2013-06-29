package akka.snake.game.java.messages;

import java.io.Serializable;

public class MoveSnake implements Serializable{
    private Direction direction;

    public MoveSnake(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "MoveSnake{" +
                "direction=" + direction +
                '}';
    }

    public static enum Direction {
        UP,DOWN,LEFT,RIGHT
    }
}
