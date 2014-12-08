import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Authors: Alex Rossi and Elbert Basolis
public class Friends {
	public static void main(String args[]) throws FileNotFoundException{ 
		System.out.println("Enter the filename you want to build the graph with:");
		Scanner sc = new Scanner(System.in);
		String file = sc.next();
		
		//IMOPORTANT - BUILD GRAPH ON EACH CHOICE USING NECESSARY CONSTRUCTER 
		//Such as Graph g = new Graph(); or whichever constructer you need
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
			
				 Graph m = new Graph(file);
				System.out.println("Enter the first friend, then second friend");
				try{
					m.BFS(m, "sam", "aparna");
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("That person's name is either improperly typed or not in the graph");
				}
				
			}else if(in == 2){ //Cliques at school
				System.out.println("Enter the school you wish to find cliques in:");
				Graph c = new Graph(file, sc.next());
				ArrayList<Graph> graphs = c.cliqueGraph();
				for(int i = 0; i < graphs.size(); i++){
					System.out.println("Clique " + (i+1) + ":");
					graphs.get(i).exportGraph();
				}
				System.out.println(); //Extra space
			}else if(in == 3){ //Connecters
	//			Graph g = new Graph(file);
				Graph g = new Graph();
					g.adjLists = new Vertex[6];
					g.adjLists[0] = new Vertex("A", null, new Neighbor(1, null));
					g.adjLists[1] = new Vertex("B", null, new Neighbor(4, new Neighbor(2, new Neighbor(0, null))));
					g.adjLists[2] = new Vertex("C", null, new Neighbor(3, new Neighbor(1, null)));
					g.adjLists[3] = new Vertex("D", null, new Neighbor(5, new Neighbor(4, new Neighbor(2, null))));
					g.adjLists[4] = new Vertex("E", null, new Neighbor(3, new Neighbor(1, null)));
					g.adjLists[5] = new Vertex("F", null, new Neighbor(3, null));
					
//					g.adjLists = new Vertex[3];
//					g.adjLists[0] = new Vertex("A", null, new Neighbor(1, null));
//					g.adjLists[1] = new Vertex("B", null, new Neighbor(2, new Neighbor(0, null)));
//					g.adjLists[2] = new Vertex("C", null, new Neighbor(1, null));
//
//				
				g.print();
				g.connectors();
			}else{
				break;
			}
		}
	}
}