/**
 * CSC 212 Project - Phase 2
 * Class: Binary Search Tree (BST)
 * * Represents a Logarithmic-Time Data Structure.
 * Used to store Products and Customers efficiently (O(log n)).
 * * Implementation Style: 
 * Matches "Lectures 25-27" logic (Iterative Search & Insert, Delete by Merging).
 * * @param <T> The type of data (must be Comparable).
 */
public class BST<T extends Comparable<T>> {
    
    protected BSTNode<T> root;

    public BST() {
        root = null;
    }

    // ========================================================
    // 1. SEARCH OPERATION
    // Logic: Iterative (Matches Slide 10)
    // Requirement: "Search Product/Customer... must use logarithmic search"
    // Time Complexity: O(log n)
    // ========================================================
    public T search(T el) {
        BSTNode<T> p = root;
        while (p != null) {
            // Compare the key with the current node
            if (el.compareTo(p.el) == 0) {
                return p.el; // Found
            } else if (el.compareTo(p.el) < 0) {
                p = p.left; // Go Left
            } else {
                p = p.right; // Go Right
            }
        }
        return null; // Not found
    }

    // ========================================================
    // 2. INSERT OPERATION
    // Logic: Iterative (Matches Slide 13)
    // Requirement: "Insert... must operate in O(log n)"
    // ========================================================
    public void insert(T el) {
        BSTNode<T> p = root, prev = null;
        
        // Step 1: Find the insertion point
        while (p != null) {
            prev = p;
            if (el.compareTo(p.el) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        
        // Step 2: Link the new node
        if (root == null) {
            root = new BSTNode<>(el);
        } else if (el.compareTo(prev.el) < 0) {
            prev.left = new BSTNode<>(el);
        } else {
            prev.right = new BSTNode<>(el);
        }
    }

    // ========================================================
    // 3. DELETE OPERATION
    // Logic: Delete by Merging (Matches Slide 22 & 23)
    // Requirement: Remove Product/Customer from the Tree
    // ========================================================
    public void delete(T el) {
        BSTNode<T> node = root, prev = null;
        
        // Step 1: Find the node to delete
        while (node != null && !node.el.equals(el)) {
            prev = node;
            if (el.compareTo(node.el) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        
        // Node not found
        if (node == null) return;

        // Step 2: Delete the node based on its position
        if (node == root) {
            root = deleteNode(root);
        } else if (prev.left == node) {
            prev.left = deleteNode(prev.left);
        } else {
            prev.right = deleteNode(prev.right);
        }
    }

    // Helper method: Implements the "Delete by Merging" logic
    private BSTNode<T> deleteNode(BSTNode<T> node) {
        if (node.right == null) {
            // Case 1: No right child -> return left child
            return node.left;
        } else if (node.left == null) {
            // Case 2: No left child -> return right child
            return node.right;
        } else {
            // Case 3: Two children -> Merge (Find rightmost node of left subtree)
            BSTNode<T> tmp = node.left;
            while (tmp.right != null) {
                tmp = tmp.right;
            }
            // Attach right subtree to the rightmost node of the left subtree
            tmp.right = node.right;
            return node.left;
        }
    }

    // ========================================================
    // 4. TRAVERSAL (In-Order)
    // Requirement: "Sorted traversals" & "List All Sorted"
    // ========================================================
    public void printInOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(BSTNode<T> p) {
        if (p != null) {
            inOrder(p.left);
            System.out.print(p.el + " ");
            inOrder(p.right);
        }
    }
    
    // ========================================================
    // 5. HELPER: Tree-to-List Conversion
    // Purpose: Converts the Tree into a Linear List (In-Order)
    // Used by: Advanced Queries (e.g., Top 3, Range Search)
    // ========================================================
    public MyLinkedList<T> getAllElements() {
        MyLinkedList<T> list = new MyLinkedList<>();
        fillListRec(root, list);
        return list;
    }

    private void fillListRec(BSTNode<T> current, MyLinkedList<T> list) {
        if (current != null) {
            fillListRec(current.left, list);  // Left
            list.add(current.el);             // Root (Add to list)
            fillListRec(current.right, list); // Right
        }
    }
}