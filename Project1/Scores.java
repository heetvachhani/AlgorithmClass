package Project1;

import java.util.Scanner;

public class Scores {

	public static void main(String[] args) {

		String[] names = new String[10];
		String[][] score = new String[10][5];

		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < score.length; i++) {
			System.out.print("Enter " + (i + 1) + " student name: "); //getting inputs
			names[i] = sc.next();
			for (int j = 0; j < score[i].length; j++) {
				System.out.print("Enter " + (j + 1) + " quiz score: ");
				score[i][j] = sc.next();
			}
			System.out.println();
		}

		//System.out.println(Arrays.deepToString(score));
		int sum=0;
		for (int i = 0; i < score.length; i++) {
			sum=0;
			for (int j = 0; j < score[i].length; j++) {
				sum +=Integer.parseInt(score[i][j]); // calculating sum of quiz
			}
			System.out.println(names[i] + "'s average score is: "+ sum/score[i].length); // finding average using total sum by 10
		}

	}

}
