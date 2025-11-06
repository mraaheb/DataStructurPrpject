

/**
 *
 * @author mraah
 */
/**
 * This helper class represents a single "node" in the linked list.
 * @param <T> The type of data this node will hold (e.g., Product, Review).
 */
public class Node<T> {
    
    // 1. The actual data stored in this node.
    T data;
    
    // 2. The pointer (reference) to the next node in the chain.
    Node<T> next;

    /**
     * Constructor to create a new node.
     * @param data The data to be stored.
     */
    public Node(T data) {
        this.data = data;
        this.next = null; // Initially, it doesn't point to anything.
    }
}