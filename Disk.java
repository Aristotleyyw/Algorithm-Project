package Graph;

import java.applet.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import Graph.Square.node;

public class Disk extends Applet{

	int N=4000;
	int avedegree=128;
	
	public void paint(Graphics g)
	{
		//define node
		class node{
			int x,y;
			int color=0;
			public ArrayList<node> next = null;			
			public void addAdj(node ver){
		        if(next == null)next = new ArrayList<node>() ;
		        next.add(ver);
		    }
			public ArrayList<node> getAdj(){
		        return next;
		    }
			public int returndegree(){
				return next.size();
			}
		}
		
		//ArrayList of all nodes
		ArrayList<node> l=new ArrayList<node>();
		node[] list=new node[N];
		
		//draw random points
		g.setColor(Color.RED);
		int d=1000;
		g.drawOval(50, 50, d, d);
		Random r=new Random(100);
		int x,y;
		int[] px=new int[2*N];
		int[] py=new int[2*N];
		int i=0;
		while(i<N){
			x=r.nextInt(1000)+50;
			y=r.nextInt(1000)+50;
			double R = Math.sqrt(Math.pow((Math.abs(x-550)),2) + Math.pow((Math.abs(y-550)),2));
           if(R<500)
           {
        	  
            g.setColor(Color.ORANGE);
			px[i]=x;
			py[i]=y;
			list[i]= new node();
			list[i].x=x;
			list[i].y=y;
			l.add( list[i]);
			i++;
			
           }
           
		}
		
		 //censor coverage
		 //draw lines
		 double dis = Math.sqrt((avedegree*500*500)/N);
		 for(int ii = 0; ii < N; ii ++){
	         	for(int j = 0; j < N; j ++){
	         		
	         		
	         		double dd = Math.sqrt(Math.pow((Math.abs(px[ii]-px[j])),2) + Math.pow((Math.abs(py[ii]-py[j])),2));
	              
	                if(dd<dis)
	                {
	                	g.setColor(Color.YELLOW);
	                	g.drawLine(px[ii], py[ii], px[j],py[j]);//draw lines!
	                	System.out.println(ii+" "+j);
	                	list[ii].addAdj(list[j]);
	                	
	                }
	               
	         		
	         	}
	         	}
		 System.out.println("SIZE"+l.size());
		 for(int ii = 0; ii < N; ii ++){
			 System.out.println(ii+":"+l.get(ii).returndegree());
		 }
		 
		 //find max and min degree nodes
		   class findMaxMinnode {
	            int mindegree = 10000, maxdegree = 1;
	            int minnum = 0, maxnum = 0;
	            int smu = 0;

	            public void paint() {

	                for (int c = 0; c < N; c++) {
	                    smu = smu + l.get(c).returndegree();
	                    if (l.get(c).returndegree() > maxdegree) {
	                        maxdegree = l.get(c).returndegree();
	                        maxnum = c;
	                    } else if (l.get(c).returndegree() < mindegree) {
	                        mindegree = l.get(c).returndegree();
	                        minnum = c;
	                    }
	                    //System.out.println(list[c].returndegree());
	                }
	                int avg = smu / N;
	                System.out.println("max:" + maxnum + " min:" + minnum + " average:" + avg);
	                g.setColor(Color.BLUE);
	                ArrayList<node> maxarr = new ArrayList<node>();
	                maxarr = l.get(maxnum).next;
	                for (int i = 0; i < l.get(maxnum).returndegree(); i++) {
	                    g.fillOval(maxarr.get(i).x - 8, maxarr.get(i).y - 8, 15, 15);//paint the nodes connected to the max degree node in blue
	                }
	                g.setColor(Color.cyan);
	                g.fillOval(l.get(maxnum).x - 8, l.get(maxnum).y - 8, 15, 15);//paint the max degree node in cyan

	                g.setColor(Color.ORANGE);
	                ArrayList<node> minarr = new ArrayList<node>();
	                minarr = l.get(minnum).next;
	                for (int i = 0; i < l.get(minnum).returndegree(); i++) {
	                    g.fillOval(minarr.get(i).x - 10, minarr.get(i).y - 10, 20, 20);//paint the nodes connected to min degree node in orange
	                }

	                g.setColor(Color.GREEN);
	                g.fillOval(l.get(minnum).x - 10, l.get(minnum).y - 10, 20, 20);//paint the min degree node in green

	            }
	        }
	     
		   findMaxMinnode f = new findMaxMinnode();
	       f.paint();
	        
		//find the vertex order through small least algorithm
		 class sml
		 {
			 ArrayList<node> x=new ArrayList<node>();
			 public ArrayList<node> findsmall(ArrayList<node> n)
			 {
				 int num=n.size();
				 int min=n.get(0).returndegree();
				 int mm=0;				 
				 for(int i=0;i<num;i++)
				 {
					 if(n.get(i).returndegree()<min)
					 {
						 min=n.get(i).returndegree();
						 mm=i;
						 
					 }
				 }
				
				 x.add(n.get(mm));
				 n.remove(mm);
				 if(n.size()>0)
				 return findsmall(n);
				 else
					 return x;	
			 }
			 
		 }
		 sml s=new sml();
		 ArrayList<node> arrsml=new ArrayList<node>();
		 arrsml=s.findsmall(l);
		 int[] allcolor=new int[arrsml.size()];
		 
		 
		//color node(1): give the color number to each node
		for(int iii=arrsml.size()-1;iii>=0;iii--)
		{
			int color_num = 1;
			boolean flag = true;
			while (flag) {
				//determine smallest available color number
				for (int ii=0;ii<arrsml.get(iii).returndegree();ii++) {
					
					if (arrsml.get(iii).next.get(ii).color == color_num) {
						color_num++;
						flag = false;
						break;
					}
				}

				if (flag) {
					arrsml.get(iii).color = color_num;
					
					flag = false;
				} else
					flag = true;
			}
		}	
			
		//color  node (2): record the amount of different color		
		int z=arrsml.size();
		int[] numofcolor=new int[100];
		System.out.println(z);
		for(int in=0;in<z;in++){
			int ii=arrsml.get(in).color;
			numofcolor[ii]++;
			//co[i].n_color=ii;
			//co[i].account++;
			g.setColor(new Color((71*ii)%255,(73*ii)%255,(23*ii)%255));
			g.fillOval(arrsml.get(in).x-5, arrsml.get(in).y-5, 10, 10);
		}//color the node and record the amount of different color
		  
		
		
		
		//draw backbone
		class Backbone{
			
			int[] maxfour=new int[4];
			int[] maxfournum=new int[4];
			
			 ArrayList<ArrayList<node>> arrc=new ArrayList<ArrayList<node>>();
			 ArrayList<node> a=new ArrayList<node>();
			 ArrayList<node> b=new ArrayList<node>();
			 ArrayList<node> c=new ArrayList<node>();
			 ArrayList<node> d=new ArrayList<node>();
			
			public void findmaxfourColor(int[] numofcolor){
			for(int j=0;j<4;j++){
			for(int i=0;i<numofcolor.length;i++){
				if(numofcolor[i]>maxfour[j])
				{
					maxfour[j]=numofcolor[i];
					maxfournum[j]=i;
				}
					
			}
			numofcolor[maxfournum[j]]=0;
			}
			
			for(int j=0;j<4;j++){
			System.out.println("the amount of this color:"+maxfour[j]+" color num:"+maxfournum[j]);
			}
			 
			}
			 public void findmostedges(ArrayList<node> arrsml,int z){
				 arrc.add(c);arrc.add(d);arrc.add(a);arrc.add(b);
				 for(int j=0;j<4;j++){
					 for(int i=0;i<z;i++){
						 if(arrsml.get(i).color==maxfournum[j])
						 {
							arrc.get(j).add(arrsml.get(i));
						 }
						 
					 }
				 }
				 int[] acc=new int[16];//
				
				 for(int j=0;j<4;j++){
					 for(int i=j+1;i<=3;i++){
						 for(int jj=0;jj<arrc.get(i).size();jj++){
							 ArrayList<node> next=arrc.get(i).get(jj).next;
							 for (node n:arrc.get(j)){
								 if(next.contains(n))
								 {
									 acc[(j+1)*i]++;
								 }
								 
							 }
						 }
						
					 }
					
				 }
				 for(int j=0;j<16;j++){
					 System.out.println(j+"edges:"+acc[j]);
				 }
				 
			 }
			
			
			
			
		}
		
		 Backbone b=new Backbone();
		 b.findmaxfourColor(numofcolor);
		 b.findmostedges(arrsml,N);
		 g.setColor(Color.BLACK);
		 g.drawOval(1150, 50, 1000, 1000);
			
	     // find the largest bipartite in the graph and draw backone
		 for(int jj=0;jj<b.b.size();jj++){
			 ArrayList<node> next=b.b.get(jj).next;
			 for (node n:b.a){
				 if(next.contains(n))
				 {
					 g.setColor(Color.orange);
					 Graphics2D g2 = (Graphics2D)g;  //Graphics
			         g2.setStroke(new BasicStroke(4.0f)); 
	                 g.drawLine(n.x+1100,n.y,b.b.get(jj).x+1100,b.b.get(jj).y);
	                 g.setColor(Color.green);
	                 g.fillOval(n.x+1100-5, n.y-5, 10, 10);
	                 g.setColor(Color.yellow);
	                 g.fillOval(b.b.get(jj).x+1100-5, b.b.get(jj).y-5, 10, 10);
				 }
				 
			 }
			 
			 
	}

}}
