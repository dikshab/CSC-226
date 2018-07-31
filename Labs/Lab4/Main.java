import edu.princeton.cs.algs4.*;

public class Main {
	public static void main(String[] args) {
		EdgeWeightedGraph G = new EdgeWeightedGraph(5);
		
		Edge e1 = new Edge(2, 4, .9);
		
		
		Edge e2 = new Edge(4, 3, .7);
		Edge e3 = new Edge(1, 3, .6);
		Edge e4 = new Edge(2, 1, .3);
		Edge e5 = new Edge(0, 1, .5);
		Edge e6 = new Edge(0, 2, .4);
		Edge e7 = new Edge(1, 4, .8);
		
		
		G.addEdge(e1);
		G.addEdge(e2);
		G.addEdge(e3);
		G.addEdge(e4);
		G.addEdge(e5);
		G.addEdge(e6);
		G.addEdge(e7);
		
		boolean[] marked = new boolean[G.V()];
		
		
		Queue<Edge> mst = new Queue<Edge>();
		
		MinPQ<Edge> pq = new MinPQ<Edge>();
		
		marked[0] = true;
		
		
		for (Edge e : G.adj(0)) {
			pq.insert(e);
		}
		
		/*
		Edge se1 = pq.delMin();
		
		int u = se1.either();
		int v = se1.other(u);
		if (!marked[u] || !marked[v]) {
			mst.enqueue(se1);
			if (!marked[u]) {
				marked[u] = true;
				for (Edge e : G.adj(u)) {
					if (!marked[e.other(u)]) pq.insert(e);
				}
			}
			if (!marked[v]) {
				marked[v] = true;
				for (Edge e : G.adj(v)) {
					if (!marked[e.other(v)]) pq.insert(e);
				}
			}
		}
		
		Edge se2 = pq.delMin();
		u = se2.either();
		v = se2.other(u);
		if (!marked[u] || !marked[v]) {
			mst.enqueue(se2);
			if (!marked[u]) {
				marked[u] = true;
				for (Edge e : G.adj(u)) {
					if (!marked[e.other(u)]) pq.insert(e);
				}
			}
			
			if (!marked[v]) {
				marked[v] = true;
				for (Edge e : G.adj(v)) {
					if (!marked[e.other(v)]) pq.insert(e);
				}
			}
		}
		
		Edge se3 = pq.delMin();
		u = se3.either();
		v = se3.other(u);
		if (!marked[u] || !marked[v]) {
			mst.enqueue(se3);
			if (!marked[u]) {
				marked[u] = true;
				for (Edge e : G.adj(u)) {
					if (!marked[e.other(u)]) pq.insert(e);
				}
			}
			
			if (!marked[v]) {
				marked[v] = true;
				for (Edge e : G.adj(v)) {
					if (!marked[e.other(v)]) pq.insert(e);
				}
			}
		}
		
		Edge se4 = pq.delMin();
		u = se4.either();
		v = se4.other(u);
		if (!marked[u] || !marked[v]) {
			mst.enqueue(se4);
			if (!marked[u]) {
				marked[u] = true;
				for (Edge e : G.adj(u)) {
					if (!marked[e.other(u)]) pq.insert(e);
				}
			}
			
			if (!marked[v]) {
				marked[v] = true;
				for (Edge e : G.adj(v)) {
					if (!marked[e.other(v)]) pq.insert(e);
				}
			}
		}
		
		Edge se5 = pq.delMin();
		u = se5.either();
		v = se5.other(u);
		if (!marked[u] || !marked[v]) {
			mst.enqueue(se5);
			if (!marked[u]) {
				marked[u] = true;
				for (Edge e : G.adj(u)) {
					if (!marked[e.other(u)]) pq.insert(e);
				}
			}
			
			if (!marked[v]) {
				marked[v] = true;
				for (Edge e : G.adj(v)) {
					if (!marked[e.other(v)]) pq.insert(e);
				}
			}
		}
		*/
		
		while(!pq.isEmpty()) {
			Edge e = pq.delMin();
			int s = e.either();
			int t = e.other(s);
			if (marked[s] && marked[t]) continue;
			mst.enqueue(e);
			if (!marked[s]) {
				marked[s] = true;
				for (Edge f : G.adj(s)) {
					if (!marked[f.other(s)]) pq.insert(f);
				}
			}
			if (!marked[t]) {
				marked[t] = true;
				for (Edge f : G.adj(t)) {
					if (!marked[f.other(t)]) pq.insert(f);
				}
			}
		}
		
		double weight = 0;
		for (Edge e : mst) {
			System.out.println(e.toString());
			weight += e.weight();
		}
		System.out.println("weight: " + weight);
		
		
		
		
		
		
		LazyPrimMST primmst = new LazyPrimMST(G);
		
		System.out.println(primmst.weight());
	}
}
