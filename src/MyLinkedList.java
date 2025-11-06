
/**
 *
 * @author mraah
 */
/**
 * Our custom implementation of a Linked List data structure.
 * @param <T> The type of data this list will store.
 */
public class MyLinkedList<T> {

    // 1. A pointer to the first node (the "head" of the list).
    private Node<T> head;
    
    // 2. A pointer to the last node (the "tail") for efficient appends.
    private Node<T> tail;
    
    // 3. A variable to keep track of the number of elements (the size).
    private int size;

    /**
     * Constructor to create a new, empty linked list.
     */
    public MyLinkedList() {
        this.head = null; // No head yet
        this.tail = null; // No tail yet
        this.size = 0;    // Size is zero
    }

    /**
     * Adds a new element to the *end* of the list.
     * (Time Complexity: O(1) - because we use a 'tail' pointer)
     * @param data The data to be added.
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data); // 1. Create the new node

        if (isEmpty()) {
            // 2. If the list is empty: the head and tail are the new node
            head = newNode;
            tail = newNode;
        } else {
            // 3. If not empty: link the old tail to the new node
            tail.next = newNode;
            // 4. Update the tail to be the new node
            tail = newNode;
        }
        
        size++; // 5. Increment the size
    }

    /**
     * Retrieves (reads) an element based on its position (index).
     * (Time Complexity: O(N) - as we might need to traverse the list)
     * @param index The position (starts from 0).
     * @return The data at that position.
     */
    public T get(int index) {
        // 1. Check if the index is valid and within bounds
        if (index < 0 || index >= size) {
            System.err.println("Error: Index out of bounds: " + index);
            return null; 
        }

        // 2. Start traversing from the head
        Node<T> current = head;
        
        // 3. Loop (traverse) "index" times
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        // 4. Return the data from the node we landed on
        return current.data;
    }

    /**
     * Removes an element based on its position (index).
     * (Time Complexity: O(N) - as we might need to traverse)
     * @param index The position (starts from 0).
     * @return true if the removal was successful.
     */
    public boolean remove(int index) {
        // 1. Check if the index is valid
        if (index < 0 || index >= size) {
            return false; // Removal failed
        }

        // 2. Special case: removing the head (the first element)
        if (index == 0) {
            head = head.next; // The new head is the second element
            // If the list became empty, update the tail as well
            if (head == null) {
                tail = null;
            }
        } else {
            // 3. General case: removing from the middle or end
            Node<T> current = head;
            
            // Traverse until we reach the node *before* the one we want to remove
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            
            // The node we want to remove is (current.next)
            Node<T> nodeToRemove = current.next;
            
            // "Skip" over the node to be removed
            current.next = nodeToRemove.next;
            
            // 4. Special case: if we just removed the tail
            if (current.next == null) {
                tail = current; // The new tail is "current"
            }
        }

        size--; // 5. Decrement the size
        return true; // Removal successful
    }

    /**
     * Returns the number of elements in the list.
     * (Time Complexity: O(1))
     * @return The size (int).
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks if the list is empty.
     * (Time Complexity: O(1))
     * @return true if the list is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }
}
