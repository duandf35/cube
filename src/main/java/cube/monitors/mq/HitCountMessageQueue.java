package cube.monitors.mq;

import com.google.common.base.Preconditions;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Message queue to contain hit count information.
 *
 * @author Wenyu
 * @since 1/28/16
 */
public final class HitCountMessageQueue implements MessageQueue<String> {

    private static final Queue<String> Q = new LinkedList<>();

    public HitCountMessageQueue() {

    }

    @Override
    public void enqueue(String message) {
        Preconditions.checkNotNull(message, "message must not be null.");
        Q.add(message);
    }

    @Override
    public String dequeue() {
        return Q.poll();
    }

    @Override
    public boolean hasNext() {
        return null != Q.peek();
    }
}
