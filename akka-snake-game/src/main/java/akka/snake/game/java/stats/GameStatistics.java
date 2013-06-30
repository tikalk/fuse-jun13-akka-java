package akka.snake.game.java.stats;

public interface GameStatistics {

	void addWin(String user);

	void addLose(String user);

	void addTimeSpent(int seconds);

}
