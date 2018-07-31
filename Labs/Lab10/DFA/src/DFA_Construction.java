

import edu.princeton.cs.algs4.*;
public class DFA_Construction {

	public static void main(String[] args) {
		String pat = "ABRACADABRA";
		int R = 256;
		int M = pat.length();
		
		int[][] dfa = new int[R][M];
		
		dfa[pat.charAt(0)-65][0] = 1;

		for (int x = 0, j = 1; j < M; ++j) {
			for (int c = 0; c < R; ++c) {
				dfa[c][j] = dfa[c][x];
			}
			dfa[pat.charAt(j)-65][j] = j + 1;
			x = dfa[pat.charAt(j)-65][x];
		}
	
		StdOut.println("   0 1 2 3 4 5 6 7 8 9 10");
		StdOut.print("A: ");
		for (int i = 0; i < M; ++i) {
			StdOut.print(dfa['A'-65][i] + " ");
		}
		StdOut.println();
		StdOut.print("B: ");
		for (int i = 0; i < M; ++i) {
			StdOut.print(dfa['B'-65][i] + " ");
		}
		StdOut.println();
		StdOut.print("C: ");
		for (int i = 0; i < M; ++i) {
			StdOut.print(dfa['C'-65][i] + " ");
		}
		StdOut.println();
		StdOut.print("D: ");
		for (int i = 0; i < M; ++i) {
			StdOut.print(dfa['D'-65][i] + " ");
		}
		StdOut.println();
		StdOut.print("R: ");
		for (int i = 0; i < M; ++i) {
			StdOut.print(dfa['R'-65][i] + " ");
		}
		

	}

}
