*************************************
This is a readme file of the homework 2 of distributed computing course. 
Author: Bowei Pang

*************************************
Running environment: Java version "1.8.0_101", Java(TM) SE Runtime Environment (build 1.8.0_101-b13)

Operating system: I write my code in windows, and I use Sublimetxt to write the code. After the testing, the code can be compiled and run both on my windows and my linux (Ubuntu 16.04) well. 

Language: Java

Compiler version: Java version "1.8.0_101", Java(TM) SE Runtime Environment (build 1.8.0_101-b13)

*************************************
Structure of the program:
There are 2 .java files and 1 makefile totally.
 
The details of the structure of the program is:

The perfsquares.java is the entrance of the program and in this class, I used ExecutorService to create executor and assign the jobs to the executor. The executor will invoke the submit() method and calculate the sum of the squares. The calculation method is in another java file, called WorkerThread.java, in this class, the processtask method will be executed by the threads and get the sum of the squares and then return the result to the perfsquares class. In the perfsquares class, the manager thread will determine which outputs is a perfect square, and then, the starting number of it will be printed on the screen. I used java.lang.management package to calculate the cpu time and real time of the threads and the program. The ratio of the real time and the cpu time was calculated and we can see that it is less than 1, so my implementation is parallel.  