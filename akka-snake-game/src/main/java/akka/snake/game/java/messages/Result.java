package akka.snake.game.java.messages;

import java.io.Serializable;

public class Result implements Serializable {
    private String message;
    private boolean shutdown;

    public Result(String message) {
        //To change body of created methods use File | Settings | File Templates.
        this.message = message;
    }

    public Result(String message,boolean shutdown) {
        //To change body of created methods use File | Settings | File Templates.
        this.message = message;
        this.shutdown = shutdown;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", shutdown=" + shutdown +
                '}';
    }
}
