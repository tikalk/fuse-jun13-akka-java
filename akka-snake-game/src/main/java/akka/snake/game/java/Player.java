package akka.snake.game.java;


public class Player {
    private String name;
    private boolean isAlive;

    private infrastructure.Snake snake;

    public Player() {
        isAlive=true;
    }

    public infrastructure.Snake getSnake() {
        return snake;
    }

    public void setSnake(infrastructure.Snake snake) {
        this.snake = snake;
    }
}
