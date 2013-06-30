package akka.snake.game.java;

import infrastructure.*;
import infrastructure.Snake;

public class Player {
    private boolean isAlive;

    private infrastructure.Snake snake;

    public Player() {
        isAlive=true;
    }


    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}
