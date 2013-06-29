package akka.snake.game.java.messages;

import java.io.Serializable;
import java.util.UUID;

public class TerminateUser implements Serializable {
    private UUID userId;

    public TerminateUser(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "TerminateUser{" +
                "userId=" + userId +
                '}';
    }
}
