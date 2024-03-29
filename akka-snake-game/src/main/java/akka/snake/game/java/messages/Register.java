package akka.snake.game.java.messages;

import java.io.Serializable;
import java.util.UUID;

public class Register implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1147363136244878248L;

	private final String name;
	private final String mail;
	private final UUID userId;

	public Register(final String name, final String mail) {
		this.name = name;
		this.mail = mail;
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
