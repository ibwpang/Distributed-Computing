import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

import javax.imageio.*;
import java.awt.image.*; 
import java.awt.font.*;   
import java.awt.geom.*;


public class Graph_BTT{

	private int N;
	private Node_BTT[] node = new Node_BTT[N];
	private int k;
	private String topo;
	private int cycle;

	public Graph_BTT(Node_BTT[] node, int N, int k, String topo, int cycle){
		//super();
		this.N = N;
		this.node = node;
		this.k = k;
		this.topo = topo;
		this.cycle = cycle;

        //creat a paint board, you can draw on it
        BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_BGR);

        //type of the output image
        String picType = "png";

        //name of the image
        File file = new File(topo+"_N"+N+"_k"+k+"_"+cycle+"."+picType);

		writeImage(bi, picType, file); //if we want to transmit the three variable into the writeImage(), we must have them in the constructor firstly. They cannot be defined out of the constructor.
	}


    

    
    public void writeImage(BufferedImage bi, String picType, File file) { 
        // get the pen
        Graphics g = bi.getGraphics();
        // draw a picture system, default is white
        g.fillRect(0, 0, 1000, 1000);
        // color of the pen
        g.setColor(new Color(255, 0, 0));
        // font of the pen
        g.setFont(new Font("Serif", Font.BOLD, 10));
        // draw what you like
        for(int id=0; id<N; id++){
            int[] idview = node[id].getnview();

            int idlevel = node[id].getlevel();
            //idrad = idrad*50;
            int idvalue = node[id].getvalue();
            //int idbin = node[id].getbin();
            int xofid = getx(idlevel,idvalue);
            int yofid = gety(idlevel);
            //if(id==200){
            //    System.out.println(xofid+","+yofid);
            //}
            for(int peer=0; peer<k; peer++){
                
                int peerlevel = node[idview[peer]].getlevel();
                //peerrad = peerrad*50;
                int peervalue = node[idview[peer]].getvalue();
                //int peerbin = node[idview[peer]].getbin();
                int xofpeer = getx(peerlevel,peervalue);
                int yofpeer = gety(peerlevel);
                // double peertheta = node[idview[peer]].gettheta();
                // int xofpeer = getx(peerrad,peertheta);
                // int yofpeer = gety(peerrad,peertheta);

                g.drawLine(xofid, yofid, xofpeer, yofpeer); //------------------   
            }
            
        }
        // release the pen
        g.dispose();
        // write the picture into the disk
        boolean val = false;
        try {
            val = ImageIO.write(bi, picType, file); //this is the sentence that can really write the png pictures to the local disk. The File file =.... is just a declare, it cannot really write the pics to the local disk.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getx(int level, int value){
        int x = 0;

        
        double unit = 1000/Math.pow(2,level-1);
        x = (int)((value-Math.pow(2,level-1))*unit+(unit/2));
        
        

        return x;

    }

    public int gety(int level){
        int y = 0;
        
        y=50+90*(level-1);
        
        return y;
    }


}
