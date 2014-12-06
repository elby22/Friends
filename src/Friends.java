import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Authors: Alex Rossi and Elbert Basolis
public class Friends {
	public static void main(String args[]) throws FileNotFoundException{ 
		System.out.println("Enter the filename you want to build the graph with:");
		Scanner sc = new Scanner(System.in);
		String file = sc.next();
		Graph g = new Graph(file);
		g.exportGraph();
		g.print();
		//IMOPORTANT - BUILD GRAPH ON EACH CHOICE USING NECESSARY CONSTRUCTER 
		while(true){
			System.out.println("Choose one of the following by entering the corresponding number...");
			System.out.println("1. Shortest intro chain");
			System.out.println("2. Cliques at school");
			System.out.println("3. Connecters");
			System.out.println("0. Quit");
			int in = sc.nextInt();
			if(in > 3 || in < 0){
				System.out.println("Invalid choice");
			}else if(in == 1){ //Shortest Chain
				
				
			}else if(in == 2){ //Cliques at school
				System.out.println("Enter the school you wish to find cliques in:");
				Graph c = new Graph(file, sc.next());
				ArrayList<Graph> graphs = c.cliqueGraph();
				for(int i = 0; i < graphs.size(); i++){
					graphs.get(i).print();
					graphs.get(i).exportGraph();
				}
				System.out.println(); //Extra space
			}else if(in == 3){ //Connecters
			
			}else{
				break;
			}
		}
	}
}
