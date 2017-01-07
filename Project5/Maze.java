package Project5;

import java.awt.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

public class Maze {
	static int width, height;
	static int[][] maze;
	static int[][] index;
	static DisjointSet setMaze;
	static Vertex[][] vertexMaze;
	static String directions = "";

	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter dimensions for the Maze ");
		System.out.print("Enter a width: ");
		width = s.nextInt();
		System.out.print("Enter a height: ");
		height = s.nextInt();
		s.close();

		maze = new int[height][width];
		index = new int[height][width];
		int temp = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				index[i][j] = temp;
				maze[i][j] = 3;
				temp++;
			}
		}
		setMaze = new DisjointSet(height * width);
		genMaze();

		vertexMaze = new Vertex[height][width];
		makeVertice();
		
		JFrame frame = new JFrame();
		frame.setTitle("Maze generated!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new CreateMaze(maze, 10));
		frame.pack();
		frame.setBackground(Color.WHITE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void genMaze() {
		boolean done = false;
		while (!done) {

			int root1 = (int) (Math.random() * 10000) % (height * width);
			int rt1H = getHeight(root1);
			int rt1W = getWidth(root1);
			while (adjVal(root1) < 0) {
				root1 = (int) (Math.random() * 10000) % (height * width);
			}
			int root2 = adjVal(root1);

			int rt2H = getHeight(root2);
			int rt2W = getWidth(root2);
			int minW = getWidth(Math.min(root1, root2));
			int minH = getHeight(Math.min(root1, root2));
			if (!(setMaze.find(root1) == setMaze.find(root2))) {
				setMaze.union(setMaze.find(root1), setMaze.find(root2));
				if (rt1H == rt2H) {
					if (maze[rt1H][minW] == 3)
						maze[rt1H][minW] = 2;
					else
						maze[rt1H][minW] = 0;
				} else if (rt1W == rt2W) {
					if (maze[minH][rt1W] == 3)
						maze[minH][rt1W] = 1;
					else
						maze[minH][rt1W] = 0;
				}
			}
			for (int i = 0; i < height * width; i++) {
				if (setMaze.set[i] == -1 * (height * width)) {
					done = true;
				}
			}
		}
	}

	public static void makeVertice() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				vertexMaze[i][j] = new Vertex();
				vertexMaze[i][j].index = index[i][j];
			}
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i != 0) {
					if (maze[i - 1][j] == 0 || maze[i - 1][j] == 1) {
						vertexMaze[i][j].makeAdj(vertexMaze[i - 1][j]);
					}
				}
				if (j != 0) {
					if (maze[i][j - 1] == 0 || maze[i][j - 1] == 2) {
						vertexMaze[i][j].makeAdj(vertexMaze[i][j - 1]);
					}
				}
				if (i != (height - 1)) {
					if (maze[i][j] == 0 || maze[i][j] == 1) {
						vertexMaze[i][j].makeAdj(vertexMaze[i + 1][j]);
					}
				}
				if (j != (width - 1)) {
					if (maze[i][j] == 0 || maze[i][j] == 2) {
						vertexMaze[i][j].makeAdj(vertexMaze[i][j + 1]);
					}
				}
			}
		}
	}

	public static Vertex findSmallUnknown() {
		Double temp = Double.POSITIVE_INFINITY;
		Vertex t = new Vertex();
		for (int i = 0; i < vertexMaze.length; i++) {
			for (int j = 0; j < vertexMaze[i].length; j++) {
				if (!vertexMaze[i][j].visited) {
					if (temp > vertexMaze[i][j].dist) {
						t = vertexMaze[i][j];
						temp = t.dist;
					}
				}
			}
		}
		if (temp == Double.POSITIVE_INFINITY) {
			return null;
		}
		return t;
	}

	public static boolean checkAdj(int a, int b) {
		if (getHeight(a) == getHeight(b)) {
			if (Math.abs(getWidth(a) - getWidth(b)) == 1)
				return true;
		}
		if (getWidth(a) == getWidth(b)) {
			if (Math.abs(getHeight(a) - getHeight(b)) == 1)
				return true;
		}
		return false;
	}

	public static int adjVal(int cell) {
		int cellW = getWidth(cell);
		int cellH = getHeight(cell);
		int posCount = 0;
		int[] temp = new int[4];
		for (int i = 0; i < temp.length; i++)
			temp[i] = -1;
		if (cellH != 0) {
			if (maze[cellH - 1][cellW] != 0 || maze[cellH - 1][cellW] != 1) {
				temp[posCount] = index[cellH - 1][cellW];
				posCount++;

			}
		}
		if (cellH != index.length - 1) {
			if (maze[cellH][cellW] != 0 || maze[cellH][cellW] != 1) {
				temp[posCount] = index[cellH + 1][cellW];
				posCount++;
			}
		}
		if (cellW != 0) {
			if (maze[cellH][cellW - 1] != 0 || maze[cellH][cellW] != 2) {
				temp[posCount] = index[cellH][cellW - 1];
				posCount++;
			}
		}
		if (cellW != index[0].length - 1) {
			if (maze[cellH][cellW] != 0 || maze[cellH][cellW] != 2) {
				temp[posCount] = index[cellH][cellW + 1];
				posCount++;
			}
		}
		if (temp[0] < 0 && temp[1] < 0 && temp[2] < 0 && temp[3] < 0)
			return -1;
		int tempDex = (int) (Math.random() * 10) % posCount;
		while (temp[tempDex] < 0) {
			tempDex = (int) (Math.random() * 10) % posCount;
		}
		return temp[tempDex];
	}

	public static boolean diffSets(int a, int b) {
		if (setMaze.find(a) == setMaze.find(b)) {
			return false;
		}
		return true;
	}

	public static int getHeight(int cellIndex) {
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index[i].length; j++) {
				if (index[i][j] == cellIndex)
					return i;
			}
		}
		return -1;
	}

	public static int getWidth(int cellIndex) {
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index[i].length; j++) {
				if (index[i][j] == cellIndex)
					return j;
			}
		}
		return -1;
	}

	static class CreateMaze extends JPanel {
		private int[][] a;
		private int mazeSize;

		public CreateMaze(int[][] array, int boxsize) {
			a = array;
			this.mazeSize = boxsize;
			setPreferredSize(new Dimension(300, 300));
		}

		public void paintComponent(Graphics g) {
			int startx = 5;
			g.setColor(Color.BLACK);
			int currenty = startx;
			for (int i = 0; i < a.length; i++) {
				int currentx = startx;
				for (int j = 0; j < a[i].length; j++) {
					currentx += mazeSize;
					if ((a[i][j] == 1 || a[i][j] == 3) && j != a[i].length - 1)
						g.drawLine(currentx, currenty, currentx, currenty + mazeSize);
				}
				currenty += mazeSize;
			}
			g.drawLine(startx + mazeSize, startx, mazeSize * a[0].length + 5, startx);
			g.drawLine(startx, mazeSize * a.length + 5, mazeSize * a[0].length + 5, mazeSize * a.length + 5);
			g.drawLine(startx, startx, startx, mazeSize * a.length + 5);
			g.drawLine(mazeSize * a[0].length + 5, startx, mazeSize * a[0].length + 5, mazeSize * a.length + 5 - mazeSize);
			currenty = startx;
			for (int i = 0; i < a.length; i++) {
				int currentx = startx;
				currenty += mazeSize;
				for (int j = 0; j < a[0].length; j++) {
					if ((a[i][j] == 2) || (a[i][j] == 3))
						g.drawLine(currentx, currenty, currentx + mazeSize, currenty);
					currentx += mazeSize;
				}

			}
		}

	}
}

class Vertex {
	public LinkedList<Vertex> adjacency;
	public boolean visited;
	public Double dist;
	public Vertex path;
	public int index;

	public Vertex() {
		adjacency = new LinkedList();
		dist = Double.POSITIVE_INFINITY;
		visited = false;
	}

	public void makeAdj(Vertex a) {
		adjacency.add(a);
	}
}
