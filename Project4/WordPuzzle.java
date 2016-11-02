package Project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WordPuzzle {

	static char grid[][];
	static HashMap<String> wordsMap = new HashMap<>();
	static List<String> wordsList = new LinkedList<>();
	static AvlTree<String> wordsTree = new AvlTree<>();
	static ArrayList<WordInfo> result = new ArrayList<>();
	static ArrayList<String> duplicateCheck = new ArrayList<>();

	public static void main(String[] args) {

		int rows, cols;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of rows: ");
		rows = sc.nextInt();
		System.out.print("Enter number of columns: ");
		cols = sc.nextInt();

		// init grid with input size
		grid= new char[rows][cols];
		// add random char to grid
	    addCharToGrid();

		// add dictionary to HashMap, LinkedList, AVLTree
		addDictionaryToDataStructures();
		System.out.println("\nInput grid==>");
		// print the grid
		printGrid();
		System.out.println();

		long start = System.currentTimeMillis();
		findWords(grid, "linkedlist");
		System.out.println("   1. Performance while dictionary kept in LinkedList: "
				+ (System.currentTimeMillis() - start) + " ms");
		start = System.currentTimeMillis();
		findWords(grid, "tree");
		System.out.println(
				"   2. Performance while dictionary kept in Tree: " + (System.currentTimeMillis() - start) + " ms");
		start = System.currentTimeMillis();
		findWords(grid, "hash");
		System.out.println("   3. Performance while dictionary kept in Hash Table: "
				+ (System.currentTimeMillis() - start) + " ms");

		System.out.println("\nFounds following words from the grid: ");
		printSolutions(result);

		sc.close();
	}

	// fill the grid with random characters
	private static void addCharToGrid() {
		Random rc = new Random();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = (char) (rc.nextInt(26) + 'a');
			}
		}
	}

	// add dictionary to hashmap, tree and LinkedList
	private static void addDictionaryToDataStructures() {
		try {
			Scanner sf = new Scanner(new File("/Users/Heet/Documents/workspace/AlgoClass/src/Project4/dictionary.txt"));
			String dictWord = "";
			while (sf.hasNextLine()) {
				dictWord = sf.nextLine();
				wordsList.add(dictWord);
				wordsTree.insert(dictWord);
				wordsMap.put(dictWord);
			}
			sf.close();
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary not found!!");
			e.printStackTrace();
		}
	}

	private static void findWords(char[][] grid, String dataStructure) {
		result = new ArrayList<>();
		duplicateCheck=new ArrayList<>();
		int cols = grid[0].length;
		int rows = grid.length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				checkVertical(grid, dataStructure, i, j);
				checkHorizontal(grid, dataStructure, i, j);
				checkDiagonalLeft(grid, dataStructure, i, j);
				checkDiagonalRight(grid, dataStructure, i, j, rows, cols);
			}
		}
	}

	private static void checkVertical(char[][] grid, String dataStructure, int row, int col) {
		StringBuilder word = new StringBuilder();
		for (int i = row; i >= 0; i--) {
			word.append(grid[i][col]);
			if (!duplicateCheck.contains(word.toString() + row + col))
				checkWord(dataStructure, word.toString(), row, col, i, col);
			word.reverse();
			if (!duplicateCheck.contains(word.toString() + i + col))
				checkWord(dataStructure, word.toString(), i, col, row, col);
			word.reverse();

		}
	}

	private static void checkHorizontal(char[][] grid, String dataStructure, int row, int col) {
		StringBuilder word = new StringBuilder();

		for (int j = col; j >= 0; j--) {
			word.append(grid[row][j]);
			if (!duplicateCheck.contains(word.toString() + row + col))
				checkWord(dataStructure, word.toString(), row, col, row, j);
			word.reverse();
			if (!duplicateCheck.contains(word.toString() + row + j))
				checkWord(dataStructure, word.toString(), row, j, row, col);
			word.reverse();

		}
	}

	private static void checkDiagonalLeft(char[][] grid, String dataStructure, int row, int col) {

		StringBuilder word = new StringBuilder();

		for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
			word.append(grid[i][j]);
			if (!duplicateCheck.contains(word.toString() + row + col))
				checkWord(dataStructure, word.toString(), row, col, i, j);
			word.reverse();
			if (!duplicateCheck.contains(word.toString() + i + j))
				checkWord(dataStructure, word.toString(), i, j, row, col);
			word.reverse();
		}
	}

	private static void checkDiagonalRight(char[][] grid, String dataStructure, int row, int col, int numRows,
			int numCols) {
		StringBuilder word = new StringBuilder();
		for (int i = row, j = col; i >= 0 && j < numCols; i--, j++) {
			word.append(grid[i][j]);
			if (!duplicateCheck.contains(word.toString() + row + col))
				checkWord(dataStructure, word.toString(), row, col, i, j);
			word.reverse();
			if (!duplicateCheck.contains(word.toString() + i + j))
				checkWord(dataStructure, word.toString(), i, j, row, col);
			word.reverse();

		}
	}

	private static void checkWord(String dataStructure, String word, int i, int j, int row, int col) {
		if (dataStructure.equals("hash")) {
			if (wordsMap.contains(word)) {
				result.add(new WordInfo(word.toString(), i, j, row, col));
				duplicateCheck.add(word + i + j);
			}
		} else if (dataStructure.equals("linkedlist")) {
			if (wordsList.contains(word)) {
				result.add(new WordInfo(word, i, j, row, col));
				duplicateCheck.add(word + i + j);
			}
		} else {
			if (wordsTree.contains(word)) {
				result.add(new WordInfo(word.toString(), i, j, row, col));
				duplicateCheck.add(word + i + j);
			}
		}
	}

	public static void printSolutions(ArrayList<WordInfo> words) {

		System.out.println("Total words Found " + words.size());
		for (WordInfo w : words) {
			System.out.println(w.toString());
		}
	}

	// Print the grid of characters
	private static void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static class WordInfo {
		private String word;
		private int rowStart;
		private int rowEnd;
		private int colStart;
		private int colEnd;

		WordInfo(String word, int rowFrom, int colFrom, int rowTo, int colTo) {
			this.word = word;
			this.rowStart = rowFrom;
			this.rowEnd = rowTo;
			this.colStart = colFrom;
			this.colEnd = colTo;
		}

		public String toString() {
			String info = "[" + rowStart + ", " + colStart + "] -> [" + rowEnd + ", " + colEnd + "] : "
					+ word.toUpperCase();
			return info;
		}

		public int compareTo(WordInfo word2) {
			return word.compareTo(word2.word);
		}
	}

}
