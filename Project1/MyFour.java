package Project1;

public class MyFour<T> {

	private T item1;
	private T item2;
	private T item3;
	private T item4;

	public void setValue(T val1, T val2, T val3, T val4) { //set item values
		item1 = val1;
		item2 = val2;
		item3 = val3;
		item4 = val4;
	}

	public boolean allEqual() { //check if all items equal or not
		if (item1.equals(item2) && item2.equals(item3) && item3.equals(item4))
			return true;
		return false;
	}

	public void shiftLeft() {
		T temp;
		temp = item1;
		item1 = item2;
		item2 = item3;
		item3 = item4;
		item4 = temp;
	}

	public String toString() { //for printing items in order
		return "(" + item1 + ", " + item2 + ", " + item3 + ", " + item4 + ")";
	}

	public static void main(String[] args) {
		MyFour<String> objString = new MyFour<>(); //creating string object of MyFour
		objString.setValue("abc", "abc", "abc", "abc");
		System.out.println("Printing String object: "+objString);
		System.out.println("Result of allEqual method is: " + objString.allEqual());
		System.out.println();
		
		
		MyFour<Integer> objInt = new MyFour<>(); //creating Integer object of MyFour
		objInt.setValue(1, 2, 3, 4);
		System.out.println("Printing Integer object: "+objInt);
		System.out.println("Result of allEqual method is: " + objInt.allEqual());
		objInt.shiftLeft();
		System.out.println("After shiftleft operation: "+objInt);

	}

}
