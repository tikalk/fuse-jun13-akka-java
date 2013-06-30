package akka.snake.game.java;

import java.util.List;
import java.util.Map;

public interface SnakeLogic {

	GameData init(List<Player> players);

	GameData nextStep(GameData prevData, Map<Player, String> moves);

}
