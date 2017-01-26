import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Node_CMT{
	
	private int id;
	private int rad;
    private double theta;
    private int center;
    //private int bin;
    private int k;
    private int[] nlist = new int[2*k];
    private int[] nview = new int[k];

	public Node_CMT(int k){
		this.k=k;
	}

	//id
	public int getid(){
		return id;
	}

	public void setid(int newid){
		id = newid;
	}

	//rad
	public int getrad(){
		return rad;
	}

	public void setrad(int newrad){
		rad = newrad;
	}

	//theta
	public double gettheta(){
		return theta;
	}

	public void settheta(double newtheta){
		theta = newtheta;
	}

	//center
	public int getcenter(){
		return center;
	}

	public void setcenter(int newcenter){
		center = newcenter;
	}


	public String getnliststr(){
		return Arrays.toString(nlist);
	}

	public int[] getnlist(){
		return nlist;
	}

	public void setnlist(int[] newnlist){
		nlist = newnlist;
	}

	public String getnviewstr(){
		return Arrays.toString(nview);
	}

	public int[] getnview(){
		return nview;
	}

	public void setnview(int[] newnview){
		nview = newnview;
	}

}