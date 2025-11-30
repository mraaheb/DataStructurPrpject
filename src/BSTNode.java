/**
 * CSC 212 Project - Phase 2
 * Class: BSTNode
 * * Represents a single node in the Binary Search Tree (BST).
 * * @param <T> The type of data stored (must be Comparable to allow sorting).
 */
public class BSTNode<T extends Comparable<T>> {
    
    // --- Attributes ---
    protected T el;              // The data element (matches slide notation)
    protected BSTNode<T> left;   // Pointer to the left child (smaller elements)
    protected BSTNode<T> right;  // Pointer to the right child (larger elements)
    protected int height;        // Node height (Prepared for AVL balancing logic)

    // --- Constructors ---

    /**
     * Default Constructor: Creates an empty node.
     */
    public BSTNode() {
        left = right = null;
        height = 0;
    }

    /**
     * Constructor: Creates a leaf node with data.
     * @param el The data to store.
     */
    public BSTNode(T el) {
        this(el, null, null);
        height = 0;
    }

    /**
     * Constructor: Creates a node with data and children references.
     * @param el The data to store.
     * @param lt Reference to the left child.
     * @param rt Reference to the right child.
     */
    public BSTNode(T el, BSTNode<T> lt, BSTNode<T> rt) {
        this.el = el;
        left = lt;
        right = rt;
        height = 0;
    }
}