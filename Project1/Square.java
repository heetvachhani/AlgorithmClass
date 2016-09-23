package Project1;

public class Square {
	
	private int length;
	
	public Square() {
		length=1;
	}
	
	public Square(int val){
		length=val;
	}
	
	public int getArea(){
		return length*length; //returning area of square 
	}
}
