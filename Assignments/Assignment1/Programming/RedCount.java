import java.util.NoSuchElementException;
import javafx.scene.Node;
import java.io.*;
import java.util.*;
public class RedCount {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

   public Node root;     // root of the BST
   public class Node {
        public int key;           // key
        public Node left, right;  // links to left and right subtrees
        public boolean color;     // color of parent link
        public int size;          // subtree count

        public Node(int key, boolean color, int size) {
            this.key = key;
            this.color = color;
            this.size = size;
        }
    }
	
	
	  // is node x red; false if x is null ?
    public static boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

     // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 


    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(int key) {

        root = put(root, key);
        root.color = BLACK;
    }
	
    //count number of red nodes
    public static int countRed(Node node) {
	int numRed = 0;
	if (node == null) {
            return 0;
	}
	numRed += countRed(node.left);
	numRed += countRed(node.right);

	if(isRed(node)){
        	numRed++;
	}
	return numRed;
    }
	
    //count number of black nodes
    public static int countBlack(Node node) {
        int numBlack = 0;
        if (node == null) {
            return 0;
        }
	numBlack += countBlack(node.left);
        numBlack += countBlack(node.right);

	if(!isRed(node)){
            numBlack++;
	}
        return numBlack;
    }

    
    public static float percentRed(int a, int b){
        int total;
        float percent;
      	total = a+b;
        percent = (b/total)*100;
		System.out.print("Percent of red nodes in tree is\n" +percent);
	return percent;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, int key) { 
        if (h == null) return new Node(key, RED, 1);
        int cmp = key - h.key;
        if      (cmp < 0) h.left  = put(h.left,  key); 
        else if (cmp > 0) h.right = put(h.right, key); 
        else              h.key   = key;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

	
	
   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
		
			
			
	public static void main (String args[]) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(args[0])); //provide file name from outside
		int counter = 0; //keep track of how many integers in the file
		while(scan.hasNextInt()) {
			counter++;
			scan.nextInt();
		}
		Scanner scan2 = new Scanner(new File(args[0]));
			int arr[] = new int[counter];
			for(int i=0;i<counter;i++){
				arr[i]=scan2.nextInt(); //fill the array with the integers
			}
			
			
            double d;
			//d = (double) percentRed()  
            
           
    }
}