package Project3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(AnyType x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 * @throws Exception
	 */
	public AnyType findMin() throws Exception {
		if (isEmpty())
			throw new Exception();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 * @throws Exception
	 */
	public AnyType findMax() throws Exception {
		if (isEmpty())
			throw new Exception();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if not found.
	 */
	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return new BinaryNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if (t != null)
			while (t.right != null)
				t = t.right;

		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the subtree.
	 * @return node containing the matched item.
	 */
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null)
			return false;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // Match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.print(t.element + "   ");
			printTree(t.right);
		}
	}

	/**
	 * Internal method to compute height of a subtree.
	 * 
	 * @param t
	 *            the node that roots the subtree.
	 */
	private int height(BinaryNode<AnyType> t) {
		if (t == null)
			return -1;
		else
			return 1 + Math.max(height(t.left), height(t.right));
	}

	// node count
	public int nodeCount(BinaryNode<AnyType> t) {
		if (t == null)
			return 0;
		return 1 + nodeCount(t.left) + nodeCount(t.right);
	}

	// check binary tree is full
	public boolean isFull(BinaryNode<AnyType> t) {
		if (t == null)
			return true;
		else if (t.left == null && t.right == null)
			return true;
		else if (t.left != null && t.right != null)
			return (isFull(t.left) && isFull(t.right));
		return false;
	}

	// compare 2 binary tree structure
	public boolean compareStructure(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;

		if (t1 == null || t2 == null)
			return false;

		return (compareStructure(t1.left, t2.left) && compareStructure(t1.right, t2.right));
	}

	// compare 2 tree
	public boolean equals(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;

		if (t1 == null || t2 == null)
			return false;

		return (t1.element.equals(t2.element) && equals(t1.left, t2.left) && equals(t1.right, t2.right));

	}

	// copy given tree and return new tree in result
	public BinarySearchTree<AnyType> copy(BinaryNode<AnyType> t) {
		BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
		return copyBST(t, newTree);
	}

	private BinarySearchTree<AnyType> copyBST(BinaryNode<AnyType> t, BinarySearchTree<AnyType> newTree) {
		if (t != null) {
			newTree.insert(t.element);
			copyBST(t.left, newTree);
			copyBST(t.right, newTree);
		}
		return newTree;
	}

	// create mirror of given binary tree
	public BinarySearchTree<AnyType> mirror(BinaryNode<AnyType> t) {
		if (t == null)
			return null;
		BinarySearchTree<AnyType> newTree = new BinarySearchTree<>();
		newTree.root = MirrorBST(t, new BinaryNode<AnyType>(null));
		return newTree;
	}

	private BinaryNode<AnyType> MirrorBST(BinaryNode<AnyType> tNode, BinaryNode<AnyType> newTreeNode) {
		newTreeNode.element = tNode.element;
		if (tNode.left != null)
			newTreeNode.right = MirrorBST(tNode.left, new BinaryNode<AnyType>(null));

		if (tNode.right != null)
			newTreeNode.left = MirrorBST(tNode.right, new BinaryNode<AnyType>(null));

		return newTreeNode;
	}

	// check if 2 tree is mirror of each other
	public boolean isMirror(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2) {
		if (t1 == null && t2 == null)
			return true;

		if (t1 == null || t2 == null)
			return false;

		return (t1.element.equals(t2.element)) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
	}

	// rotate given node to left
	public void rotateLeft(AnyType val) {
		BinaryNode<AnyType> node = findNode(val); // find Node to rotate
		if (node == null)
			return;

		BinaryNode<AnyType> nodeParent = findParent(val); // find Parent node
		if (node.right == null) 
			return;

		BinaryNode<AnyType> nodeRight = node.right;
		node.right = nodeRight.left;

		if (nodeParent == null)
			root = nodeRight;
		else if (nodeParent.left == node)
			nodeParent.left = nodeRight;
		else
			nodeParent.right = nodeRight;

		nodeRight.left = node;
	}

	// rotate given node to right
	public void rotateRight(AnyType val) {
		BinaryNode<AnyType> node = findNode(val); // find Node to rotate
		if (node == null)
			return;

		BinaryNode<AnyType> nodeParent = findParent(val); // find Parent node
		if (node.left == null) 
			return;
		BinaryNode<AnyType> nodeLeft = node.left;
		node.left = nodeLeft.right;
	
		if (nodeParent == null)
			root = nodeLeft;
		else if (nodeParent.left == node)
			nodeParent.left = nodeLeft;
		else
			nodeParent.right = nodeLeft;

		nodeLeft.right = node;
	}

	private BinaryNode<AnyType> findParent(AnyType val) {
		BinaryNode<AnyType> parent = null;
		BinaryNode<AnyType> node = root;

		while (!node.element.equals(val)) {
			if (val.compareTo(node.element) > 1) { 
				if (node.right == null)
					return null;
				parent = node;
				node = node.right;
			} else { 
				if (node.left == null)
					return null;
				parent = node;
				node = node.left;
			}
		}
		return parent;
	}

	private BinaryNode<AnyType> findNode(AnyType val) {
		BinaryNode<AnyType> node = root;
		if (node == null)
			return null;
		while (!node.element.equals(val)) {
			if (val.compareTo(node.element) > 1) { 
				if (node.right == null)
					return null;
				node = node.right;
			} else { 
				if (node.left == null)
					return null;
				node = node.left;
			}
		}
		return node;
	}

	public void printLevels(BinaryNode<AnyType> t) {
		Queue<BinaryNode<AnyType>> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			BinaryNode<AnyType> n = q.poll();
			System.out.print(n.element + " ");
			if (n.left != null)
				q.add(n.left);
			if (n.right != null)
				q.add(n.right);
		}
		System.out.println();
	}

	// Basic node stored in unbalanced binary search trees
	private static class BinaryNode<AnyType> {
		// Constructors
		BinaryNode(AnyType theElement) {
			this(theElement, null, null);
		}

		BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
		}

		AnyType element; // The data in the node
		BinaryNode<AnyType> left; // Left child
		BinaryNode<AnyType> right; // Right child
	}

	/** The tree root. */
	private BinaryNode<AnyType> root;

	// Test program
	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		t.insert(8);
		t.insert(3);
		t.insert(10);
		t.insert(14);
		t.insert(9);

		BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
		t2.insert(7);
		t2.insert(3);
		t2.insert(10);
		t2.insert(14);
		t2.insert(9);
		System.out.print("Tree1:-> ");
		t.printTree();
		System.out.println();
		System.out.print("Tree2:-> ");
		t2.printTree();
		System.out.println();
		System.out.println();
		System.out.print("Print statements of tree are using inorder traversal: \n");

		System.out.println("Node count for Tree1:-> " + t.nodeCount(t.root));
		System.out.println();

		System.out.println("Is Tree1 Full?:-> " + t.isFull(t.root));
		System.out.println();

		System.out.println("Compare structure for Tree1 & Tree2:-> " + t.compareStructure(t.root, t2.root));
		System.out.println();

		System.out.println("Check if 2 trees are equal:-> " + t.equals(t.root, t2.root));
		System.out.println();

		System.out.print("Copied Tree1 to new tree:-> ");
		BinarySearchTree<Integer> copyTree = t.copy(t.root);
		copyTree.printTree();
		System.out.println();
		System.out.println();

		System.out.print("Mirroring Tree1:-> ");
		BinarySearchTree<Integer> mirrorTree = t.mirror(t.root);
		mirrorTree.printTree();
		System.out.println();
		System.out.println();

		System.out.println(
				"Check if Tree1 & mirrorTree are mirror of each other:-> " + t.isMirror(t.root, mirrorTree.root));
		System.out.println();
		
		System.out.print("Rotate Left node8 Tree1 :-> ");
		t.rotateLeft(8);
		t.printTree();
		System.out.println();
		System.out.println();
		
		System.out.print("Rotate Right node8 Tree1:-> ");
		t.rotateRight(10);
		t.printTree();
		System.out.println();
		System.out.println();

		System.out.print("Print level Tree1:-> ");
		t.printLevels(t.root);
	}
}
