import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class Neighbor {
	public int vertexNum;
	public Neighbor next;
	public Neighbor(int vnum, Neighbor nbr) {
		this.vertexNum = vnum;
		next = nbr;
	}
}

class Vertex {
	String name;
	String school;
	Neighbor adjList;
	Vertex(String name, String school, Neighbor neighbors) {
		this.name = name;
		this.school = school;
		this.adjList = neighbors;
	}
	
	@Override
	public String toString() {
		return "name=" + name + ", school=" + school + ", adjList=" + adjList;
	}
	
	
}


 public class Graph {

	 Vertex[] adjLists;


	 public Graph(String file) throws FileNotFoundException {

		 Scanner sc = new Scanner(new File(file));
//		 while(sc.hasNext()){
//			 System.out.println(sc.next());
//		 }
		 
		 adjLists = new Vertex[sc.nextInt()];
		 sc.nextLine(); //Skips empty
//		 System.out.println(adjLists.length);
		 
		 // read vertices
		 for (int v=0; v < adjLists.length; v++) {
			 String str = sc.nextLine().trim();
			 String name = str.substring(0, str.indexOf('|'));
//			 System.out.println(str);
			 str = str.substring(str.indexOf('|') + 1);
//			 System.out.println(name);
//			 System.out.println(str);
//			 System.out.println("________________________________");
//			 System.out.println(str.charAt(0));
			 if(str.charAt(0) == 'y'){
//				 System.out.println(str.substring(2));
				 adjLists[v] = new Vertex(name, str.substring(2), null);
			 }else{
//				 System.out.println("null");
				 adjLists[v] = new Vertex(name, null, null);
			 }
		 }

		 for (int i = 0; i < adjLists.length; i++){
			 System.out.println(adjLists[i].toString());
		 }
		 
//		 // read edges
//		 while (sc.hasNext()) {
//
//			 // read vertex names and translate to vertex numbers
//			 int v1 = indexForName(sc.next());
//			 int v2 = indexForName(sc.next());
//
//			 // add v2 to front of v1's adjacency list and
//			 // add v1 to front of v2's adjacency list
//			 adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
//			 adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);			 
//		 }
	 }

	 public int countEdges() {
		 int edges=0;
		 for (int v=0; v < adjLists.length; v++) {
			 for (Neighbor n=adjLists[v].adjList;
					 n != null; n=n.next) {
				 edges++;
			 }
		 }
			 edges /= 2;
		 return edges;
	 }

	 int indexForName(String name) {
		 for (int v=0; v < adjLists.length; v++) {
			 if (adjLists[v].name.equals(name)) {
				 return v;
			 }
		 }
		 return -1;
	 }	

	 public void print() {
		 System.out.println();
		 for (int v=0; v < adjLists.length; v++) {
			 System.out.print(adjLists[v].name);
			 for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
				 System.out.print(" --> " + adjLists[nbr.vertexNum].name);
			 }
			 System.out.println("\n");
		 }
	 }

	 public void dfs() {
		 boolean[] visited = new boolean[adjLists.length];
		 for (int v=0; v < visited.length; v++) {
			 visited[v] = false;
		 }
		 for (int v=0; v < visited.length; v++) {
			 if (!visited[v]) {
				 System.out.println("\nSTARTING AT " + adjLists[v].name + "\n");
				 dfs(v, visited);
			 }
		 }
	 }
	 
	 // recursive dfs
	 private void dfs(int v, boolean[] visited) {
		 visited[v] = true;
		 System.out.println("\tvisiting " + adjLists[v].name);
		 for (Neighbor e=adjLists[v].adjList; e != null; e=e.next) {
			 if (!visited[e.vertexNum]) {
				 System.out.println("\t" + adjLists[v].name + "--" + adjLists[e.vertexNum].name);
				 dfs(e.vertexNum, visited);
			 }
		 }
	 }
//     
//	 /**
//	  * @param args
//	  */
//	 public static void main(String[] args) 
//			 throws IOException {
//		 // TODO Auto-generated method stub
//		 Scanner sc = new Scanner(System.in);
//		 System.out.print("Enter graph input file name: ");
//		 String file = sc.nextLine();
//		 Graph graph = new Graph(file);
//		 graph.print();
//		 
//		 System.out.println("Doing DFS...");
//	     graph.dfs();
//	 }
 }
