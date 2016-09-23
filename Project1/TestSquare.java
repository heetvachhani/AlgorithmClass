package Project1;

public class TestSquare {

	public static void main(String[] args) {
		Square sq1 = new Square(); // sq1 object that sets length of square with default value 1
		Square sq2 = new Square(5); //sq2 object that sets length of square with value 5

		System.out.println("Area of 1st Square: " + sq1.getArea());
		System.out.println("Area of 2nd Square: " + sq2.getArea());
	}

}
