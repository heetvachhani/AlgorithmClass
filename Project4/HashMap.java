package Project4;

public class HashMap<K> {
	int SIZE_OF_TABLE = 6000;

	@SuppressWarnings("unchecked")
	HashMap() {
		table = new HashEntry[SIZE_OF_TABLE];
		for (int i = 0; i < SIZE_OF_TABLE; i++) {
			table[i] = null;
		}
	}

	private int getHashCode(K key) {
		int hashVal = 0;
	     for (int i=0; i < key.toString().length(); i++)
	         hashVal += ((String) key).charAt(i);   // sum ASCII of each char
	     return hashVal % SIZE_OF_TABLE;    // mod sum by size
	}
	
	public void put(K key) {
		if (key == null)
			return;

		int index = getHashCode(key);
		HashEntry<K> newEntry = new HashEntry<K>(key, null);

		if (table[index] == null) {
			table[index] = newEntry;
		} else {
			HashEntry<K> previous = null;
			HashEntry<K> current = table[index];
			while (current != null) { // we have reached last entry of bucket.
				if (current.key.equals(key)) {
					if (previous == null) { // node has to be insert on first of
											// bucket.
						newEntry.next = current.next;
						table[index] = newEntry;
						return;
					} else {
						newEntry.next = current.next;
						previous.next = newEntry;
						return;
					}
				}
				previous = current;
				current = current.next;
			}
			previous.next = newEntry;
		}

	}

	public boolean contains(K key) {
		int index = getHashCode(key);
		if (table[index] == null) {
			return false;
		} else {
			HashEntry<K> temp = table[index];
			while (temp != null) {
				if (temp.key.equals(key))
					return true;
				temp = temp.next; // return value corresponding to key.
			}
			return false; // returns null if key is not found.
		}
	}

	public boolean remove(K deleteKey) {

		int index = getHashCode(deleteKey);

		if (table[index] == null) {
			return false;
		} else {
			HashEntry<K> previous = null;
			HashEntry<K> current = table[index];

			while (current != null) { // we have reached last entry node of
										// bucket
				if (current.key.equals(deleteKey)) {
					if (previous == null) { // delete first entry node.
						table[index] = table[index].next;
						return true;
					} else {
						previous.next = current.next;
						return true;
					}
				}
				previous = current;
				current = current.next;
			}
			return false;
		}
	}
	
	// resizing hash function
		@SuppressWarnings({ "unchecked", "unused" })
		private void resize(int size) {
	        HashEntry<K> temp = new HashEntry<K>(null, null);
	        for (int i = 0; i < size; i++) {
	            for (HashEntry<K> key : table) {
	                temp.key=(K) key;
	            }
	        }
	    }


	public void display() {
		for (int i = 0; i < SIZE_OF_TABLE; i++) {
			if (table[i] != null) {
				HashEntry<K> entry = table[i];
				while (entry != null) {
					System.out.print("{" + entry.key + "}" + " ");
					entry = entry.next;
				}
			}
		}
	}

	static class HashEntry<K> {
		K key;
		HashEntry<K> next;

		public HashEntry(K key, HashEntry<K> next) {
			this.key = key;
			this.next = next;
		}

	}
	
	private HashEntry<K>[] table;

}