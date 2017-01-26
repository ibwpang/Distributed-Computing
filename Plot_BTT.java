import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
//import java.tecycle*xunit.DateFormat;
//import java.util.Date;

public class Plot_BTT extends JFrame {

    private int index = 0;
    //private int[] val = new int[MAX_SAMPLES];
    private int[] dist = new int[51];
    //private int[] distance = new int[51];
    javax.swing.JPanel dataPanel;  //cannot double extends JFrame and JPanel, so we have to write like this.

    /** Creates new form Plot */
    public Plot_BTT(int[] dist) {
        super("Plot of the distance sum (y-axis) with the cycles (x-axis)");
        initComponents();
        this.dist = dist;
    }
    
    // distance = str2int(dist);

    // public int[] str2int(double[] dist){
    //     int[] distance = new int[51];
    //     for(int i=0;i<dist.length;i++){
    //         distance[i] = (int)dist[i];
    //     }
    //     return distance;
    // }
    


    public void initComponents(){

        dataPanel = new javax.swing.JPanel();

        dataPanel.setBackground(new java.awt.Color(255, 255, 255));
        dataPanel.setMinimumSize(new java.awt.Dimension(800, 500));
        dataPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        getContentPane().add(dataPanel, java.awt.BorderLayout.CENTER);
        pack();
        setVisible(true);
    }



    // Graph the sensor values in the dataPanel JPanel
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(Arrays.toString(dist));
        double left = dataPanel.getX();       // get size of pane
        double top = dataPanel.getY();
        double right = left + dataPanel.getWidth();
        double bottom = top + dataPanel.getHeight();

        double y0 = bottom;                   // leave some room for margins
        double yn = top;
        double x0 = left+50;
        double xn = right;
        int distmax = getMax(dist);

        double xunit = (xn - x0)/50;
        double yunit = (yn - y0)/distmax; //almost be zero

        // draw X and Y axis
        g.setColor(Color.BLACK);
        g.drawLine((int)x0, (int)yn, (int)x0, (int)y0);
        g.drawLine((int)x0, (int)y0, (int)xn, (int)y0);

        g.setColor(Color.RED);
        for (int cycle = 0; cycle<50; cycle++) {   
            //g.drawLine((int)x0, (int)y0, (int)x0+10*(int)xunit, (int)y0-10000*(int)yunit);
            g.drawLine((int)x0+cycle*(int)xunit, (int)y0+(int)(dist[cycle]*yunit), (int)x0+(cycle+1)*(int)xunit, (int)y0+(int)(dist[cycle+1]*yunit));
            
            //int min = (cycle*xunit - x0) / (60 / 2);
            //g.drawString(Integer.toString(min), cycle*xunit - (min < 10 ? 3 : 7) , y0 + 20);
        }

        // draw Y axis - distance sum
        // g.setColor(Color.BLUE);
        // for (int vt = 120; vt > 0; vt -= 20) {         // tick every 200
        //     int v = y0 + (int)(vt * vscale);
        //     g.drawLine(x0 - 5, v, x0 + 5, v);
        //     g.drawString(Integer.toString(vt), x0 - 38 , v + 5);
        // }

        // graph sensor values
        // int xp = -1;
        // int vp = -1;
        // for (int i = 0; i < index; i++) {
        //     int x = x0 + (int)((time[i] - time[0]) * tscale);
        //     int v = y0 + (int)(val[i] * vscale);
        //     if (xp > 0) {
        //         g.drawLine(xp, vp, x, v);
        //     }
        //     xp = x;
        //     vp = v;
        // }
        System.out.println("All done successfully, please check the results. You can stop this process and then try another topology or try another topology in another command terminal.");
        //System.exit(0);
    }
    
    public int getMax(int[] arr){
         int max=arr[0];
         for(int i=1;i<arr.length;i++){
             if(arr[i]>max){
                 max=arr[i];
             }
         }
         return max;
     }

    // java.awt.EventQueue.invokeLater(new Runnable() {
    //         public void run() {
    //             new Plot().setVisible(true);
    //         }
    //     });


}

