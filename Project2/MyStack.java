package Project2;

import java.util.ArrayList;

public class MyStack<T> {

	private ArrayList<T> mylist = new ArrayList<T>();

	public void push(T data) {
		mylist.add(data);
	}

	public T pop() {
		T data = mylist.get(mylist.size() - 1);
		mylist.remove(mylist.size() - 1);
		return data;
	}

	public T peek() {
		return mylist.get(mylist.size() - 1);
	}

	public boolean isEmpty() {
		return mylist.isEmpty();
	}

	public static void main(String[] args) {
		MyStack<Character> stack = new MyStack<>();
		String expression = "{{{}}}";
		System.out.println("Is expression: {{{}}} nasted correctly? => " + checkExpression(stack, expression));
		expression = "{[(])}";
		System.out.println("Is expression: {[(])} nasted correctly? => " + checkExpression(stack, expression));

	}

	public static boolean checkExpression(MyStack<Character> stack, String exp) {
		if (exp.length() == 0 || exp.length() % 2 != 0) {
			return false;
		}
		for (char c : exp.toCharArray()) {
			if (c == '{' || c == '[' || c == '(') {
				stack.push(c);
			} else {
				if (stack.isEmpty())
					return false;
				char current = stack.pop();

				switch (current) {
				case '{':
					if (c != '}')
						return false;
					break;
				case '[':
					if (c != ']')
						return false;
					break;
				case '(':
					if (c != ')')
						return false;
					break;
				default:
					return false;
				}
			}
		}
		if (stack.isEmpty())
			return true;
		return false;
	}

}
