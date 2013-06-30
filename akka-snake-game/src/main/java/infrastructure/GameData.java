package infrastructure;

import akka.snake.game.java.Player;

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
    private final Set<Player> players;
    private final Set<Fruit> fruits;

    public GameData(Board board, Set<Player> players, Set<Fruit> fruits) {
        this.board = board;
        this.players = players;
        this.fruits = fruits;
    }

    public Board getBoard() {
        return board;
    }

    public Set<Fruit> getFruits() {
        return fruits;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
