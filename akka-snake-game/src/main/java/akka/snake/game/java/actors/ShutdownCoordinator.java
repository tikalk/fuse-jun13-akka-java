package akka.snake.game.java.actors;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import akka.snake.game.java.messages.Result;

public class ShutdownCoordinator extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorSystem system;

    public ShutdownCoordinator(ActorSystem system) {
        //To change body of created methods use File | Settings | File Templates.
        this.system = system;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("message " + message + " from sender " + getSender());
        if (message instanceof Result) {
            if(((Result) message).isShutdown())
                system.shutdown();
        } else {
            unhandled(message);
        }
    }

    public static class CoordinatorCreator implements Creator<ShutdownCoordinator> {
        ActorSystem system;

        public CoordinatorCreator(ActorSystem system) {
            this.system = system;
        }

        /**
         * This method must return a different instance upon every call.
         */
        @Override
        public ShutdownCoordinator create() throws Exception {
            return new ShutdownCoordinator(system);
        }
    }
}
