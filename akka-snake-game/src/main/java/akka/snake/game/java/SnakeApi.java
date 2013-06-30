package akka.snake.game.java;

public interface SnakeApi {

	public void register(String user, String email);

	public void unregister(String user);

	public void move(String user, String direction);

	public void startGame();

	public void endGame();

}
