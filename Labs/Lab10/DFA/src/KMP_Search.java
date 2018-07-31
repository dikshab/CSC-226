import edu.princeton.cs.algs4.*;

public class KMP_Search {

	public static void main(String[] args) {
		In in = new In(args[0]);
		String txt = in.readAll();
		String pat = args[1];
		
		KMP kmp = new KMP(pat);
		int index = kmp.search(txt);
		
		if (index < txt.length()) {
			StdOut.println("Pattern found at index: " + index);
		}
		else {
			StdOut.println("Pattern wasn't found.");
		}
		
	}

}
