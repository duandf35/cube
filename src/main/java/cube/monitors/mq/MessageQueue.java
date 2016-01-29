package cube.monitors.mq;

/**
 * FIFO message queue.
 *
 * @author Wenyu
 * @since 1/28/16
 */
public interface MessageQueue<T> {

    /**
     * Put object into the queue.
     * @param t the object
     */
    void enqueue(T t);

    /**
     * Get one object from the queue.
     * @return the object
     */
    T dequeue();

    /**
     * Check if the queue contains message.
     * @return true if contains message
     */
    boolean hasNext();
}
