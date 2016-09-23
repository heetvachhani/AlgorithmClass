package Project1;

import java.util.Scanner;

public class EvaluateTemperature {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter temperature: "); //getting input of temp and unit
		int temp = sc.nextInt(); 
		System.out.print("Enter unit for temperature: ");
		String unit = sc.next();
		
		if (unit.equals("C")) { // converting temp to Fahrenheit if its in Celsius
			temp = (int) ((9.0 / 5.0) * temp + 32);
		}
		
		if (temp < 0) {
			System.out.println("Extremely cold");
		} else if (temp <= 32 && temp >= 0) {
			System.out.println("Very cold");
		} else if (temp <= 50 && temp >= 33) {
			System.out.println("Cold");
		} else if (temp <= 70 && temp >= 51) {
			System.out.println("Mild");
		} else if (temp <= 90 && temp >= 71) {
			System.out.println("Warm");
		} else if (temp <= 100 && temp >= 91) {
			System.out.println("Hot");
		} else {
			System.out.println("Very hot");
		}

	}

}
