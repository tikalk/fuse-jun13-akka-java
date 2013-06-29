package akka.snake.game.java.messages;

import java.io.Serializable;
import java.util.UUID;

public class Register implements Serializable{
    private String name;
    private String mail;
    private UUID userId;

    public Register(String name, String mail) {
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
        return "Register{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", userId=" + userId +
                '}';
    }
}
