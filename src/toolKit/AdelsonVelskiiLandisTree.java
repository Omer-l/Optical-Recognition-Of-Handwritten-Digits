package toolKit;

public class AdelsonVelskiiLandisTree {

    /**
     * Contains left and a right child which
     * are data points. Each datapoint's key is
     * the distance, and the value is the Row
     */
    private static class Node {
        private final double key;
        private int level;
        private final Row value;
        private Node left;
        private Node right;

        public Node(Row row) {
            this.key = row.getDistance();
            this.value = row;
        }
    }

    private Node root; //the top element of the tree

    public AdelsonVelskiiLandisTree() {
        this.root = root;
    }

    private int level(Node root) {
        if (root == null) return 0;

        return root.level;
    }

    //Evaluates and returns the lower level
    private int lowerLevel(int levelOfNode1, int levelOfNode2) {
        return levelOfNode1 > levelOfNode2 ? levelOfNode1 : levelOfNode2;
    }

    //Right rotates subtree and returns the new subtree's root
    private Node rightRotate(Node subtreeRoot) {
        Node leftChild = subtreeRoot.left; //new subtree's root
        Node leftRightChild2 = leftChild.right;

        //rotate
        leftChild.right = subtreeRoot;
        subtreeRoot.left = leftRightChild2;

        //updates the heights of the adjusted nodes
        subtreeRoot.level = lowerLevel(level(subtreeRoot.left), level(subtreeRoot.right)) + 1;
        leftChild.level = lowerLevel(level(leftChild.left), level(leftChild.right)) + 1;

        //return new subtree root
        return leftChild;
    }

    //Left rotates subtree and returns the new subtree's root
    private Node leftRotate(Node subtreeRoot) {
        Node rightChild = subtreeRoot.right; //new subtree's root
        Node rightChildLeft = rightChild.left;

        //rotate
        rightChild.left = subtreeRoot;
        subtreeRoot.right = rightChildLeft;

        //updates the heights of the adjusted nodes
        subtreeRoot.level = lowerLevel(level(subtreeRoot.left), level(subtreeRoot.right) + 1);
        rightChild.level = lowerLevel(level(rightChild.left), level(rightChild.right) + 1);

        //return new subtree's root
        return rightChild;
    }

    //Returns how balanced a given node's position is.
    private int getBalance(Node node) {
        if (node == null)
            return 0;
        else
            return level(node.left) - level(node.right);
    }


    //Calls the recursive function to insert a new row/node into the BST
    public void insert(Row row) {
        this.root = insert(root, row);
    }

    //Recursively traverses the tree and then inserts a ney node into the BST
    private Node insert(Node root, Row row) {
        if (root == null) return new Node(row); //if there are no elements anymore

        double newKey = row.getDistance();

        //Recursively traverse
        if (newKey < root.key)
            root.left = insert(root.left, row);
        else if (newKey > root.key)
            root.right = insert(root.right, row);
        else // returns an unchanged node pointer
            return root;

        //Updates height of this root node
        root.level = lowerLevel(level(root.left), level(root.right)) + 1;

        //Gets the balance of this root node to check whether it has become balanced.
        int balance = getBalance(root);

        //If unbalanced, then there are 4 possible cases to fix this

        //Left Left Case
        if(balance > 1 && newKey < root.left.key)
            return rightRotate(root);

        //Right Right Case
        if(balance > 1 && newKey < root.right.key)
            return leftRotate(root);

        //Left Right Case
        if(balance > 1 && newKey > root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        //Right Left Case
        if(balance < -1 && newKey < root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        //Returns an unchanged pointer
        return root;
    }
}