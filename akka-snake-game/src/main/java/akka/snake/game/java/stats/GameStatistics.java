package akka.snake.game.java.stats;

import java.util.List;

import akka.snake.game.java.GameData;

public interface GameStatistics {

	void addWin(String user);

	void addLose(String user);

	void addScore(String user, int score);

	void addGameDataStats(GameData data);

	UserStats get(String user);

	List<UserStats> getAll();

}
