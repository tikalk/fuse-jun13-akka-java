package infrastructure;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: noam
 * Date: 6/30/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameData {
    private final Board board;
    private final Set<Snake> snakes;
    private final Set<Fruit> fruits;

    public GameData(Board board, Set<Snake> snakes, Set<Fruit> fruits) {
        this.board = board;
        this.snakes = snakes;
        this.fruits = fruits;
    }

    public Board getBoard() {
        return board;
    }

    public Set<Snake> getSnakes() {
        return snakes;
    }

    public Set<Fruit> getFruits() {
        return fruits;
    }
}
