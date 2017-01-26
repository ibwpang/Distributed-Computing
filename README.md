# Distributed-Computing-hw1
*************************************
This is a readme file of the homeowrk 1 of distributed computing course. 
Author: Bowei Pang

*************************************
Running environment: Java version "1.8.0_101", Java(TM) SE Runtime Environment (build 1.8.0_101-b13)

Operating system: I write my code in windows, and I use Sublimetxt to write the code. After the testing, the code can be compiled and run both on my windows and my linux (Ubuntu 16.04) well. 

Language: Java

Compiler version: Java version "1.8.0_101", Java(TM) SE Runtime Environment (build 1.8.0_101-b13)

***********************
Structure of the program:
There are 12 .java files and 1 makefile totally. They can be classified by three part:

Part1: the .java files of TMAN, Node, Graph and Plot are about task 1. TMAN is the "entrance" of all of the three tasks, you can run the program using the command which mentioned in the "Homework 1.pdf" on E-learning Canvas. The Node.java contains the node structure of task1, the Graph and Plot classes can be used to draw the graphs and plot. The Node, Graph and Plot are called in the TMAN. 

Part2: the .java files of BTT, Node_BTT, Graph_BTT and Plot_BTT are about task 2. The Node_BTT.java contains the node structure of task2, the Graph_BTT and Plot_BTT classes can be used to draw the graphs and plot. The Node_BTT, Graph_BTT and Plot_BTT are called in the BTT, and the BTT is called in TMAN.

Part3: the .java files of CMT, Node_CMT, Graph_CMT and Plot_CMT are about task 3. The Node_CMT.java contains the node structure of task3, the Graph_CMT and Plot_CMT classes can be used to draw the graphs and plot. The Node_CMT, Graph_CMT and Plot_CMT are called in the CMT, and the CMT is called in TMAN.

Some important methods I defined in my programs:
init() is for initiate the topology and the initial value of the parameters of nodes. evol() is for the evolution of the topology in different cycles. toponame_writenviewfile() is for generating the files about the neighbours of the node in different cycles and toponame_writedistfile() is for generating the distance sum of the nodes in difference cycles. I used these methods in my programs when I need to draw a graph or draw a plot, they all defined in the .java files of TMAN, BTT and CMT. 


