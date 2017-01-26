import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Node_BTT{
	
	private int id;
	private int value;
    private int level;
    //private int bin;
    private int k;
    private int[] nlist = new int[2*k];
    private int[] nview = new int[k];

	public Node_BTT(int k){
		this.k=k;
	}

	public int getid(){
		return id;
	}

	public void setid(int newid){
		id = newid;
		value = newid+1;
	}

	public int getvalue(){
		return value;
	}

	public int getlevel(){
		for(int x=0;x<32;x++){
			if((Math.pow(2,x)<=value)&&(value<=(Math.pow(2,(x+1))-1))){
				level = x+1;
			}
		}
		return level;
	}

	public String getbin(){
		return Integer.toBinaryString(value);
	}

	//public void setlevel(int newlevel){
	//	level = newlevel;
	//}

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