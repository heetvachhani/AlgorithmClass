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

	public static void main(String[] args) {
		MyStack<Character> stack = new MyStack<>();
		String expression = "[{({})}]";
		System.out.println("Is expression: [{({})}] nasted correctly? => " + checkExpression(stack, expression));
		expression = "{[(])}";
		System.out.println("Is expression: {[(])} nasted correctly? => " + checkExpression(stack, expression));

	}

	public static boolean checkExpression(MyStack<Character> stack, String exp) {
		for (int i = 0; i < exp.length(); i++) {
			if (exp.charAt(i) == '[' || exp.charAt(i) == '(' || exp.charAt(i) == '{') {
				stack.push(exp.charAt(i));
			} else {
				char current = stack.pop();
				switch (current) {
				case '{':
					if (exp.charAt(i) != '}')
						return false;
					break;
				case '(':
					if (exp.charAt(i) != ')')
						return false;
					break;
				case '[':
					if (exp.charAt(i) != ']')
						return false;
					break;
				}
			}
		}
		return true;
	}

}
