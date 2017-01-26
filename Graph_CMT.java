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



// import java.awt.Color;
// import java.awt.Font;
// import java.awt.Graphics;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;

// import javax.imageio.ImageIO;

public class Graph_CMT{

	private int N;
	private Node_CMT[] node = new Node_CMT[N];
	private int k;
	private String topo;
	private int cycle;

	public Graph_CMT(Node_CMT[] node, int N, int k, String topo, int cycle){
		//super();
		this.N = N;
		this.node = node;
		this.k = k;
		this.topo = topo;
		this.cycle = cycle;

        //creat a paint board, you can draw on it
        BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_BGR);

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
        g.fillRect(0, 0, 500, 500);
        // color of the pen
        g.setColor(new Color(255, 0, 0));
        // font of the pen
        g.setFont(new Font("Serif", Font.BOLD, 10));
        // draw what you like
        for(int id=0; id<N; id++){
            int[] idview = node[id].getnview();

            int idrad = node[id].getrad();
            idrad = idrad;////
            double idtheta = node[id].gettheta();
            int centerid = node[id].getcenter();
            int xofid = getx(idrad,idtheta,centerid);
            int yofid = gety(idrad,idtheta,centerid);
            // if(id==200){
            //     System.out.println(xofid+","+yofid);
            // }
            for(int peer=0; peer<k; peer++){
                
                int peerrad = node[idview[peer]].getrad();
                peerrad = peerrad;////
                double peertheta = node[idview[peer]].gettheta();
                int centerpeer = node[idview[peer]].getcenter();
                int xofpeer = getx(peerrad,peertheta,centerpeer);
                int yofpeer = gety(peerrad,peertheta,centerpeer);

                g.drawLine(xofid+250, yofid+250, xofpeer+250, yofpeer+250); //------------------   
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

    public int getx(int rad, double theta, int center){
        int xx = 0;
        if(center==0){
            double x = Math.cos(theta*Math.PI/180)*rad;
            xx = (int)x;
        }
        else if(center==200){ //---
            double x = Math.cos(theta*Math.PI/180)*rad+center;
            xx = (int)x;
        }
        return xx;

    }

    public int gety(int rad, double theta, int center){
        double y = Math.sin(theta*Math.PI/180)*rad;
        int yy = (int)y;
        return yy;
    }


}
