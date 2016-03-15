package com.magicaeludos.mobile.magicaeludos.framework;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Andreas on 24.02.2016.
 */
public class Probability {

    /*Changable variables:*/
    boolean lane1 = true; /*True if lanes is open for obstacle*/
    boolean lane2 = true;
    boolean lane3 = true;
    int lane1Block = 0; /*Numbers of frames a lane is blocked before open again*/
    int lane2Block = 0;
    int lane3Block = 0;

    /*Constants to define game performance*/
    double rockRate = 1.0; /*Arrival rate of rock*/
    int rockLength = 10; /*Number of frames the rock blocks a lane. Might also block some space on the path*/
    int rockWidth = 1; /*Numer of lanes the rock is covering*/
    int rockPri = 10; /*Prioritizing index used to choose between obstacles if they want to enter at same time*/

    double logRate = 0.5;
    int logLength = 10;
    int logWidth = 2;
    int logPri = 5;

    double timeStep = 1.0/30.0; /*Time steps in seconds*/
    int maxLaneBlock = 2; /*Maximal allowed number of lanes blocked at the same time*/



    /*Random rand = new Random();
    return  Math.log(1-rand.nextDouble())/(-rate);*/

    public double probExp(double rate, double timeStep){
        /*Using a exponential arrival times with given rate, compute the probability that
         an arrival happens in a given timeStep.
         rate: Rate of arrivals
         timeStep: length of time steps*/

        return 1 - Math.exp(-rate*timeStep);
    }
    public int randomInteger(int start, int end, Random aRandom){
        //get the range, casting to long to avoid overflow problems
        long range = (long)end - (long)start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + start);
        return randomNumber;
    }
    public boolean sendObstacle(double rate){
        /*Return whether or not a given obstacle will be sent to the game
        * rate: Rate of obstacle arrivals*/

        boolean obstacle = true;
        Random rand = new Random();

        double acceptProb = probExp(rate, timeStep);
        if (rand.nextDouble() < acceptProb){
            obstacle = true;
        }
        return obstacle;
    }
    public int lanesOpen(){
        /*Return umber of lanes currently open for obstacles. Not possible
        * to exceed maxLaneBlock*/
        int lanes = 0;

        if(lane1 == true){lanes++;}
        if(lane2 == true && lanes < maxLaneBlock){lanes++;}
        if(lane3 == true && lanes < maxLaneBlock){lanes++;}

        return lanes;
    }
    public int connectedLanes(){
        /*Return the maximum number of lanes that are connected/beside each other*/
        int n = 0;

        if(lanesOpen()==1){n=1;}
        if(lanesOpen()==2){
            if(lane1==true && lane3==true){n=1;}
            else{n=2;}
        }
        if(lanesOpen()==3){n=3;}
        return n;
    }
    public int obstaclesWidth(List<Obstacle> obstacles){
        /*Return the total width of all obstacles
        obstacles: The obstacles to send*/
        int width = 0;
        for(int i=0; i<obstacles.size(); i++){
            width = width + obstacles.get(i).getWidth();
        }
        return width;
    }
    public List<Obstacle> simulateObstacles(int connectedLanes){
        /*Simulate obstacles*/

        List<Obstacle> obstacles = new ArrayList<Obstacle>();

        /*Run all samplers and add to 'obstacles' if true*/

        /*If there are 3 lanes open, simulate obstacles requiring 3-lane-width*/
        if(connectedLanes == 3){

        }
        /*If there are at least 2 lanes open and adjacent, simulate obstacles requiring 2-lane-width*/
        if(connectedLanes == 2 || connectedLanes == 3){
            if(sendObstacle(logRate)){obstacles.add(new Obstacle("Log",logRate,logWidth,logLength,logPri));}
        }

        /*Simulate obstacles requiring only one lane*/
        if(sendObstacle(rockRate)){obstacles.add(new Obstacle("Rock",rockRate,rockWidth,rockLength,rockPri));}

        return obstacles;
    }
    public int findLargestPri(List<Obstacle> obstacles){
        /*Return the index of the obstacle with the largest priority index found in list*/
        /*obstacles: list of obstacles*/

        int pri = -1000;
        int index = 0;

        for (int i=0; i<obstacles.size(); i++){
            if (pri < obstacles.get(i).getPriIndex()){
                pri = obstacles.get(i).getPriIndex();
                index = i;
            }
        }
        return index;
    }
    public List<Obstacle> cutObstacles(List<Obstacle> obstacles){
        /*Cut required obstacles from list
        obstacles: list with obstacles*/
        List<Obstacle> cutObstacles = new ArrayList<Obstacle>();

        int index;
        int end = obstacles.size();
        for (int i=0; i<end; i++){
            index = findLargestPri(obstacles);
            cutObstacles.add(obstacles.get(index));
            obstacles.remove(obstacles.size()-1);
        }

        while(obstaclesWidth(cutObstacles) > lanesOpen()){
            cutObstacles.remove(cutObstacles.size()-1);
        }

        return cutObstacles;
    }
    public Obstacle chooseObstacle(List<Obstacle> obstacles){
        /*Choose the one obstacle with the highest priority index.
        obstacles: list with obstacles*/

        int index = findLargestPri(obstacles);
        return obstacles.get(index);
    }
    public List<Obstacle> distributeObstacles(List<Obstacle> obstacles, int lanesOpen){
        /*Distribute obstacle uniformly on the open lanes
        obstacles: The obstacles to distribute
        lanesOpen: Number of open lanes
         */

        Random random = new Random();
        int n = obstacles.size();
        Vector<Integer> lanes = new Vector<Integer>();

        int index = 0;
        int rand;
        boolean taken;
        while(index < n){
            taken = false;
            rand = randomInteger(1, n, random);

            for (int j=0; j<lanes.size(); j++){
                if(lanes.get(j)==rand){taken = true;}
            }
            if (taken == false){
                lanes.add(rand);
                index++;
            }
        }

        for(int i=0; i<lanes.size(); i++){
            obstacles.get(i).setLane(lanes.get(i));
        }


        return obstacles;

    }
    public List<Obstacle> chooseLanes(int lanesOpen, List<Obstacle> obstacles){
        /*Choose uniformly what lanes to send the obstacles to
        lanesOpen: Number of open lanes
        obstacles: list of obstacles*/

        /*NOT DONE HERE!!!*/

        int n = obstacles.size();

        if(n == 3){
            /*distribute singles*/
            obstacles = distributeObstacles(obstacles, lanesOpen);
        }
        if(n == 2){

        }

        return obstacles;

    }
    public int chooseLane(Obstacle obstacle, int lanesOpen){
        /*Choose the lane the obstacle will b sent to*/
        /*obstacle: Obstacle to send*/

        int lane;
        int width = obstacle.getWidth();
        Random random = new Random();
        if(lanesOpen == 3){
            if(width==1){lane = randomInteger(1,3,random);}
            else if(width==2){lane = randomInteger(1,2,random);}
            else{lane = 1;}
        }
        else if(lanesOpen == 2){
            if(lane1 && lane2){
                if(width==1){lane = randomInteger(1,2,random);}
                else{lane = 1;}
            }
            else if(lane2 && lane3){
                if(width==1){lane = 1+randomInteger(1,2,random);}
                else{lane = 2;}
            }
            else{
                lane = randomInteger(1,2,random);
                if(lane==2){lane = 3;}
            }
        }
        else/*lanesOpen == 1*/{
            if(lane1){lane = 1;}
            else if(lane2){lane = 2;}
            else{lane = 3;}
        }
        return lane;
    }
    public void blockLanes(Obstacle obstacle){
        /*Block the lanes that obstacle occupies and set blosk timer
        lane: The lane the obstacle starts blocking*/

        int lane = obstacle.getLane();
        int width = obstacle.getWidth();
        int time = obstacle.getLength();

        if(lane == 3){
            lane3 = false;
            lane3Block = time;
        }

        else if(lane == 2){
            lane2 = false;
            lane2Block = time;
            if(width==2){lane3 = false;
                         lane3Block = time;}
        }

        else{
            lane1 = false;
            lane1Block = time;
            if(width == 2){
                lane2=false;
                lane2Block = time;
            }
            if(width == 3){
                lane2=false;
                lane3=false;
                lane2Block = time;
                lane3Block = time;
            }
        }

    }
    public void updateLanes(){
        /*Update the lane timer and if the lane is open again*/

        if(!lane1){
            lane1Block = lane1Block - 1;
            if(lane1Block==0){lane1 = true;}
        }
        if(!lane2){
            lane2Block = lane2Block - 1;
            if(lane2Block==0){lane2 = true;}
        }
        if(!lane3){
            lane3Block = lane3Block - 1;
            if(lane3Block==0){lane3 = true;}
        }
    }
    public void sendObstacles(){
        /*Need also to update lanes before this*/
        updateLanes();

        /*Check how many lanes are open for obstacles this frame*/
        int connectedLanes = connectedLanes();
        int lanesOpen = lanesOpen();

        /*choose/simulate what obstacles to send this frame*/
        List<Obstacle> obstacles = simulateObstacles(connectedLanes);

        /*Choose obstacle(s). Two approaches:*/
        /*1)    If the number of obstacles to send(where their width
                are taken into account) are larger than open lanes,
                choose what obstacles to send (need a way of prioritizing)*/
        if(obstaclesWidth(obstacles) > lanesOpen){
            obstacles = cutObstacles(obstacles);
        }
        /*2)    Choose only one obstacle with the largest priority index*/
        Obstacle chosen = chooseObstacle(obstacles);

        /*FROM THIS ON, WE FOLLOW CHOICE 2) ABOVE!*/

        /*Distribute uniformly the object over the open lanes*/
        int lane = chooseLane(chosen, lanesOpen);
        chosen.setLane(lane);

        /*Set the required lanes to false (block lanes)*/
        /*Set the number of frames the lane is supposed to be closed based on the
        obstacle that is sent to the lane*/
        blockLanes(chosen);

    }

}

/*Two approaches:
   1) We start by running all obstacle-samplers to get a entering-time for each of them.
*     For every update we increase the game-time value. When the game-time reaches the
*     entering-time for one of the obstacle samplers, the obstacle is sent in to the
*     game druing that update, and a new entering time is sampled.
*  2) We don't save the game-time, but for each update we decide for each obstacle
*     if it is sent in to the game or not.
*
* Remember this:
*   # */