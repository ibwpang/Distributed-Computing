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

public class Graph{

	private int N;
	private Node[] node = new Node[N];
	private int k;
	private String topo;
	private int cycle;

	public Graph(Node[] node, int N, int k, String topo, int cycle){
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
            idrad = idrad*50;
            double idtheta = node[id].gettheta();
            int xofid = getx(idrad,idtheta);
            int yofid = gety(idrad,idtheta);
            if(id==200){
                System.out.println(xofid+","+yofid);
            }
            for(int peer=0; peer<k; peer++){
                
                int peerrad = node[idview[peer]].getrad();
                peerrad = peerrad*50;
                double peertheta = node[idview[peer]].gettheta();
                int xofpeer = getx(peerrad,peertheta);
                int yofpeer = gety(peerrad,peertheta);

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

    public int getx(int rad, double theta){
        double x = Math.cos(theta*Math.PI/180)*rad;
        int xx = (int)x;
        return xx;

    }

    public int gety(int rad, double theta){
        double y = Math.sin(theta*Math.PI/180)*rad;
        int yy = (int)y;
        return yy;
    }


}


// public class Graph{

// 	int N = 0;
// 	int k = 0;
// 	Node[] node = new Node[N];

// 	public Graph(Node[] node, int N, int k){
// 		this.node = node;
// 		this.N = N;
// 		this.k = k;
// 	}

//     public static void main(String[] args) throws Exception{

//         int width = 100;   
//         int height = 100;   
//         String s = "Hello";   
           
//         File file = new File("image.jpg");   
           
//         Font font = new Font("Serif", Font.BOLD, 10);   
//         BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   
//         Graphics2D g2 = (Graphics2D)bi.getGraphics();   
//         g2.setBackground(Color.WHITE);   
//         g2.clearRect(0, 0, width, height);   
//         g2.setPaint(Color.RED);   
           
//         FontRenderContext context = g2.getFontRenderContext();   
//         Rectangle2D bounds = font.getStringBounds(s, context);   
//         double x = (width - bounds.getWidth()) / 2;   
//         double y = (height - bounds.getHeight()) / 2;   
//         double ascent = -bounds.getY();   
//         double baseY = y + ascent;   
           
//         g2.drawString(s, (int)x, (int)baseY);   
           
//         ImageIO.write(bi, "jpg", file);   
//     }   
// }   

