

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
class Pair implements Comparable<Pair>{
    int v;
    int wt;
    Pair(int v,int wt){
        this.v=v;
        this.wt=wt;
    }
    @Override
     public int compareTo(Pair obj){
         return this.wt-obj.wt;
         
     }
}



class Grap{
	ArrayList<ArrayList<ArrayList<Integer>>> adj=new ArrayList<ArrayList<ArrayList<Integer>>> ();
	ArrayList<ArrayList<Integer>> list;
	
	void makeVertex(int vertices) {
		for(int i=0;i<vertices;i++) {
			adj.add(new ArrayList<ArrayList<Integer>>());
		}
		for(int i=0;i<vertices;i++) {
	  list=new ArrayList<ArrayList<Integer>>();
		}
		
	}
	void addEdge( int source ,int destination,int weight) {
		ArrayList<Integer> t1=new ArrayList<Integer>();
		ArrayList<Integer> t2=new ArrayList<Integer>();
		 
		t1.add(destination);
		t1.add(weight);
		t2.add(source);
		t2.add(weight);
		adj.get(source).add(t1);
		adj.get(destination).add(t2);	
	}
	//  Dijkstras 
	int[] dijkstra(int V,  int S)
    {
    boolean[] vis = new boolean[V];
       PriorityQueue<Pair> q = new PriorityQueue<>();
       
       q.add(new Pair(S,0));
       int ans[] = new int[V];
       Arrays.fill(ans,10000000);
       ans[S] =0;
       while(q.size()!=0)
       {
           Pair cur = q.remove();
           
           int u= cur.v;
           if(vis[u])
           {
               continue;
           }
           vis[u] = true;
           
           ArrayList<ArrayList<Integer>> neigh = adj.get(u);
           for(ArrayList<Integer> list: neigh)
           {
               int vertex = list.get(0);
               int wt = list.get(1);
               if(ans[vertex]>ans[u]+wt)
               {
                   ans[vertex] = ans[u] +wt;
                   q.add(new Pair(vertex,ans[vertex]));
               }
           }
       }
       return ans;
    }
}

@WebServlet("/flight")
public class getpath extends HttpServlet {
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String source =  req.getParameter("source");
		String destination = req.getParameter("destination");
		 // Storing destinations with vertex numbers
		String source1=source.toLowerCase();
		String destination1=destination.toLowerCase();
		int start = 0 ;
		int end=0;
		switch(source1) {
		case "delhi":start=0;
		break;
		case "dubai" :start=1;
		break;
		case "beijing" :start=2;
		break;
		case "melbourne" :start=3;
		break;
		case "london" : start=4;
		break;
		case "moscow" :start=5;
		break;
		case "tokyo" : start=6;
		break;
		}
		switch(destination1) {
		case "delhi":end=0;
		break;
		case "dubai" :end=1;
		break;
		case "beijing" :end=2;
		break;
		case "melbourne" :end=3;
		break;
		case "london" : end=4;
		break;
		case "moscow" :end=5;
		break;
		case "tokyo" : end=6;
		break;
		
		}
		Grap obj=new Grap();
		int v=7;
		obj.makeVertex(v);
		obj.addEdge(0, 1, 4);
		obj.addEdge(0,2, 6);
		obj.addEdge(0, 3, 12);
		
		obj.addEdge(1, 0, 4);
		obj.addEdge(1, 4, 8);
		
		obj.addEdge(2, 0, 6);
		obj.addEdge(2, 4, 11);
		obj.addEdge(2, 5, 8);
		
		obj.addEdge(3, 0, 12);
		obj.addEdge(3,6 , 10);
		
		obj.addEdge(4, 1, 8);
		obj.addEdge(4, 2, 11);
		
		obj.addEdge(5, 2, 8);
		obj.addEdge(5, 6, 10);
		
		obj.addEdge(6, 4, 10);
		obj.addEdge(6, 5, 10);
		int shortestDistance=0;
		int ans[] =obj.dijkstra(v, start);
		for(int i=0;i<v;i++) {
			if(i==end) {
				shortestDistance=ans[i];
			}
		}
		PrintWriter out=res.getWriter();
		out.println("Sir/Madam your shortest flight from " +source + " to " + destination + " will take " + shortestDistance + " Hrs");
		out.print("\n");
		out.println("Enjoy Your Journey With Flying Go");
	}
}
