import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Authors: Alex Rossi and Elbert Basolis
public class Friends {
	public static void main(String args[]) throws FileNotFoundException{ 
		System.out.println("Enter the filename you want to build the graph with:");
		Scanner sc = new Scanner(System.in);
//		String file = sc.next();
		//test with test.txt
//		Graph g = new Graph(file);
		Graph c = new Graph("test.txt", "rutgers");
		c.print();
		ArrayList<Graph> graphs = c.cliqueGraph();
		for(int i = 0; i < graphs.size(); i++){
			graphs.get(i).print();
		}
		
//		g.print();
//		g.dfs();
	}
}
