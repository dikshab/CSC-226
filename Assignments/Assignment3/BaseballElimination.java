/* BaseballElimination.java
   CSC 226 - Summer 2018
   Assignment 4 - Baseball Elimination Program
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java BaseballElimination
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test divisions (in the format described below) and run
   the program with
	java -cp .;algs4.jar BaseballElimination file.txt (Windows)
   or
    java -cp .:algs4.jar BaseballElimination file.txt (Linux or Mac)
   where file.txt is replaced by the name of the text file.
   
   The input consists of an integer representing the number of teams in the division and then
   for each team, the team name (no whitespace), number of wins, number of losses, and a list
   of integers represnting the number of games remaining against each team (in order from the first
   team to the last). That is, the text file looks like:
   
	<number of teams in division>
	<team1_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>
	...
	<teamn_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>

	
   An input file can contain an unlimited number of divisions but all team names are unique, i.e.
   no team can be in more than one division.


   R. Little - 07/13/2018
*/

import edu.princeton.cs.algs4.*;
import java.util.*;
import java.io.*;

//Do not change the name of the BaseballElimination class
public class BaseballElimination{
	
	// We use an ArrayList to keep track of the eliminated teams.
    public ArrayList<String> eliminated = new ArrayList<>();

    private final HashMap<String, Integer> teams;
    private final String[] teamArray;
    private final int[] w;
    private final int[] l;
    private final int[] r;
    private final int[][] g;
    public BaseballElimination(Scanner s) {
    // create a baseball division from given file name in format specified below
        In data = new In(s);
        int numTeams = data.readInt();
        teams = new HashMap<>();
        teamArray = new String[numTeams];
        w = new int[numTeams];
        l = new int[numTeams];
        r = new int[numTeams];
        g = new int[numTeams][numTeams];
        int teamId = 0;
        while (!data.isEmpty()) {
            String name = data.readString();
            teams.put(name, teamId);
            teamArray[teamId] = name;
            // read w, l, r
            w[teamId] = data.readInt();
            l[teamId] = data.readInt();
            r[teamId] = data.readInt();
            // real g
            for (int i = 0; i < numTeams; i++) {
                g[teamId][i] = data.readInt();
            }
            teamId += 1;
        }        
    }
    public int numberOfTeams() {
        // number of teams
        return teams.size();
    }
    public Iterable<String> teams() {
        // all teams
        return teams.keySet();
    }
    public int wins(String team) {
        // number of wins for given team
        return w[teams.get(team)];
    }
    public int losses(String team) {
        // number of losses for given team
        return l[teams.get(team)];
    }
    public int remaining(String team) {
        // number of remaining games for given team
        return r[teams.get(team)];
    }
    public int against(String team1, String team2) {
        // number of remaining games between team1 and team2
        return g[teams.get(team1)][teams.get(team2)];
    }
    public boolean isEliminated(String team) {
        // is given team eliminated?
        return certificateOfElimination(team) != null;
    }
    public Iterable<String> certificateOfElimination(String team) {
        // subset R of teams that eliminates given team; null if not eliminated
        int x = teams.get(team);
        int n = numberOfTeams() - 1;
        // trivial
        for (int i = 0; i <= n; i++) {
            if (w[x] + r[x] - w[i] < 0) {
                
                eliminated.add(teamArray[i]);
                return eliminated;
            }
        }
        // maxflow
        int total = n + (n + 1) * (n + 1) + 2;
        int sourceId = total - 2;
        int targetId = total - 1;
        // creating a graph
        FlowNetwork graph = new FlowNetwork(total);
        for (int i = 0; i <= n; i++) {
            if (i == x) continue;
            for (int j = i; j <= n; j++) {
                if (j == x) continue;
                if (g[i][j] > 0) {
                    int gameId = n + (i + 1) * (j + 1) - 1;
                    // source to game
                    graph.addEdge(new FlowEdge(sourceId, gameId, g[i][j]));
                    // game to team
                    graph.addEdge(new FlowEdge(gameId, i, 
                                               Double.POSITIVE_INFINITY));
                    graph.addEdge(new FlowEdge(gameId, j, 
                                               Double.POSITIVE_INFINITY));
                }
            }
            graph.addEdge(new FlowEdge(i, targetId, w[x] + r[x] - w[i]));
        }
        FordFulkerson maxFlow = new FordFulkerson(graph, sourceId, targetId);
        LinkedList<String> list  = null;
        for (FlowEdge edge : graph.adj(targetId)) {
            if (maxFlow.inCut(edge.from())) {
                if (list == null) {
                    list = new LinkedList<>();
                }
                list.add(teamArray[edge.from()]);
            }
        }
        return list;
    }
	
	
	
	/* main()
	   Contains code to test the BaseballElimantion function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
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
		
		BaseballElimination be = new BaseballElimination(s);		
		
		if (be.eliminated.isEmpty())
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}
