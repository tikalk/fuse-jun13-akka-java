package akka.snake.game.java.messages;

import java.awt.*;
import java.io.Serializable;

import akka.snake.game.java.messages.MoveSnake.Direction;

public class SnakePosition implements Serializable {
    private Register user;
    private Point position;
    private int length;
    private Direction direction;

    public SnakePosition(Register user,Point position, int length, Direction direction) {
        this.user = user;
        this.position = position;
        this.length = length;
        this.direction= direction;
    }

    public Point getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public Register getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "SnakePosition{" +
                "user=" + user +
                ", position=" + position +
                ", length=" + length +
                '}';
    }
}
