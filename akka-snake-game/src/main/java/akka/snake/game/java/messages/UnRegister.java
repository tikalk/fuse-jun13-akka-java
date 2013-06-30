package akka.snake.game.java.messages;

import java.io.Serializable;
import java.util.UUID;

public class UnRegister implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6247500481372976207L;

	private final String name;
	private String mail;
	private final UUID userId;

	public UnRegister(final String name) {
		this.name = name;
		userId = UUID.randomUUID();
	}

	public String getName() {
		return name;
	}

	public String getMail() {
		return mail;
	}

	public UUID getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "Register{" + "name='" + name + '\'' + ", mail='" + mail + '\'' + ", userId=" + userId + '}';
	}
}
