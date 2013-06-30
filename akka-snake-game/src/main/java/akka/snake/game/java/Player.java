package akka.snake.game.java;

public class Player {
    private String name;
    private boolean alive;

    private infrastructure.Snake snake;

    public Player() {
        alive =true;
    }

    public infrastructure.Snake getSnake() {
        return snake;
    }

    public void setSnake(infrastructure.Snake snake) {
        this.snake = snake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
