package akka.snake.game.java.messages;

import java.io.Serializable;
import java.util.UUID;

public class TerminateUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8398488247347884921L;

	private final UUID userId;

	public TerminateUser(final UUID userId) {
		this.userId = userId;
	}

	public UUID getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "TerminateUser{" + "userId=" + userId + '}';
	}
}
