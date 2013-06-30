package infrastructure;

/**
 * Created with IntelliJ IDEA.
 * User: noam
 * Date: 6/30/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class SnakeHead implements BoardElement {
    private final Snake snake;

    public SnakeHead(Snake snake) {
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }
}
