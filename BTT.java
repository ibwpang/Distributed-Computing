import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BTT{

	private int N = 0;
	private int k = 0;
	private String topo = null;

	//we can use level and id to get the distance

	int[] distsumall = new int[51];

	public BTT(int N, int k, String topo){
		this.N = N;
		this.k = k;
		this.topo = topo;

		start();

	}

	public void start(){

		Node_BTT[] node = init(N, k);

		evol(N, k, node, topo);

		//Plot_BTT bplot = new Plot_BTT(distsumall);

	}

	public Node_BTT[] init(int N, int k){

		Node_BTT[] node= new Node_BTT[N];

		for(int id=0; id<N; id++){

			node[id] = new Node_BTT(k);  //we need to use id-1 instead of id.

			node[id].setid(id);

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

		int[] newnviewinit = new int[k+1]; //the k th bit if newnviewinit is the distsum, so we need k+1 bits totally
		int[] distsuminit = new int[N];
		int[] nviewinit = new int[k];

		//System.out.println("-------------------");

		for(int v=0; v<N; v++){  //v need to begin from 1
			newnviewinit = B_sort_init(v, node, node[v].getnview(), k);
			for(int z=0; z<k; z++){
				nviewinit[z] = (int)newnviewinit[z];
			}
			distsuminit[v] = newnviewinit[k];  //the k th bit of newnviewinit is the distsum  
		}

		int distsuminitall = 0;
		for(int g=0; g<N; g++){
			distsuminitall = distsuminitall+distsuminit[g];
		}

	    distsumall[0] = distsuminitall;

	    //System.out.println(node[200].getnviewstr());
      	System.out.println("distsumallinit="+distsumall[0]);
      	System.out.println(" ");

		// System.out.println(node[200].getid());
		// System.out.println(node[200].getrad());
		// System.out.println(node[200].gettheta());
		//System.out.println(node[200].getnliststr());
		//System.out.println(node[200].getnviewstr());

		return node;


	}

	public void evol(int N, int k, Node_BTT[] node, String topo){

		

		//get currentrad and targetrad
		for(int cycle=1;cycle<=50;cycle++){
			

			int[] distsum = new int[N];//distsum[N1]: the dist sum for one node in one cycle.

			
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
      			if(topo.equals("B")){
      				int[] newnview = new int[k+1];
      				newnview = B_sort(id, node, node[id].getnlist(), k);
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

			System.out.println("Total distance of cycle:"+cycle+"is"+distsumall[cycle]);//---*********---print the dist of each node with its neighbours in each cycle
			System.out.println(" ");

			
			if((cycle==1) || (cycle==5) || (cycle==10) || (cycle==15)){
				//write a file with nview list--------------------------
				B_writenviewfile(node, N, k, topo, cycle); //write a file which contains the neighbour list for each node

				//draw a graph-----------------------
				Graph_BTT bgraph = new Graph_BTT(node, N, k, topo, cycle);
				//dgraph.writeImage(dgraph.bi, dgraph.picType, dgraph.file);
			}
			
		}//50 cycles finished

		//write a file with distance sum---------------------------
		B_writedistfile(distsumall, N, k, topo);

		//draw a plot : init_phase+50_cycles with distsumall-------------------------------
		Plot_BTT bplot = new Plot_BTT(distsumall);


	}

	public int[] B_sort(int id, Node_BTT[] node, int[] nlistsort, int k){
		int[] newnview = new int[k+1];  //we will add the distsum at the bit of k, so the size need to be k+1
		int levelid = node[id].getlevel();
        //int binid = node[id].getbin();
        int valueid = node[id].getvalue();
        int[] dist = new int[nlistsort.length];  //nlist
        int parentid = 0;
      	for(int num=0;num<nlistsort.length;num++){
      		// double radiuspeer = node[nlistsort[num]].getrad();
      		// double anglepeer = node[nlistsort[num]].gettheta();
      		// double diffangle = Math.abs(angleid-anglepeer);
      		int levelpeer = node[nlistsort[num]].getlevel();
      		int valuepeer = node[nlistsort[num]].getvalue();
      		int levelidtmp = levelid;
      		int valueidtmp = valueid;
      		int levelpeertmp = levelpeer;
      		int valuepeertmp = valuepeer;

      		//System.out.println("-------------------------"+num);
      		//System.out.println("levelpeer:"+levelpeer);
      		//System.out.println("valuepeer:"+valuepeer);
      		if(valuepeertmp==valueidtmp){
      			dist[num]=0;
      			//System.out.println("valuepeer==valueid,"+"dist"+"["+num+"]"+":"+dist[num]);
      		}
      		else{
      			//int levelmin = Math.min(levelid,levelpeer);
      			boolean f = true;
      			while(f){
      				
      				if(levelidtmp<levelpeertmp){
      					if(valuepeertmp%2==1){
      						valuepeertmp=(valuepeertmp-1)/2;
      						levelpeertmp--;
      					}
      					else if(valuepeertmp%2==0){
      						valuepeertmp=valuepeertmp/2;
      						levelpeertmp--;
      					}
      					if(levelpeertmp==levelidtmp){
      						f=false;
      					}
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~peer up"+levelpeertmp);
      				}
      				else if(levelpeertmp<levelidtmp){
      					if(valueidtmp%2==1){
      						valueidtmp=(valueidtmp-1)/2;
      						levelidtmp--;
      					}
      					else if(valueidtmp%2==0){
      						valueidtmp=valueidtmp/2;
      						levelidtmp--;
      					}
      					if(levelidtmp==levelpeertmp){
      						f=false;
      					}
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~id up"+levelidtmp);
      				}
      				else if(levelpeertmp==levelidtmp)
      				//else
      				{
      					f=false;
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~equal level"+levelidtmp);
      				}
      			}

      			

      			if(valueidtmp==valuepeertmp){
      				parentid = valueidtmp-1;
      				//System.out.println("*****************equal value"+valueidtmp);
      			}
      			else if(valueidtmp!=valuepeertmp){
      				boolean ff =true;
      				while(ff){
      					if(valueidtmp%2==1){
      						valueidtmp=(valueidtmp-1)/2;
      						//System.out.println("*****************id up"+valueidtmp);
      					}
      					else if(valueidtmp%2==0){
      						valueidtmp=valueidtmp/2;
      						//System.out.println("*****************id up"+valueidtmp);
      					}
      					if(valuepeertmp%2==1){
      						valuepeertmp=(valuepeertmp-1)/2;
      						//System.out.println("*****************peer up"+valuepeertmp);
      					}
      					else if(valuepeertmp%2==0){
      						valuepeertmp=valuepeertmp/2;
      						//System.out.println("*****************peer up"+valuepeertmp);
      					}
      					
      					if(valueidtmp==valuepeertmp){
      						parentid = valueidtmp-1;
      						ff = false;
      						//System.out.println("*****************equal value"+valuepeertmp);
      					}
      				}
      			}
      			dist[num] = (levelid-1)+(levelpeer-1)-2*(node[parentid].getlevel()-1);
      			//System.out.println("dist"+"["+num+"]"+":"+dist[num]);
			}

      		//dist[num] = Math.sqrt(Math.pow(radiusid, 2)+Math.pow(radiuspeer, 2)-2*radiusid*radiuspeer*(Math.cos(diffangle*Math.PI/180)));
      	}//for num


      	//bubble sort
      	int tempdist=0;
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
      	int distsum = 0;
      	for(int s=0;s<k;s++){
      		distsum = distsum+dist[s];
      	}

		for(int up=0; up<k; up++){
			newnview[up] = nlistsort[up];
		}

		newnview[k] = distsum;  //the k th bit if newnview is the distsum

		return newnview;
	}


	public int[] B_sort_init(int id, Node_BTT[] node, int[] nviewinitdist, int k){
		int[] newnview = new int[k+1];  //we will add the distsum at the bit of k, so the size need to be k+1
		int levelid = node[id].getlevel();
        //int binid = node[id].getbin();
        int valueid = node[id].getvalue();
        int[] dist = new int[nviewinitdist.length];  //nlist
        int parentid = 0;
        //System.out.println("======================="+id);
        //System.out.println("levelid:"+levelid);
        //System.out.println("valueid:"+valueid);
        //System.out.println("nviewinitdist:"+Arrays.toString(nviewinitdist));
      	for(int num=0;num<nviewinitdist.length;num++){
      		int levelpeer = node[nviewinitdist[num]].getlevel();
      		int valuepeer = node[nviewinitdist[num]].getvalue();
      		int levelidtmp = levelid;
      		int valueidtmp = valueid;
      		int levelpeertmp = levelpeer;
      		int valuepeertmp = valuepeer;
      		//System.out.println("-------------------------"+num);
      		//System.out.println("levelpeer:"+levelpeer);
      		//System.out.println("valuepeer:"+valuepeer);
      		if(valuepeertmp==valueidtmp){
      			dist[num]=0;
      			//System.out.println("valuepeer==valueid,"+"dist"+"["+num+"]"+":"+dist[num]);
      		}
      		else{
      			//int levelmin = Math.min(levelid,levelpeer);
      			boolean f = true;
      			while(f){
      				
      				if(levelidtmp<levelpeertmp){
      					if(valuepeertmp%2==1){
      						valuepeertmp=(valuepeertmp-1)/2;
      						levelpeertmp--;
      					}
      					else if(valuepeertmp%2==0){
      						valuepeertmp=valuepeertmp/2;
      						levelpeertmp--;
      					}
      					if(levelpeertmp==levelidtmp){
      						f=false;
      					}
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~peer up"+levelpeertmp);
      				}
      				else if(levelpeertmp<levelidtmp){
      					if(valueidtmp%2==1){
      						valueidtmp=(valueidtmp-1)/2;
      						levelidtmp--;
      					}
      					else if(valueidtmp%2==0){
      						valueidtmp=valueidtmp/2;
      						levelidtmp--;
      					}
      					if(levelidtmp==levelpeertmp){
      						f=false;
      					}
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~id up"+levelidtmp);
      				}
      				else if(levelpeertmp==levelidtmp)
      				//else
      				{
      					f=false;
      					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~equal level"+levelidtmp);
      				}
      			}

      			

      			if(valueidtmp==valuepeertmp){
      				parentid = valueidtmp-1;
      				//System.out.println("*****************equal value"+valueidtmp);
      			}
      			else if(valueidtmp!=valuepeertmp){
      				boolean ff =true;
      				while(ff){
      					if(valueidtmp%2==1){
      						valueidtmp=(valueidtmp-1)/2;
      						//System.out.println("*****************id up"+valueidtmp);
      					}
      					else if(valueidtmp%2==0){
      						valueidtmp=valueidtmp/2;
      						//System.out.println("*****************id up"+valueidtmp);
      					}
      					if(valuepeertmp%2==1){
      						valuepeertmp=(valuepeertmp-1)/2;
      						//System.out.println("*****************peer up"+valuepeertmp);
      					}
      					else if(valuepeertmp%2==0){
      						valuepeertmp=valuepeertmp/2;
      						//System.out.println("*****************peer up"+valuepeertmp);
      					}
      					
      					if(valueidtmp==valuepeertmp){
      						parentid = valueidtmp-1;
      						ff = false;
      						//System.out.println("*****************equal value"+valuepeertmp);
      					}
      				}
      			}
      			dist[num] = (levelid-1)+(levelpeer-1)-2*(node[parentid].getlevel()-1);
      			//System.out.println("dist"+"["+num+"]"+":"+dist[num]);
			}      	
      	}//for num

        //find the sum of distance of the node[id] with its neighbours
      	int distsum = 0;
      	for(int s=0;s<k;s++){
      		distsum = distsum+dist[s];
      	}

		for(int up=0; up<k; up++){
			newnview[up] = nviewinitdist[up];
		}

		newnview[k] = distsum;  

		return newnview;

	}


	public void B_writenviewfile(Node_BTT[] node, int N, int k, String topo, int cycle){ //node and cycle will change in different cycles

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


	public void B_writedistfile(int[] distsumall, int N, int k, String topo){

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


	public int[] concat(int[] a, int[] b) {
  	  	int aLen = a.length;
   		int bLen = b.length;
   		int[] c= new int[aLen+bLen];
   		System.arraycopy(a, 0, c, 0, aLen);
   		System.arraycopy(b, 0, c, aLen, bLen);
   		return c;
	}





}