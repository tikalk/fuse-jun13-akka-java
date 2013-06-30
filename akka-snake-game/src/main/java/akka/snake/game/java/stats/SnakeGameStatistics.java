package akka.snake.game.java.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

		addUser(user);

		jedis.incr("snake:wins:" + user);
	}

	@Override
	public void addLose(final String user) {
		if (jedis == null) {
			if (!init()) {
				return;
			}
		}

		addUser(user);

		jedis.incr("snake:loses:" + user);
	}

	@Override
	public void addScore(final String user, final int score) {
		if (jedis == null) {
			if (!init()) {
				return;
			}
		}

		addUser(user);

		jedis.incrBy("snake:score:" + user, score);
	}

	private void addUser(final String user) {
		if (!jedis.sismember("snake:users", user)) {
			jedis.sadd("snake:users", user);
		}
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

		UserStats userStats = null;
		if (jedis.sismember("snake:users", user)) {
			final int wins = Integer.valueOf(jedis.get("snake:wins:" + user));
			final int loses = Integer.valueOf(jedis.get("snake:loses:" + user));
			final int score = Integer.valueOf(jedis.get("snake:score:" + user));
			userStats = new UserStats(wins, loses, score);
		} else {
			userStats = new UserStats(0, 0, 0);
		}

		return userStats;
	}

	@Override
	public List<UserStats> getAll() {
		if (jedis == null) {
			if (!init()) {
				return null;
			}
		}

		final List<UserStats> stats = new ArrayList<UserStats>();

		final Set<String> users = jedis.smembers("snake:users");
		for (final String user : users) {
			final int wins = Integer.valueOf(jedis.get("snake:wins:" + user));
			final int loses = Integer.valueOf(jedis.get("snake:loses:" + user));
			final int score = Integer.valueOf(jedis.get("snake:score:" + user));

			stats.add(new UserStats(wins, loses, score));
		}

		return stats;
	}

	public static void main(final String[] args) {
		final SnakeGameStatistics stats = new SnakeGameStatistics();
		final UserStats userStats = stats.get("hanan");

		System.out.println("game stats for user: hanan \nwins \t" + userStats.getWins() + "\nloses \t" + userStats.getLoses() + "\ntotal score \t" + userStats.getTotalScore());
	}
}
