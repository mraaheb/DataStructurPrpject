/**
 * CSC 212 Project - Phase 2 (Bonus)
 * Class: AVLTree (Self-Balancing BST)
 * * Implements AVL Tree logic with Rotations (LL, RR, LR, RL).
 * Used to guarantee O(log n) search time even in worst cases.
 */
public class AVLTree<T extends Comparable<T>> extends BST<T> {

    public AVLTree() {
        root = null;
    }

    // ========================================================
    // 1. HELPER METHODS (Height & Balance)
    // ========================================================
    
    private int height(BSTNode<T> N) {
        if (N == null) return 0; // Height of null is 0 (or -1 depending on convention, slides imply 0-based for null as base)
        return N.height;
    }

    private int getBalance(BSTNode<T> N) {
        if (N == null) return 0;
        return height(N.left) - height(N.right);
    }

    private void updateHeight(BSTNode<T> N) {
        if (N != null) {
            N.height = Math.max(height(N.left), height(N.right)) + 1;
        }
    }

    // ========================================================
    // 2. ROTATIONS (The Core Logic)
    // ========================================================

    // Right Rotate (for LL Imbalance)
    private BSTNode<T> rightRotate(BSTNode<T> y) {
        //System.out.println(">> Balancing Tree: Performing Right Rotation..."); // سطر التأكد
        BSTNode<T> x = y.left;
        BSTNode<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        // Return new root
        return x;
    }

    // Left Rotate (for RR Imbalance)
    private BSTNode<T> leftRotate(BSTNode<T> x) {
       // System.out.println(">> Balancing Tree: Performing Left Rotation..."); // سطر التأكد
        BSTNode<T> y = x.right;
        BSTNode<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        // Return new root
        return y;
    }

    // ========================================================
    // 3. INSERT (Recursive with Balancing)
    // ========================================================
    
    @Override
    public void insert(T el) {
        root = insertRec(root, el);
    }

    private BSTNode<T> insertRec(BSTNode<T> node, T el) {
        // 1. Perform standard BST insert
        if (node == null) {
            // New node has height 1
            BSTNode<T> newNode = new BSTNode<>(el);
            newNode.height = 1; 
            return newNode;
        }

        int cmp = el.compareTo(node.el);
        if (cmp < 0) {
            node.left = insertRec(node.left, el);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, el);
        } else {
            return node; // Duplicate keys not allowed (or handle as needed)
        }

        // 2. Update height of this ancestor node
        updateHeight(node);

        // 3. Get the balance factor to check whether this node became unbalanced
        int balance = getBalance(node);

        // 4. If unbalanced, there are 4 cases:

        // Left Left Case
        if (balance > 1 && el.compareTo(node.left.el) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && el.compareTo(node.right.el) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && el.compareTo(node.left.el) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && el.compareTo(node.right.el) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return the (unchanged) node pointer
    }

    // ========================================================
    // 4. DELETE (Recursive with Balancing)
    // ========================================================
    
    @Override
    public void delete(T el) {
        root = deleteRec(root, el);
    }

    private BSTNode<T> deleteRec(BSTNode<T> root, T el) {
        // 1. Standard BST delete
        if (root == null) return root;

        int cmp = el.compareTo(root.el);
        if (cmp < 0) {
            root.left = deleteRec(root.left, el);
        } else if (cmp > 0) {
            root.right = deleteRec(root.right, el);
        } else {
            // Node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                BSTNode<T> temp = (root.left != null) ? root.left : root.right;
                if (temp == null) {
                    root = null; // No child case
                } else {
                    root = temp; // One child case
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                BSTNode<T> temp = minValueNode(root.right);
                root.el = temp.el; // Copy the inorder successor's content to this node
                root.right = deleteRec(root.right, temp.el); // Delete the inorder successor
            }
        }

        if (root == null) return root;

        // 2. Update height
        updateHeight(root);

        // 3. Balance the tree
        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private BSTNode<T> minValueNode(BSTNode<T> node) {
        BSTNode<T> current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }
}
    

