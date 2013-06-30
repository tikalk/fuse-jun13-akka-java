package infrastructure;

/**
 * Created with IntelliJ IDEA.
 * User: noam
 * Date: 6/30/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SnakeBody implements BoardElement {
    private final Direction directionTowardsTheHead;

    public SnakeBody(Direction previousBodyElement) {
        this.directionTowardsTheHead = previousBodyElement;
    }

    public Direction getDirectionTowardsTheHead() {
        return directionTowardsTheHead;
    }
}
