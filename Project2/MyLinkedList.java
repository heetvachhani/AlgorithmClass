package Project2;

public class MyLinkedList<AnyType> implements Iterable<AnyType> {
	/**
	 * Construct an empty LinkedList.
	 */
	public MyLinkedList() {
		doClear();
	}

	@SuppressWarnings("unused")
	private void clear() {
		doClear();
	}

	/**
	 * Change the size of this collection to zero.
	 */
	public void doClear() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;

		theSize = 0;
		modCount++;
	}

	/**
	 * Returns the number of items in this collection.
	 * 
	 * @return the number of items in this collection.
	 */
	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Adds an item to this collection, at the end.
	 * 
	 * @param x
	 *            any object.
	 * @return true.
	 */
	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	}

	/**
	 * Adds an item to this collection, at specified position. Items at or after
	 * that position are slid one position higher.
	 * 
	 * @param x
	 *            any object.
	 * @param idx
	 *            position to add at.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size(), inclusive.
	 */
	public void add(int idx, AnyType x) {
		addBefore(getNode(idx, 0, size()), x);
	}

	/**
	 * Adds an item to this collection, at specified position p. Items at or
	 * after that position are slid one position higher.
	 * 
	 * @param p
	 *            Node to add before.
	 * @param x
	 *            any object.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size(), inclusive.
	 */
	private void addBefore(Node<AnyType> p, AnyType x) {
		Node<AnyType> newNode = new Node<>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}

	/**
	 * Returns the item at position idx.
	 * 
	 * @param idx
	 *            the index to search in.
	 * @throws IndexOutOfBoundsException
	 *             if index is out of range.
	 */
	public AnyType get(int idx) {
		return getNode(idx).data;
	}

	/**
	 * Changes the item at position idx.
	 * 
	 * @param idx
	 *            the index to change.
	 * @param newVal
	 *            the new value.
	 * @return the old value.
	 * @throws IndexOutOfBoundsException
	 *             if index is out of range.
	 */
	public AnyType set(int idx, AnyType newVal) {
		Node<AnyType> p = getNode(idx);
		AnyType oldVal = p.data;

		p.data = newVal;
		return oldVal;
	}

	/**
	 * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	 * 
	 * @param idx
	 *            index to search at.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between 0 and size( ) - 1, inclusive.
	 */
	private Node<AnyType> getNode(int idx) {
		return getNode(idx, 0, size() - 1);
	}

	/**
	 * Gets the Node at position idx, which must range from lower to upper.
	 * 
	 * @param idx
	 *            index to search at.
	 * @param lower
	 *            lowest valid index.
	 * @param upper
	 *            highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException
	 *             if idx is not between lower and upper, inclusive.
	 */
	private Node<AnyType> getNode(int idx, int lower, int upper) {
		Node<AnyType> p;

		if (idx < lower || idx > upper)
			throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());

		if (idx < size() / 2) {
			p = beginMarker.next;
			for (int i = 0; i < idx; i++)
				p = p.next;
		} else {
			p = endMarker;
			for (int i = size(); i > idx; i--)
				p = p.prev;
		}

		return p;
	}

	/**
	 * Removes an item from this collection.
	 * 
	 * @param idx
	 *            the index of the object.
	 * @return the item was removed from the collection.
	 */
	public AnyType remove(int idx) {
		return remove(getNode(idx));
	}

	/**
	 * Removes the object contained in Node p.
	 * 
	 * @param p
	 *            the Node containing the object.
	 * @return the item was removed from the collection.
	 */
	private AnyType remove(Node<AnyType> p) {
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;

		return p.data;
	}

	/**
	 * Returns a String representation of this collection.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[ ");

		for (AnyType x : this)
			sb.append(x + " ");
		sb.append("]");

		return new String(sb);
	}

	public void swap(int idx1, int idx2) {
		Node<AnyType> node1 = getNode(idx1);
		Node<AnyType> node2 = getNode(idx2);

		if (idx1 < 0 || idx1 > size() - 1 || idx2 < 0 || idx2 > size() - 1) {
			System.out.println("Please enter index within range!!");
		} else if (Math.abs(idx1 - idx2) == 1) { // if 2 elements are next to each other
			node2.next.prev = node1;
			node1.next = node2.next;
			node1.prev.next = node2;
			
			node2.prev = node1.prev;
			node2.next = node1;
			node1.prev = node2;
		} else { 
			Node<AnyType> temp = new Node<>(node1.data, node1.prev, node1.next);

			node1.next = node2.next;
			node1.next.prev = node1;
			node1.prev = node2.prev;
			node1.prev.next = node1;

			node2.next = temp.next;
			node2.next.prev = node2;
			node2.prev = temp.prev;
			node2.prev.next = node2;

		}
	}

	public void shift(int shiftNum) {
		Node<AnyType> first;

		if (shiftNum > 0) { // shift left
			for (int i = 0; i < shiftNum; i++) {
				first = beginMarker.next;
				beginMarker.next = beginMarker.next.next;
				beginMarker.next.prev = beginMarker;
				endMarker.prev.next = first;
				first.prev = endMarker.prev;
				first.next = endMarker;
				endMarker.prev = first;

			}
		}

		else if (shiftNum < 0) { // shift right
			for (int i = 0; i < Math.abs(shiftNum); i++) {
				first = beginMarker.next;
				beginMarker.next = endMarker.prev;
				endMarker.prev.prev.next = endMarker;
				endMarker.prev = endMarker.prev.prev;
				beginMarker.next.prev = beginMarker;
				first.prev=beginMarker.next;
				beginMarker.next.next = first;

			}
		}
	}

	public void erase(int idx, int noOfElement) {
		// checking if index or total elements exceeds size of given list 
		if (idx < 0 || idx > size() - 1 || noOfElement <= 0 || idx + noOfElement - 1 > size() - 1) {
			System.out.println("Entered values are out of bound!!");
			return;
		} else {
			Node<AnyType> startNode = getNode(idx);
			int endIndex = idx + noOfElement - 1;
			Node<AnyType> endNode = getNode(endIndex);
			startNode.prev.next = endNode.next;
			endNode.next.prev = startNode.prev;
			this.theSize -= noOfElement;
		}
	}

	public void insertList(MyLinkedList<AnyType> newList, int idx) {
		if (idx < 0 || idx > size() - 1) { // checking for index getting out of bound
			System.out.println("Please enter valid index!!");
		} else {
			Node<AnyType> nodeBeforeInsert;
			Node<AnyType> nodeAfterInsert;
			if (idx == 0) { // if inserting at first index then first will be beginMarker
				nodeBeforeInsert = beginMarker;

			} else {
				nodeBeforeInsert = getNode(idx - 1);
			}
			nodeAfterInsert = getNode(idx);
			newList.beginMarker.next.prev = nodeBeforeInsert;
			nodeBeforeInsert.next = newList.beginMarker.next;

			newList.endMarker.prev.next = nodeAfterInsert;
			nodeAfterInsert.prev = newList.endMarker.prev;

			this.theSize += newList.size();
		}
	}

	/**
	 * Obtains an Iterator object used to traverse the collection.
	 * 
	 * @return an iterator positioned prior to the first element.
	 */
	public java.util.Iterator<AnyType> iterator() {
		return new LinkedListIterator();
	}

	/**
	 * This is the implementation of the LinkedListIterator. It maintains a
	 * notion of a current position and of course the implicit reference to the
	 * MyLinkedList.
	 */
	private class LinkedListIterator implements java.util.Iterator<AnyType> {
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext() {
			return current != endMarker;
		}

		public AnyType next() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!hasNext())
				throw new java.util.NoSuchElementException();

			AnyType nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove() {
			if (modCount != expectedModCount)
				throw new java.util.ConcurrentModificationException();
			if (!okToRemove)
				throw new IllegalStateException();

			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}
	}

	/**
	 * This is the doubly-linked list node.
	 */
	private static class Node<AnyType> {
		public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
			data = d;
			prev = p;
			next = n;
		}

		public AnyType data;
		public Node<AnyType> prev;
		public Node<AnyType> next;
	}

	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;

	public static void main(String[] args) {
		MyLinkedList<Integer> lst = new MyLinkedList<>();

		System.out.print("Initial list of 10 items => ");
		for (int i = 0; i < 10; i++)
			lst.add(i);
		System.out.println(lst + "\n");

		System.out.print("Swap elements at index 3 and 7 => ");
		lst.swap(3, 7);
		System.out.println(lst + "\n");

		System.out.print("Shift list to +2 elements => ");
		lst.shift(2);
		System.out.println(lst + "\n");

		System.out.print("Shift list to -2 elements => ");
		lst.shift(-2);
		System.out.println(lst + "\n");

		System.out.print("Remove elements starting from index 0 to 3 elements => ");
		lst.erase(0, 3);
		System.out.println(lst + "\n");

		System.out.print("Enter new list of 10, 11, 12 starting at index 2 => ");
		MyLinkedList<Integer> lst2 = new MyLinkedList<>();
		for (int i = 10; i < 13; i++)
			lst2.add(i);
		lst.insertList(lst2, 2);
		System.out.println(lst + "\n");

	}

}
