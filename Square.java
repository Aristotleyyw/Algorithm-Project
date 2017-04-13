package Graph;

import java.applet.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class Square extends Applet {

    int N = 4000;
    int avedegree = 128;

    class node {
        int x, y;
        int color = 0;
        ArrayList<node> next = null;

        public void addAdj(node ver) {
            if (next == null) next = new ArrayList<node>();
            next.add(ver);
        }

        public ArrayList<node> getAdj() {
            return next;
        }

        public int returndegree() {
            return next.size();
        }
    }//node structure


    class xnode {
        public node node;
        public int degree;

        public xnode(node node) {
            this.node = node;
            this.degree = node.returndegree();
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(100, 100, 1000, 1000);//draw rectangle!

        node[] list = new node[N];//list:the list of all nodes
        
        
        ArrayList<node> l = new ArrayList<node>();//ArrayList of all nodes

        int x, y;
        Random r = new Random(100);
        for (int i = 0; i < N; i++) {
            x = r.nextInt(1000) + 100;
            y = r.nextInt(1000) + 100;
            g.drawLine(x, y, x, y);//draw points!
            g.setColor(Color.red);
            //g.fillOval(x - 2, y - 2, 5, 5);

            list[i] = new node();
            list[i].x = x;
            list[i].y = y;
            l.add(list[i]);
            //System.out.println(y);

        }
        double distance = Math.sqrt((avedegree * 1000 * 1000) / (N * 3.141592653));
        System.out.println("R/distance" + distance);

        //double distance=400;
        int edge = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {


                double d = Math.sqrt(Math.pow((Math.abs(list[i].x - list[j].x)), 2) + Math.pow((Math.abs(list[i].y - list[j].y)), 2));//锟斤拷锟斤拷锟斤拷锟�

                if (d < distance) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawLine(list[i].x,list[i].y, list[j].x,list[j].y);//draw lines
                    list[i].addAdj(list[j]);
                    edge++;
                }

            }
        }

        System.out.println("num of edges:" + edge);

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
        //findMaxMinnode f = new findMaxMinnode();
        //f.paint();

        ArrayList xl = new ArrayList<xnode>();
        for (int i = 0; i < N; i++) {
            xl.add(i, new xnode(l.get(i)));

        }

        //find the vertex order through small least algorithm
        class sml {
            ArrayList de = new ArrayList<Integer>();
            ArrayList<node> x = new ArrayList<node>();//save the arraylist in sml order

            public ArrayList<node> findsmall(ArrayList<node> n) {
                int num = n.size();
                int min = n.get(0).returndegree();
                int mm = 0;
                for (int i = 0; i < num; i++) {
                    if (n.get(i).returndegree() < min) {
                        min = n.get(i).returndegree();
                        mm = i;

                    }
                }
                //de.add();
                x.add(n.get(mm));
                de.add(n.get(mm).returndegree());
                for (int i = 0; i < n.size(); i++) {
                    if (n.get(i).next.contains(n.get(mm))) {
                        n.get(i).next.remove(n.get(mm));
                    }

                }
                n.remove(mm);

                if (n.size() > 0)
                    return findsmall(n);
                else
                    return x;
            }

            public ArrayList<node> findsmal_big(ArrayList<node> n) {

                //int min=n.get(0).returndegree();
                //the index of the min degree node
                while (n.size() > 0) {

                    int min = n.get(0).returndegree();
                    int mm = 0;
                    for (int i = 0; i < n.size(); i++) {
                        if (n.get(i).returndegree() < min) {
                            min = n.get(i).returndegree();
                            mm = i;

                        }
                    }
                    System.out.println(mm);
                    System.out.println(n.size());
                    x.add(n.get(mm));
                    de.add(n.get(mm).returndegree());
                    for (int i = 0; i < n.size(); i++) {
                        if (n.get(i).next.contains(n.get(mm))) {
                            n.get(i).next.remove(n.get(mm));
                        }

                    }
                    n.remove(mm);

                }
                return x;
            }

            public ArrayList<node> findsmalll(ArrayList<xnode> xl) {
                int index = 0;
                xl.sort(new Comparator<xnode>() {
                    @Override
                    public int compare(xnode o1, xnode o2) {
                        return o1.degree - o2.degree;
                    }
                });
                while (index < N) {
                    x.add(((xnode)xl.get(index)).node);
                    xnode snode=(xnode)xl.get(index);
                    int snode_degree= snode.degree;
                    de.add(snode_degree);
                    for (int i = index+1; i < N; i++) {
                        xnode $node = (xnode)xl.get(i);
                        if ($node.node.next.contains(xl.get(index))) {
                            $node.degree--;

                            for(int j = i - 1; j > index; j --){
                                xnode prevNode = (xnode)xl.get(j);
                                if(prevNode.degree > $node.degree){
                                    xl.set(j, $node);
                                    xl.set(j+1, prevNode);
                                }

                            }

                        }
                    }
                    index++;
                }
                return x;
            }

        }

        for(int i=0; i< N;i++)
        {
        	System.out.println("degree befor sml:"+l.get(i).next.size());
        	
        }
        sml s = new sml();
        
        ArrayList<node> arrsml = new ArrayList<node>();
        
        //ArrayList<node> arrr=new ArrayList<node>();
        //arrsml=s.findsmall(l);//the list of node in order of sml:arrsml
        arrsml = s.findsmalll(xl);
        
        int[] allcolor = new int[arrsml.size()];
        
        //arrsml.get(arrsml.size()-1).color=1;
        int maxdegree = 0;
/*
        for (int i=0;i<xl.size();i++) {
            xnode snode=(xnode)xl.get(i);
            int snodedegree=snode.degree;
            System.out.println("degree:" +snodedegree);
            if ( snodedegree> maxdegree) {
                maxdegree = snodedegree;
            }

        }
        */
        for(Object in:s.de){
            System.out.println("degree:"+in);
            if((int)in>maxdegree)
            {
                maxdegree=(int)in;
            }

        }
        System.out.println("maxdegree:" + maxdegree);

        int colornum = 0;
        for (int i = arrsml.size() - 1; i >= 0; i--) {
            int color_num = 1;
            boolean flag = true;
            while (flag) {
                //determine smallest available color number
                for (int ii = 0; ii < arrsml.get(i).returndegree(); ii++) {

                    if (arrsml.get(i).next.get(ii).color == color_num) {
                        color_num++;
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    arrsml.get(i).color = color_num;

                    flag = false;
                } else
                    flag = true;
            }
            if (colornum < color_num)
                colornum = color_num;

        }

        System.out.println("color num:" + colornum);

        for (int i = arrsml.size() - 1; i >= 0; i--) {
            //System.out.println("color:"+arrsml.get(i).color);
        }


        int z = arrsml.size();
        int[] numofcolor = new int[10000];
        //System.out.println(z);
        for (int i = 0; i < z; i++) {

            int ii = arrsml.get(i).color;
            numofcolor[ii]++;
            //co[i].n_color=ii;
            //co[i].account++;
            g.setColor(new Color((71 * ii) % 255, (73 * ii) % 255, (23 * ii) % 255));

           // g.fillOval(arrsml.get(i).x - 10, arrsml.get(i).y - 10, 20, 20);
        }//color the node and record the amount of different color


        class Backbone {

            int[] maxfour = new int[4];
            int[] maxfournum = new int[4];
            //ArrayList<node> arrsml=s.findsmall(l);
            
            ArrayList<ArrayList<node>> arrc = new ArrayList<ArrayList<node>>();
            ArrayList<node> a = new ArrayList<node>();
            ArrayList<node> b = new ArrayList<node>();
            ArrayList<node> c = new ArrayList<node>();
            ArrayList<node> d = new ArrayList<node>();

            public void findmaxfourColor(int[] numofcolor) {
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < numofcolor.length; i++) {
                        if (numofcolor[i] > maxfour[j]) {
                            maxfour[j] = numofcolor[i];
                            maxfournum[j] = i;
                        }

                    }
                    numofcolor[maxfournum[j]] = 0;
                }

                for (int j = 0; j < 4; j++) {
                    System.out.println("the amount of this color:" + maxfour[j] + " color num:" + maxfournum[j]);
                }

            }

            public void findmostedges(ArrayList<node> arrsml, int z) {
            	 arrc.add(c);
            	 arrc.add(d);
                arrc.add(a);
                arrc.add(b);
               
               
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < z; i++) {
                        if (arrsml.get(i).color == maxfournum[j]) {
                            arrc.get(j).add(arrsml.get(i));
                        }

                    }
                }
                int[] acc = new int[16];//

                for (int j = 0; j < 4; j++) {
                    for (int i = j + 1; i <= 3; i++) {
                        for (int jj = 0; jj < arrc.get(i).size(); jj++) {
                            ArrayList<node> next = arrc.get(i).get(jj).next;
                            for (node n : arrc.get(j)) {
                                if (next.contains(n)) {
                                    acc[(j + 1) * i]++;
                                }

                            }
                        }

                    }

                }
                for (int j = 0; j < 16; j++) {
                    System.out.println(j + "edges:" + acc[j]);
                }

            }


        }

        Backbone b = new Backbone();
        b.findmaxfourColor(numofcolor);
        b.findmostedges(arrsml, N);
        g.setColor(Color.BLACK);
        g.drawRect(1200, 100, 1000, 1000);

        //backbone
        for (int jj = 0; jj < b.b.size(); jj++) {
            ArrayList<node> next = b.b.get(jj).next;
            for (node n : b.a) {
                if (next.contains(n)) {
                    g.setColor(Color.orange);
                    Graphics2D g2 = (Graphics2D) g;  //
                    g2.setStroke(new BasicStroke(4.0f));
                    g.drawLine(n.x + 1100, n.y, b.b.get(jj).x + 1100, b.b.get(jj).y);
                    g.setColor(Color.green);
                    g.fillOval(n.x + 1100 - 10, n.y - 10, 20, 20);
                    g.setColor(Color.YELLOW);
                    g.fillOval(b.b.get(jj).x + 1100 - 10, b.b.get(jj).y - 10, 20, 20);
                }

            }


        }


    }


}
