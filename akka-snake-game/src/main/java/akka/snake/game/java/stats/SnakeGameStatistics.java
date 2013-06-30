package akka.snake.game.java.stats;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import akka.snake.game.java.GameData;

public class SnakeGameStatistics implements GameStatistics {

	private Jedis jedis;

	public SnakeGameStatistics() {

	}

	private boolean init() {
		try {
			jedis = new Jedis("localhost");
		} catch (final JedisConnectionException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public void addWin(final String user) {
		if (jedis == null) {
			if (!init()) {
				return;
			}
		}

		jedis.incr("snake:wins:" + user);
	}

	@Override
	public void addLose(final String user) {
		if (jedis == null) {
			if (!init()) {
				return;
			}
		}

		jedis.incr("snake:loses:" + user);
	}

	@Override
	public void addScore(final String user, final int score) {
		if (jedis == null) {
			if (!init()) {
				return;
			}
		}

		jedis.incrBy("snake:score:" + user, score);
	}

	@Override
	public void addGameDataStats(final GameData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserStats get(final String user) {
		if (jedis == null) {
			if (!init()) {
				return null;
			}
		}

		final int wins = Integer.valueOf(jedis.get("snake:wins:" + user) == null ? "0" : jedis.get("snake:wins:" + user));
		final int loses = Integer.valueOf(jedis.get("snake:loses:" + user) == null ? "0" : jedis.get("snake:loses:" + user));
		final int score = Integer.valueOf(jedis.get("snake:score:" + user) == null ? "0" : jedis.get("snake:score:" + user));

		return new UserStats(wins, loses, score);
	}

	public static void main(final String[] args) {
		final SnakeGameStatistics stats = new SnakeGameStatistics();
		final UserStats userStats = stats.get("hanan");

		System.out.println("game stats for user: hanan \nwins \t" + userStats.getWins() + "\nloses \t" + userStats.getLoses() + "\ntotal score \t" + userStats.getTotalScore());
	}
}
