/* PrimVsKruskal.java
   CSC 226 - Summer 2018
   Assignment 2 - Prim MST versus Kruskal MST Template
   
   The file includes the "import edu.princeton.cs.algs4.*;" so that yo can use
   any of the code in the algs4.jar file. You should be able to compile your program
   with the command
   
	javac -cp .;algs4.jar PrimVsKruskal.java
	
   To conveniently test the algorithm with a large input, create a text file
   containing a test graphs (in the format described below) and run
   the program with
   
	java -cp .;algs4.jar PrimVsKruskal file.txt
	
   where file.txt is replaced by the name of the text file.
   
   The input consists of a graph (as an adjacency matrix) in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry G[i][j] >= 0.0 of the adjacency matrix gives the weight (as type int) of the edge from 
   vertex i to vertex j (if G[i][j] is 0.0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that G[i][j]
   is always equal to G[j][i].


   R. Little - 06/22/2018
*/

//import edu.princeton.cs.algs4.*;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
	 

//Do not change the name of the PrimVsKruskal class
public class PrimVsKruskal{

/* PrimVsKruskal(G)
	Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
	determine if the minimum spanning tree of G found by Prim's algorithm is equal to 
	the minimum spanning tree of G found by Kruskal's algorithm.
	
	If G[i][j] == 0.0, there is no edge between vertex i and vertex j
	If G[i][j] > 0.0, there is an edge between vertices i and j, and the
	value of G[i][j] gives the weight of the edge.
	No entries of G will be negative.
*/
	public boolean PrimVsKruskal(int[][] G){
		int n = G.length;
		/* Build the MST by Prim's and the MST by Kruskal's */
		/* (You may add extra methods if necessary) */
	
		/* ... Your code here ... */
		primMST(G);
                KruskalMST(G);
		/* Determine if the MST by Prim equals the MST by Kruskal */
		boolean pvk = true;
		//if(primMST(G)== KruskalMST(G))
                    pvk = true;
		return pvk;	
	}
		// A utility function to print the constructed MST stored in
		// parent[]
    public void printMST(int[] parent, int n, int[][] graph)
    {
        int Vertex = graph.length;
        System.out.println("Edge   Weight");
        for (int i = 1; i < Vertex; i++)
            System.out.println(parent[i]+" - "+ i+"    "+graph[i][parent[i]]);
    }
 
    // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    public void primMST(int graph[][])
    {
        int Vertex = graph.length;
        // Array to store constructed MST
        int parent[] = new int[Vertex];
 
        // Key values used to pick minimum weight edge in cut
        int key[] = new int [Vertex];
 
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[Vertex];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < Vertex; i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
 
        // The MST will have V vertices
        for (int count = 0; count < Vertex-1; count++)
        {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < Vertex; v++)
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v]!=0 && mstSet[v] == false &&
                    graph[u][v] <  key[v])
                {
                    parent[v]  = u;
                    key[v] = graph[u][v];
                }
        }
    
 
        // print the constructed MST
        printMST(parent, Vertex, graph);
    }

    public int minKey(int key[], Boolean mstSet[])
    {
        int Vertex = key.length;
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < Vertex; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
     
  // A class to represent a graph edge
    public class Edge implements Comparable<Edge>
    {
        int src, dest, weight;
 
        // Comparator function used for sorting edges 
        // based on their weight
        @Override
        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    };
 
    // A class to represent a subset for union-find
    public class subset
    {
        int parent, rank;
    };
 
    int V, E;    // V-> no. of vertices & E->no.of edges
    Edge edge[]; // collection of all edges
 
    // A utility function to find set of an element i
    // (uses path compression technique)
    int find(subset subsets[], int i)
    {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
 
    // A function that does union of two sets of x and y
    // (uses union by rank)
    public void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
        // If ranks are same, then make one as root and increment
        // its rank by one
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
 
    // The main function to construct MST using Kruskal's algorithm
    public Edge[] KruskalMST(int[][] graph)
    {
        int vertex = graph.length;
        Edge result[] = new Edge[vertex];  // Tnis will store the resultant MST
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i=0; i<vertex; ++i)
            result[i] = new Edge();
 
        // Step 1:  Sort all the edges in non-decreasing order of their
        // weight.  If we are not allowed to change the given graph, we
        // can create a copy of array of edges
        Arrays.sort(edge);
 
        // Allocate memory for creating V ssubsets
        subset subsets[] = new subset[vertex];
        for(i=0; i<vertex; ++i)
            subsets[i]=new subset();
 
        // Create V subsets with single elements
        for (int v = 0; v < vertex; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;  // Index used to pick next edge
 
        // Number of edges to be taken is equal to V-1
        while (e < vertex - 1)
        {
            // Step 2: Pick the smallest edge. And increment 
            // the index for next iteration
            Edge next_edge = new Edge();
            next_edge = edge[i++];
 
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
 
            // If including this edge does't cause cycle,
            // include it in result and increment the index 
            // of result for next edge
            if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }
 
        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in " + 
                                     "the constructed MST");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- " + 
                   result[i].dest+" == " + result[i].weight);
    
        return result;
    }
    
    public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int n = s.nextInt();
		int[][] G = new int[n][n];
		int valuesRead = 0;
		for (int i = 0; i < n && s.hasNextDouble(); i++){
			for (int j = 0; j < n && s.hasNextDouble(); j++){
				G[i][j] = (int) s.nextDouble();
				if (i == j && G[i][j] != 0.0) {
					System.out.printf("Adjacency matrix contains self-loops.\n");
					return;
				}
				if (G[i][j] < 0.0) {
					System.out.printf("Adjacency matrix contains negative values.\n");
					return;
				}
				if (j < i && G[i][j] != G[j][i]) {
					System.out.printf("Adjacency matrix is not symmetric.\n");
					return;
				}
				valuesRead++;
			}
		}
		
		if (valuesRead < n*n){
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}	
		
        //boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n");
    }
}