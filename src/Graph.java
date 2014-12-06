import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;


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
		 
//		  read vertices
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
	 
	 String nameForIndex(int index){
		 if(index < adjLists.length){
			 return adjLists[index].name;
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
	 //This makes an arraylist of Graph
	 //For every new clique, a new graph is generated and is returned inside of the arraylist
	 public ArrayList<Graph> cliqueGraph() {
		 ArrayList<Graph> graphs = new ArrayList<Graph>();
		 ArrayList<Vertex> verticies = null;
		 boolean[] visited = new boolean[adjLists.length];
		 for (int v=0; v < visited.length; v++) {
			 visited[v] = false;
		 }
		 for (int v=0; v < visited.length; v++) {
			 if (!visited[v]) {
				 verticies = new ArrayList<Vertex>();
				 verticies.add(adjLists[v]);
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
				 cliqueDfs(e.vertexNum, visited, verticies);
			 }
		 }
	 }
	 //TODO Test this some more on regular graph, not just clque graph
	 public void exportGraph(){
		 System.out.println(); //New Line
		 Vertex[] verticies = adjLists;
		 System.out.println(verticies.length);
		 
		 for(int i = 0; i < verticies.length; i++){
			 System.out.print(verticies[i].name + "|");
			 if(verticies[i].school != null){
				 System.out.println("y|"+ verticies[i].school);
			 }else{
				 System.out.println("n");
			 }
		 }
		 String name1;
		 String name2;
		 boolean inList = false;
		 ArrayList<String> outList = new ArrayList<String>();
		 for(int i = 0; i < verticies.length; i++){
			 name1 = verticies[i].name;
			 for (Neighbor e=adjLists[i].adjList; e != null; e=e.next) {
				 name2 = nameForIndex(e.vertexNum);
				 for(int n = 0; n < outList.size(); n++){
					 if(outList.get(n).contains(name1) && outList.get(n).contains(name2)){
						 inList = true;
					 }
				 }
				 if(!inList){
					 outList.add(name1+"|"+name2);
				 }
				 inList = false;
			 }
		 }
		 for(int i = 0; i < outList.size(); i++){
			 System.out.println(outList.get(i));
		 }
	 }
	 
	 public void BFS(Graph g, String start, String target){
		 Vertex root = null;
		 Vertex end = null;
		 boolean goodToGo = false;
		 Queue<Vertex> q = new LinkedList<Vertex>();   //Linked List implementation of Queue
		 int v = 0;
		 int z = 0;
		
		 v = indexForName(start);
		 root = adjLists[v];
		 
		 z = indexForName(target);
		 end = adjLists[z];
		 
		 
		 boolean[] visited = new boolean[adjLists.length];   //Keeps track of visited nodes
		 int[] prev = new int[adjLists.length];		     //Keeps track of previous nodes (for back-tracking to find the shortest path)
		 int firstNode = indexForName(root.name);
		 visited[firstNode] = true;			     //Sets first node to "Visited"
		 for (v++ ;v < visited.length; v++) {
			 visited[v] = false;
			 prev[v] = -1;
		 }
		 q.add(root);					//Adds starting position to queue
		 while(!q.isEmpty()){
			 Vertex temp = q.remove();
			 for(Vertex temp3 = temp; temp3.adjList != null; temp3.adjList = temp3.adjList.next){ //Cycles through neighbors of a given Node (we'll call it x)
				 if(visited[temp3.adjList.vertexNum] == false){                       //Searches to see if this neighbor has been visited
					 if(adjLists[temp3.adjList.vertexNum].name.equals(target)){	 //Tests if there is a connection between root and target
						 goodToGo = true;
						 System.out.println("test");
					 }
					 visited[temp3.adjList.vertexNum]= true;    		  //If so, sets visited[neighbor's vertexNum] to true
					 int prevNode = indexForName(temp3.name);									  
					 prev[temp3.adjList.vertexNum] = prevNode;	   	//Then sets prev[neighbor's vertexNum] to the given Node, x
					 Vertex nextNode = adjLists[temp3.adjList.vertexNum];	 //Sets Vertex nextNode to the next Node that is to be added
					 q.add(nextNode);												  //to the queue, then adds it to the queue
				 }
			 }
			 
		 }

		 if(goodToGo == true){
			 printShortestPath(root, end, prev);                 //prints out the shortest path from root to end
		 }else{
			 System.out.println("These people are not connected");
			 return;
		 }
	 }
	 private void printShortestPath(Vertex start, Vertex end, int[] prev){    //finds shortest path
		 Vertex temp = end;
		 Stack<String> hello = new Stack();
		 while(prev[indexForName(temp.name)] != -1){ 
			 hello.push(temp.name);
			 //System.out.println(temp.name);
			 if(temp == start){
				 break;
			 }
			 temp = adjLists[prev[indexForName(temp.name)]];
		 } 
		 
		 while(!hello.isEmpty()){
			 if(hello.size() == 1){
				 System.out.println(hello.pop());
			 }else{
			 System.out.print(hello.pop()+ "--");
			 }
		 }
		 System.out.println();
		 System.out.println();
	 }
 }
