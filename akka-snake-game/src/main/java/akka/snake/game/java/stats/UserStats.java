package akka.snake.game.java.stats;

public class UserStats {

	private final int wins;
	private final int loses;
	private final int totalScore;

	public UserStats(final int wins, final int loses, final int totalScore) {
		super();
		this.wins = wins;
		this.loses = loses;
		this.totalScore = totalScore;
	}

	public int getWins() {
		return wins;
	}

	public int getLoses() {
		return loses;
	}

	public int getTotalScore() {
		return totalScore;
	}
}
