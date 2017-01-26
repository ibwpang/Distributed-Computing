import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CMT{

	private int N = 0;
	private int k = 0;
	private String topo = null;

	//we can use level and id to get the distance

	double[] distsumall = new double[51];

	public CMT(int N, int k, String topo){
		this.N = N;
		this.k = k;
		this.topo = topo;

		start();

	}

	public void start(){

		Node_CMT[] node = init(N, k);

		evol(N, k, node, topo);

		//Plot_BTT bplot = new Plot_BTT(distsumall);

	}

	public Node_CMT[] init(int N, int k){

		Node_CMT[] node= new Node_CMT[N];
		int rad = 200;
		//int rad2 = 10;
		double NN = N;
		double theta = 360/NN;
		int center1 = 0;
		int center2 = 200;

		for(int id=0; id<N; id++){

			node[id] = new Node_CMT(k);  //we need to use id-1 instead of id.
			node[id].setid(id);
			if(id<=N/6){
				node[id].setrad(rad);
				node[id].settheta(theta*(id+N/3));
				node[id].setcenter(center2);
			}
			else if(id>(N/6)*5){
				node[id].setrad(rad);
				node[id].settheta(theta*(id-N/3));
				node[id].setcenter(center2);
			}
			else if((N/6<id)&&(id<=(N/6)*5)){
				node[id].setrad(rad);
				node[id].settheta(theta*id);
				node[id].setcenter(center1);
			}
			
			int[] nlist = new int[2*k];
			for(int i=0;i<2*k;i++){
				nlist[i]=0;
			}
			int[] nview = new int[k];
			for(int j=0;j<k;j++){
				nview[j]=0;
			}

			//set nlist
			for(int i=0;i<2*k;i++){
				int x = (int)(Math.random()*(N)); //Math.random():return the value that bigger or equal to 0, and smaller than 1.
				boolean flag = true;
				while(flag){
					for(int ii=0;ii<2*k;ii++){
						if(nlist[ii]==x){          //it is enough to different with the items before it.
							x = (int)(Math.random()*(N));
							flag=true;
							break;   //here is a break, becasue there is no need to check the items behind ii.
						}
						flag=false;
					}
			    }//while
			    nlist[i]=x;
			}
			node[id].setnlist(nlist);

			//set nview
			for(int j=0;j<k;j++){
				int y = (int)(Math.random()*(N));
				boolean flag = true;
				while(flag){
					for(int jj=0;jj<k;jj++){
						if(nview[jj]==y){          //In order to prevent to repeat the item value before it. it is enough to different with the items before it.
							y = (int)(Math.random()*(N)); 
							flag=true;
							break;
						}						
						flag=false;
					}
			    }//while
			    nview[j]=y;
			}
			node[id].setnview(nview);

		}

		double[] newnviewinit = new double[k+1];
		double[] distsuminit = new double[N];
		int[] nviewinit = new int[k];
		for(int v=0; v<N; v++){
			newnviewinit = C_sort_init(v, node, node[v].getnview(), k);
			for(int z=0; z<k; z++){
				nviewinit[z] = (int)newnviewinit[z];
			}
			distsuminit[v] = newnviewinit[k];
		}

		double distsuminitall =	0;
		for(int g=0; g<N; g++){
			distsuminitall = distsuminitall+distsuminit[g];
		}

	    distsumall[0] = distsuminitall;

	    //System.out.println(node[200].getnviewstr());
      	System.out.println(distsumall[0]);
      	System.out.println(" ");

		// System.out.println(node[200].getid());
		// System.out.println(node[200].getrad());
		// System.out.println(node[200].gettheta());
		//System.out.println(node[200].getnliststr());
		//System.out.println(node[200].getnviewstr());

		return node;

	}



	public void evol(int N, int k, Node_CMT[] node, String topo){

		// int currentrad = 1;
		// int targetrad = 0;

		//get currentrad and targetrad
		for(int cycle=1;cycle<=50;cycle++){

			//method 2  //currentrad will increment 1 when cycle is the multiple of 3 and plus 1. The currentrad will not bigger than targetrad always.
			// if(cycle<=5*n){
			// 	targetrad=r[(cycle-1)/5];  //from r[0], change to next r when cycle increment 5. We assume the rising speed of targetrad is faster than the rising speed of currentrad.
			// 	if(((cycle%3)==1)&&(currentrad<targetrad)){
			// 		currentrad++;
			// 	}
			// }
			// if(5*n<cycle){
			// 	targetrad=r[n-1];
			// 	if(((cycle%3)==1)&&(currentrad<targetrad)){
			// 		currentrad++;
			// 	}
			// }

			// System.out.println(currentrad);
			// // System.out.println(targetrad);
			// // System.out.println(topo);
			// //System.out.println("arr to str:"+Arrays.toString(node[200].getnview()));

			// //update the radius in this particular cycle of all nodes
			// for(int idd=0; idd<N; idd++){
			// 	//update the radius of nodes in this particular cycle
			// 	node[idd].setrad(currentrad);  
			// }

			double[] distsum = new double[N];
			//double[] distsumall = new double[50]; //remove to be class variable

			//we will use currentrad as the radius of the nodes in each cycle
			for(int id=0; id<N; id++){

				// if(id == 200){  //this if is just to test as well---1
				// 	System.out.println(node[id].getnliststr());
				// 	System.out.println(node[id].getnviewstr());
				// }

				int[] nview = node[id].getnview();
				int[] nlist = node[id].getnlist();
				int peerid = (int)(Math.random()*(k)); //select one neighbour(peer) to communicate randomly. peerid is between 0~29
				int peer = nview[peerid];  //peer is the id of the node which has a index peerid in node[id]'s nview
				// if(id == 200){  //this if is just to test
				// 	System.out.println("peerid:"+peerid+", peer:"+peer);  
				// }
				int[] peerview = node[peer].getnview();

				//merge the peerview and the nview into nlist.
      			nlist = concat(nview, peerview);

      			//replaced the repeated items in the nlist with another random neighbour
      			for(int p=0;p<2*k;p++){
      				int z=0; 
      				//boolean flag = true;
						for(int q=0;q<2*k;q++){
							if((nlist[q]==nlist[p])&&(p!=q)){   //the item can only be same with another item which is behind of him
								z = (int)(Math.random()*(N));    //it is not possible that there are three same neighbour in the nlist, because both two of its sublist have unique items.
								//flag=false;                      //problem: what if z is still same with another item?
								nlist[q]=z; //q is definitely behind the p
							}
						}
				}
      			//put the new nlist (with no repeated items) into the nlist of node[id]
      			node[id].setnlist(nlist);

    //             if(id == 200){  //this if is just to test as well---2
				// 	System.out.println(node[id].getnliststr());
				// 	System.out.println(node[id].getnviewstr());
				// }

      			//calculate the nearest 30 neighbours in nlist, and put it into nview of node[id]
      			if(topo.equals("C")){
      				double[] newnview = new double[k+1];
      				newnview = D_sort(id, node, node[id].getnlist(), k);
      				for(int t=0;t<k;t++){
      					nview[t] = (int)newnview[t];
      				}
      				
      				distsum[id] = newnview[k];

      			}

      			//put the new nview into the nview of node[id]
      			node[id].setnview(nview);
     			
    //   			if(id == 200){  //this if is just to test as well---3
				// 	//System.out.println(node[id].getnliststr());
				// 	System.out.println(node[id].getnviewstr());
					
				// }
			}

			
			for(int e=0;e<N;e++){
				distsumall[cycle] = distsumall[cycle]+distsum[e];
			}

			System.out.println(distsumall[cycle]);
			System.out.println(" ");

			
			if((cycle==1) || (cycle==5) || (cycle==10) || (cycle==15)){
				//write a file with nview list--------------------------
				C_writenviewfile(node, N, k, topo, cycle); //write a file which contains the neighbour list for each node

				//draw a graph-----------------------
				Graph_CMT cgraph = new Graph_CMT(node, N, k, topo, cycle);
				//dgraph.writeImage(dgraph.bi, dgraph.picType, dgraph.file);
			}
			
		}//50 cycles finished

		//write a file with distance sum---------------------------
		C_writedistfile(distsumall, N, k, topo);

		//draw a plot : init_phase+50_cycles with distsumall-------------------------------
		Plot_CMT cplot = new Plot_CMT(distsumall);

	}



	public void C_writenviewfile(Node_CMT[] node, int N, int k, String topo, int cycle){ //node and cycle will change in different cycles

		try{
			//FileReader file = null;
			//BufferedReader reader = null;
			FileWriter fw = null;
			BufferedWriter bw = null;
			StringBuffer buf = new StringBuffer(); 

			//String[] write = new String[N];

			for(int id=0; id<N; id++){
				buf.append("The neighbour of node"+id+" in cycle"+cycle+" is: "+node[id].getnviewstr()+"\r\n"); // int should be converted to string automatically in java when int is outputed with other strings
			}

			//buf = write;

			fw = new FileWriter(topo+"_N"+N+"_k"+k+"_"+cycle+".txt");
			bw = new BufferedWriter(fw);
			bw.write(buf.toString());
			bw.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}


	public void C_writedistfile(double[] distsumall, int N, int k, String topo){

		try{
			//FileReader file = null;
			//BufferedReader reader = null;
			FileWriter fw = null;
			BufferedWriter bw = null;
			StringBuffer buf = new StringBuffer(); 

			//String[] write = new String[N];

			buf.append("The distance sum of nodes in initial phase(cycle0) is: "+distsumall[0]+"\r\n");
			for(int cycle=1; cycle<=50; cycle++){
				buf.append("The distance sum of nodes in cycle"+cycle+" is: "+distsumall[cycle]+"\r\n"); // int should be converted to string automatically in java when int is outputed with other strings
		    }
			//buf = write;

			fw = new FileWriter(topo+"_N"+N+"_k"+k+".txt");
			bw = new BufferedWriter(fw);
			bw.write(buf.toString());
			bw.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}



	public double[] D_sort(int id, Node_CMT[] node, int[] nlistsort, int k){
		double[] newnview = new double[k+1];  //we will add the distsum at the bit of k, so the size need to be k+1
		double radiusid = node[id].getrad();
        double angleid = node[id].gettheta();
        int centerid = node[id].getcenter();
        double[] dist = new double[nlistsort.length];  //nlist
      	for(int num=0;num<nlistsort.length;num++){
      		double radiuspeer = node[nlistsort[num]].getrad();
      		double anglepeer = node[nlistsort[num]].gettheta();
      		int centerpeer = node[nlistsort[num]].getcenter();

      		if((centerid==0)&&(centerpeer==0)){
      			double diffangle = Math.abs(angleid-anglepeer);
      			dist[num] = Math.sqrt(Math.pow(radiusid, 2)+Math.pow(radiuspeer, 2)-2*radiusid*radiuspeer*(Math.cos(diffangle*Math.PI/180)));
			}      	
			else if((centerid==0)&&(centerpeer==200)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer+centerpeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
			else if((centerid==200)&&(centerpeer==0)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid+centerid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
			else if((centerid==200)&&(centerpeer==200)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid+centerid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer+centerpeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
      	}//for num


      	//bubble sort
      	double tempdist=0;
      	int tempnlist=0;
   		int i=0, j=0;
  		boolean swapped = false;
 		// loop through all numbers 
 		for(i = 0; i < dist.length-1; i++) { 
	   		    swapped = false;
	  			for(j = 0; j < dist.length-1-i; j++) {
	   				   //  (Bubble up the nearest neighbour)
 		   				 if(dist[j] > dist[j+1]) {
		   					     tempdist = dist[j];
		   					     tempnlist = nlistsort[j];
		    					 dist[j] = dist[j+1];
		    					 nlistsort[j] = nlistsort[j+1];
 		    					 dist[j+1] = tempdist;
 		    					 nlistsort[j+1] = tempnlist;
  		    					 swapped = true;
		  			     }
				}
 		       // if no number was swapped that means 
 		       //   array is sorted now, break the loop. 
		      if(!swapped) {
 		   			 break;
 		 	   }
		}//for i

        //find the sum of distance of the node[id] with its neighbours, in a particular cycle
      	double distsum = 0;
      	for(int s=0;s<k;s++){
      		distsum = distsum+dist[s];
      	}

		for(int up=0; up<k; up++){
			newnview[up] = nlistsort[up];
		}

		newnview[k] = distsum;  //the k th bit if newnview is the distsum

		return newnview;
	}



	public double[] C_sort_init(int id, Node_CMT[] node, int[] nviewinitdist, int k){
		double[] newnview = new double[k+1];  //we will add the distsum at the bit of k, so the size need to be k+1
		double radiusid = node[id].getrad();
        double angleid = node[id].gettheta();
        int centerid = node[id].getcenter();
        double[] dist = new double[nviewinitdist.length];  //nlist
      	for(int num=0;num<nviewinitdist.length;num++){
      		double radiuspeer = node[nviewinitdist[num]].getrad();
      		double anglepeer = node[nviewinitdist[num]].gettheta();
      		int centerpeer = node[nviewinitdist[num]].getcenter();
      		
      		if((centerid==0)&&(centerpeer==0)){
      			double diffangle = Math.abs(angleid-anglepeer);
      			dist[num] = Math.sqrt(Math.pow(radiusid, 2)+Math.pow(radiuspeer, 2)-2*radiusid*radiuspeer*(Math.cos(diffangle*Math.PI/180)));
			}      	
			else if((centerid==0)&&(centerpeer==200)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer+centerpeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
			else if((centerid==200)&&(centerpeer==0)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid+centerid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
			else if((centerid==200)&&(centerpeer==200)){
				double xid = Math.cos(angleid*Math.PI/180)*radiusid+centerid;
				double yid = Math.sin(angleid*Math.PI/180)*radiusid;
				double xpeer = Math.cos(anglepeer*Math.PI/180)*radiuspeer+centerpeer;
				double ypeer = Math.sin(anglepeer*Math.PI/180)*radiuspeer;
				dist[num] = Math.sqrt(Math.pow((xid-xpeer),2)+Math.pow((yid-ypeer),2));
			}
      	}//for num

        //find the sum of distance of the node[id] with its neighbours, in a particular cycle
      	double distsum = 0;
      	for(int s=0;s<k;s++){
      		distsum = distsum+dist[s];
      	}

		for(int up=0; up<k; up++){
			newnview[up] = nviewinitdist[up];
		}

		newnview[k] = distsum;  //the k th bit if newnview is the distsum, the k th bit if newnview is the distsum. dist is double, so we have to ask all of the nodes id to be also double too, because we need to put them into same array.

		return newnview;
	}


	public int[] concat(int[] a, int[] b) {
  	  	int aLen = a.length;
   		int bLen = b.length;
   		int[] c= new int[aLen+bLen];
   		System.arraycopy(a, 0, c, 0, aLen);
   		System.arraycopy(b, 0, c, aLen, bLen);
   		return c;
	}





}