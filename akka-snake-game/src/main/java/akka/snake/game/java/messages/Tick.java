package akka.snake.game.java.messages;

import java.io.Serializable;

public class Tick implements Serializable {
    private long time;

    public Tick() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Tick{" +
                "time=" + time +
                '}';
    }
}
