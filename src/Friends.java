import java.io.FileNotFoundException;
import java.util.Scanner;

//Authors: Alex Rossi and Elbert Basolis
public class Friends {
	public static void main(String args[]) throws FileNotFoundException{ 
		System.out.println("Enter the filename you want to build the graph with:");
		Scanner sc = new Scanner(System.in);
		String file = sc.next();
		//test with test.txt
		Graph g = new Graph(file);
	}
}
