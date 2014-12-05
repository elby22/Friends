import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	 
	 public Graph() {
	}

	 public Graph(String file) throws FileNotFoundException {

		 Scanner sc = new Scanner(new File(file));
		 
		 adjLists = new Vertex[sc.nextInt()];
		 sc.nextLine(); //Skips empty line
		 
		 // read vertices
		 for (int v=0; v < adjLists.length; v++) {
			 String str = sc.nextLine().trim();
			 String name = str.substring(0, str.indexOf('|'));
			 str = str.substring(str.indexOf('|') + 1);
			 if(str.charAt(0) == 'y'){
				 adjLists[v] = new Vertex(name, str.substring(2), null);
			 }else{
				 adjLists[v] = new Vertex(name, null, null);
			 }
		 }
		 
		 // read edges
		 while (sc.hasNext()) {

			 // read vertex names and translate to vertex numbers
			 String str = sc.next();
			 int v1 = indexForName(str.substring(0, str.indexOf('|')));
			 int v2 = indexForName(str.substring(str.indexOf('|') + 1));

			 // add v2 to front of v1's adjacency list and
			 // add v1 to front of v2's adjacency list
			 adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
			 adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);			 
		 }
	 }
	 
	 //For use with clique methods - Keeps only names associated with a particular school
	 public Graph(String file, String school) throws FileNotFoundException {

		 Scanner sc = new Scanner(new File(file));
		 
		 //How many people associated with a school
		 int count = 0;
		 int endI = sc.nextInt();
		 sc.nextLine(); //Skips empty line
		 for (int i = 0; i < endI; i++){
			 if(sc.nextLine().contains(school)) count++;
		 }

		 adjLists = new Vertex[count];
		 
		 //Reinitialize scanner and find people only in school
		 sc = new Scanner(new File(file));
		 int numV = sc.nextInt();
		 sc.nextLine(); //Skips empty line
		 System.out.println(numV);
//		  read vertices
		 System.out.println(numV);
		 System.out.println(adjLists.length);
		 int adjIndex = 0;
		 for (int v=0; v < numV; v++) {
			 String str = sc.nextLine().trim();
			 String name = str.substring(0, str.indexOf('|'));
			 str = str.substring(str.indexOf('|') + 1);
			 if(str.charAt(0) == 'y'){
				 String sch = str.substring(2);
				 if(sch.equals(school)){
					 adjLists[adjIndex] = new Vertex(name, school, null);
					 adjIndex++;
				 }
			 }
		 }
		 
		 // read edges
		 while (sc.hasNext()) {
			 // read vertex names and translate to vertex numbers
			 String str = sc.next();
			 boolean v1Present = false;
			 boolean v2Present = false;
			 String v1Name = str.substring(0, str.indexOf('|'));
			 String v2Name = str.substring(str.indexOf('|') + 1);
			 int v1 = indexForName(v1Name);
			 int v2 = indexForName(v2Name);
			 
			 for(int v = 0; v < adjLists.length; v++){
				 if(adjLists[v].name.equals(v1Name)) v1Present = true;
			 }
			 
			 for(int v = 0; v < adjLists.length; v++){
				 if(adjLists[v].name.equals(v2Name)) v2Present = true;
			 }
			 
			 if(v1Present && v2Present){
				 System.out.println(true);
				 // add v2 to front of v1's adjacency list and
				 // add v1 to front of v2's adjacency list
				 adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
				 adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);
			 }
		 }
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
	 
	 int indexForName(Graph g, String name) {
		 for (int v=0; v < g.adjLists.length; v++) {
			 if (g.adjLists[v].name.equals(name)) {
				 return v;
			 }
		 }
		 return -1;
	 }	
	 
	 String nameForIndex(Graph g, int index){
		 if(index < g.adjLists.length){
			 return g.adjLists[index].name;
		 }
		 return null;
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
	 //This makes an arraylist of Vertex[], which is essentially a graph.
	 //For every new clique, a new graph is generated and is returned inside of the arraylist
	 //TODO Correct output - waiting on email from sesh
	 public ArrayList<Graph> cliqueGraph() {
		 ArrayList<Graph> graphs = new ArrayList<Graph>();
		 ArrayList<Vertex> verticies = null;
		 boolean[] visited = new boolean[adjLists.length];
		 for (int v=0; v < visited.length; v++) {
			 visited[v] = false;
		 }
		 for (int v=0; v < visited.length; v++) {
			 if (!visited[v]) {
				 System.out.println("\nSTARTING AT " + adjLists[v].name + "\n");
				 verticies = new ArrayList<Vertex>();
				 verticies.add(adjLists[v]);
				 System.out.print(adjLists[v].name + "--");
				 cliqueDfs(v, visited, verticies);
				 Graph g = new Graph();
				 g.adjLists = new Vertex[verticies.size()];
				 for(int i = 0; i < g.adjLists.length; i++){
					 g.adjLists[i] = verticies.get(i);
				 }
				 graphs.add(g);
			 }
		 }
		 //Map new vertex numbers to new graphs
		 for(int i = 0; i < graphs.size(); i++){
			 Vertex[] vs = graphs.get(i).adjLists;
			 for(int j = 0; j < vs.length; j++){
				 for (Neighbor e=vs[j].adjList; e != null; e=e.next) {
					 String oldName = adjLists[e.vertexNum].name;
					 e.vertexNum = indexForName(graphs.get(i), oldName);
				 }
			 }				 
		 }
		return graphs;
	 }
	 
	 // recursive dfs, modified to find people in a clique and add them to arraylist verticies
	 private void cliqueDfs(int v, boolean[] visited, ArrayList<Vertex> verticies) {
		 visited[v] = true;
		 for (Neighbor e=adjLists[v].adjList; e != null; e=e.next) {
			 if (!visited[e.vertexNum]) {
				 verticies.add(adjLists[e.vertexNum]);
				 System.out.print(adjLists[e.vertexNum].name + "--");
				 cliqueDfs(e.vertexNum, visited, verticies);
			 }
		 }
	 }
 }
