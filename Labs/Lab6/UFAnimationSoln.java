
import edu.princeton.cs.algs4.*;

public class UFAnimationSoln {
    public static void main (String[] args) {

    	StdOut.println("Enter an integer: ");
    	int N = StdIn.readInt();
    	StdDraw.setScale(-1,N);   	
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.pause(1000);
        StdDraw.setPenColor(StdDraw.WHITE);
        
    	Point[] array = new Point[N*N];
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			array[N*i+j]=new Point(i,j);
    			StdDraw.filledCircle(i,j,0.03);
    		}
    	}
    	StdDraw.pause(500);
    	
    	UF unionFind = new UF(N*N);
    	int i=0;
    	while(i<N*N-1){
    		int p = StdRandom.uniform(N*N);
    		int q = StdRandom.uniform(N*N);
    		StdOut.println(p+"  "+q);
    		if(unionFind.find(p)!=unionFind.find(q)){
    			i++;
    			unionFind.union(p,q);
    			StdOut.println("connected "+i);
    			StdDraw.line(array[p].x,array[p].y,array[q].x,array[q].y);
    			StdDraw.pause(500);
    		}
    	}	
      
    }
}

class Point{
	public int x,y;
	
	public Point(int x, int y){
		this.x=x;
		this.y=y;
	}
}