import java.util.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.io.*;
import java.lang.*;
import java.lang.management.*;

public class perfsquares extends Thread{
  
  String name = "";
  String args0 = "";
  String args1 = "";
  long userstartTime = 0;
  long cpustartTime = 0;

  public perfsquares(String name, String args0, String args1, long userstartTime, long cpustartTime){
    this.name=name;
    this.args0=args0;
    this.args1=args1;
    this.userstartTime=userstartTime;
    this.cpustartTime=cpustartTime;
  }

  public void run() {

    System.out.println(name+" start to run.");

    int N = Integer.parseInt(args0);
    int k = Integer.parseInt(args1);
    int numThread = 1000;
    List<Future> futures = new ArrayList<Future>();
    String str = "";
    ExecutorService executor = Executors.newCachedThreadPool(); //Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.
    
    System.out.println("Create a thread pool and use the threads as workers and start to process tasks.");

    for(int i=1; i<=N; i++){
      WorkerThread job = new WorkerThread(i, N, k); 
      Future<String> future = executor.submit(job);  //job will be executed by one of the threads. Asynchronous execution. 
      futures.add(future);
    }
    try{
        for (Future future : futures) {
            str += future.get(); //get() will be block until execute the next callable, so we need to put this sentence out of the for loop above.
        }  
      }
      catch(final InterruptedException ex) {
            ex.printStackTrace();
      } 
      catch(final ExecutionException ex) {
            ex.printStackTrace();
      }
    executor.shutdown();   //executor of newFixedThreadPool need a explicit shutdown.
    while(!executor.isTerminated()){}

    String[] strArray = str.split(" ");
    int[] arr = new int[strArray.length];
    double root = 0;
    ArrayList<Integer> arrlist = new ArrayList<Integer>();
    for(int m = 0; m < strArray.length; m++) {
       arr[m] = Integer.parseInt(strArray[m]);
       root = Math.pow(arr[m], 0.5);
       if(root%1==0){  //check if is a perfect square.
          arrlist.add(m+1);
       }
    }

    System.out.println("Finished all threads and tasks.");
    if(arrlist.size()==0){
        System.out.println("There is no such starting numbers.");
    }
    else{
      for(int x=0;x<arrlist.size();x++){
        System.out.println("The starting number: "+arrlist.get(x));
      }
    }
    //long endTime   = System.currentTimeMillis();
    long userendTime = getUserTime();
    long cpuendTime = getCpuTime();
    long usertotalTime = userendTime - userstartTime;
    long cputotalTime = cpuendTime - cpustartTime;
    if(usertotalTime<0){
      usertotalTime = -1*usertotalTime;
    }
    if(cputotalTime<0){
      cputotalTime= -1*cputotalTime;
    }
    long userduration = usertotalTime/1000000000;
    long userafterpoint = usertotalTime%1000000000;
    long cpuduration = cputotalTime/1000000000;
    long cpuafterpoint = cputotalTime%1000000000;
    double ratio = (double)usertotalTime/cputotalTime;
    //String d = String.format("%.6f", duration);
    //long realtime = userduration+userafterpoint/1000000000;
    //long cputime = cpuduration+cpuafterpoint/1000000000;
    
    System.out.println("The real time to complete is: "+userduration+"."+userafterpoint+"s");
    System.out.println("The cpu time to complete is: "+cpuduration+"."+cpuafterpoint+"s");
    System.out.println("The ratio of real-time/cpu-time is: "+ratio);
  }

  public static long getUserTime( ) {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    return bean.isCurrentThreadCpuTimeSupported( ) ?
        bean.getCurrentThreadUserTime( ) : 0L;
  }

  public static long getCpuTime( ) {
    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
    return bean.isCurrentThreadCpuTimeSupported( ) ?
        bean.getCurrentThreadCpuTime( ) : 0L;
}

  public static void main(String args[]){
    //long startTime = System.currentTimeMillis();
    long userstartTime = getUserTime();
    long cpustartTime = getCpuTime();
    perfsquares master = new perfsquares("Thread-Master", args[0], args[1], userstartTime, cpustartTime);
    master.start();
  }

}