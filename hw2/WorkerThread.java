import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.lang.*;

public class WorkerThread implements Callable<String>{
  private int task;
  private int N=0;
  private int k=0;
  private int subresult = 0;

  public WorkerThread(int task, int N, int k) {
    this.task = task;
    this.N=N;
    this.k=k;
  }
   
  public String call() {
    synchronized(this) {  //the threads will not process same object in this hw, so synchronized statement is dispensable.
      //System.out.println(Thread.currentThread().getName()+"(Start), task is - "+task);
      subresult = processtask();
      //System.out.println(Thread.currentThread().getName()+"(Finished), task is - "+task+", sub-result is "+subresult);
      return subresult+" ";  //in order to seperate the subresults and type convert, we use a " ".
    }
  }

  private int processtask(){
      int result=0;
      try{
          for(int i=task;i<(task+k);i++){
             result+=Math.pow(i,2);
          }
          Thread.sleep(0);
      }
      catch(InterruptedException e){
        e.printStackTrace();
      }
      return result;
  }

}  