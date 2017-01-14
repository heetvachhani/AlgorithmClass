package Project6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Kruskals {

	private static TreeMap<String, VertexSet> verticesMap;
	private static TreeSet<Edge> edgesMap;

	public static void main(String[] args) throws FileNotFoundException {
		readInputFile();

		ArrayList<Edge> resultList = new ArrayList<Edge>();
		for (Iterator<Edge> it = edgesMap.iterator(); it.hasNext();) {
			Edge e = it.next();
			String c1 = e.node1;
			String c2 = e.node2;
			if (findVSet(c1) != findVSet(c2)) {
				resultList.add(e);
				union(e.node1, e.node2);
			}
		}

		System.out.println("\nMST edges:::::\n");
		System.out.println("Cities\t\tDistance");
		double w = 0;
		for (Iterator<Edge> it = resultList.iterator(); it.hasNext();) {
			Edge e = it.next();
			System.out.println(e.node1 + " - " + e.node2 + " :  " + e.weight);
			w += e.weight;
		}
		System.out.println("\nSum of MST weight: " + w);
	}

	public static VertexSet findVSet(String c) {
		VertexSet v = verticesMap.get(c);
		return (v.head);
	}

	public static void union(String c1, String c2) {
		VertexSet v1 = verticesMap.get(c1);
		VertexSet v2 = verticesMap.get(c2);

		v1.head.tail.next = v2.head;
		v1.head.tail = v2.head.tail;
		for (VertexSet v = v2.head; v != null; v = v.next)
			v.head = v1.head;
	}

	// read graph from file
	@SuppressWarnings("resource")
	public static void readInputFile() throws FileNotFoundException {
		// /Users/Heet/Downloads/assn9_data.csv
		System.out.print("Enter input file path: ");
		Scanner inputFile = new Scanner(System.in);
		System.out.println();
		File file = new File(inputFile.nextLine());

		inputFile = new Scanner(file);

		Comparator<Edge> c = new Comparator<Edge>() {
			public int compare(Edge e1, Edge e2) {
				double weight1 = e1.weight;
				double weight2 = e2.weight;
				if (weight1 < weight2)
					return (-1);
				else if (weight1 > weight2)
					return (1);
				else {
					String s1 = e1.node1;
					String s2 = e2.node1;
					return (s1.compareTo(s2));
				}
			}
		};

		verticesMap = new TreeMap<String, VertexSet>();
		edgesMap = new TreeSet<Edge>(c);

		while (inputFile.hasNextLine()) {
			String s = inputFile.nextLine();
			if (s.length() > 0 && s.charAt(0) != '#') {
				StringTokenizer sTk = new StringTokenizer(s, ",");
				String vertexLabel1 = sTk.nextToken();
				if (verticesMap.containsKey(vertexLabel1)) {
					System.out.println("Multiple  vertex " + vertexLabel1 + " in the list");
					System.exit(0);
				}
				VertexSet v = new VertexSet(vertexLabel1);
				verticesMap.put(vertexLabel1, v);
				while (sTk.hasMoreTokens()) {
					String vertexLabel2 = sTk.nextToken();

					double edgeWeight = 0;
					try {
						edgeWeight = Double.parseDouble(sTk.nextToken());
					} catch (NumberFormatException nfe) {
						System.out.println("Wrong label/weight in the " + "adjacency list of " + vertexLabel1);
						System.exit(0);
					} catch (NoSuchElementException nfe) {
						System.out.println("Wrong adjacency list " + vertexLabel1);
						System.exit(0);
					}

					if (vertexLabel1.compareTo(vertexLabel2) < 0) {
						Edge e = new Edge(vertexLabel1, vertexLabel2, edgeWeight);
						edgesMap.add(e);
					}
				}
			}
		}

		inputFile.close();
	}

	// graph edge
	private static class Edge {
		String node1; // 1st node label
		String node2; // 2nd node label
		double weight; // weight of edge

		public Edge(String n1, String n2, double w) {
			node1 = n1;
			node2 = n2;
			weight = w;
		}
	}

	// graph vertex set
	private static class VertexSet {
		VertexSet next;
		VertexSet head;
		VertexSet tail;

		public VertexSet(String l) {
			next = null;
			head = tail = this;
		}
	}
}
