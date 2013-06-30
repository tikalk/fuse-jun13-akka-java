package infrastructure;

import akka.snake.game.java.Player;

/**
 * Created with IntelliJ IDEA.
 * User: noam
 * Date: 6/30/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Snake {
    private Point head;
    private Point tail;
    private Direction direction;
    private final Player player;

    public Snake(Player player) {
        this.player = player;
    }

    public Point getHead() {
        return head;
    }

    public void setHead(Point head) {
        this.head = head;
    }

    public Point getTail() {
        return tail;
    }

    public void setTail(Point tail) {
        this.tail = tail;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Player getPlayer() {
        return player;
    }
}
