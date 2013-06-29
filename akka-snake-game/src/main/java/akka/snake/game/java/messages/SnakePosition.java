package akka.snake.game.java.messages;

import java.awt.*;
import java.io.Serializable;

public class SnakePosition implements Serializable {
    private Register user;
    private Point position;
    private int length;

    public SnakePosition(Register user,Point position, int length) {
        this.user = user;
        this.position = position;
        this.length = length;
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
