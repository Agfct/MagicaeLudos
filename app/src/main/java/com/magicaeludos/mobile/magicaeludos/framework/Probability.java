package com.magicaeludos.mobile.magicaeludos.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Andreas on 24.02.2016.
 */
public class Probability {

    /*Changable variables:*/
    private boolean lane1 = true; /*True if lanes is open for obstacle*/
    private boolean lane2 = true;
    private boolean lane3 = true;
    private int lane1Block = 0; /*Numbers of frames a lane is blocked before open again*/
    private int lane2Block = 0;
    private int lane3Block = 0;

    /*Constants to define game performance*/
    private double rockRate = 2; /*Arrival rate of rock*/
    private int rockLength = 30; /*Number of frames the rock blocks a lane. Might also block some space on the path*/
    private int rockWidth = 1; /*Number of lanes the rock is covering*/
    private int rockPri = 10; /*Prioritizing index used to choose between obstacles if they want to enter at same time*/
    private boolean rockCollect = false;

    private double logRate = 2.5;
    private int logLength = 20;
    private int logWidth = 2;
    private int logPri = 5;
    private boolean logCollect = false;

    private double dropRate = 2;
    private int dropLength = 10;
    private int dropWidth = 1;
    private int dropPri = 8;
    private boolean dropCollect = true;


    private double timeStep = 1.0/30.0; /*Time steps in seconds*/
    private int maxLaneBlock = 2; /*Maximal allowed number of lanes blocked at the same time*/
    private Set<ObstacleType> obs; /*List of obstacles to sample from*/



    /*Random rand = new Random();
    return  Math.log(1-rand.nextDouble())/(-rate);*/

    public Probability(Set<ObstacleType> obstacles){
        this.obs = obstacles;
    }

    public void setRockRate(double rate){rockRate = rate;}
    public void setRocklength(double length){rockRate = length;}
    public void setRockPri(double pri){rockRate = pri;}
    public void setLogRate(double rate){logRate = rate;}
    public void setLogLength(double length){logRate = length;}
    public void setLogPri(double pri){logRate = pri;}
    public void setDropRate(double rate){dropRate = rate;}
    public void setDropLength(double length){dropRate = length;}
    public void setDropPri(double pri){dropRate = pri;}
    public void setMaxLaneBlock(int lanes){maxLaneBlock = lanes;}

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

        boolean obstacle = false;
        Random rand = new Random();

        double acceptProb = probExp(rate, timeStep);
        double u = rand.nextDouble();
        //Log.w("probs: ", acceptProb+"  "+u);
        if (u < acceptProb){
            obstacle = true;
        }
        return obstacle;
    }
    public int lanesOpen(){
        /*Return umber of lanes currently open for obstacles. Not possible
        * to exceed maxLaneBlock*/
        int lanes = 0;

        if(lane1 == true){lanes++;}
        if(lane2 == true){lanes++;}
        if(lane3 == true){lanes++;}

        if(3-maxLaneBlock >= lanes){lanes=0;}

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
    public int obstaclesWidth(List<ObstacleProb> obstacleProbProbs){
        /*Return the total width of all obstacleProbProbs
        obstacleProbProbs: The obstacleProbProbs to send*/
        int width = 0;
        for(int i=0; i< obstacleProbProbs.size(); i++){
            width = width + obstacleProbProbs.get(i).getWidth();
        }
        return width;
    }
    public List<ObstacleProb> simulateObstacles(int connectedLanes, List<ObstacleProb> obstacleProbProbs, int lanesOpen){
        /*Simulate obstacleProbProbs*/


        /*Run all samplers and add to 'obstacleProbProbs' if true*/

        /*If there are 3 lanes open, simulate obstacles requiring 3-lane-width*/
        if(connectedLanes == 3){

        }
        /*If there are at least 2 lanes open and adjacent, simulate obstacles requiring 2-lane-width*/
        if(connectedLanes == 2 || connectedLanes == 3) {
            //Sample non-collectables:
            if (lanesOpen > 0) {
                if(obs.contains(ObstacleType.LOG)) {
                    if (sendObstacle(logRate)){obstacleProbProbs.add(new ObstacleProb(ObstacleType.LOG, logRate, logWidth, logLength, logPri, logCollect));}
                }
            }
            //Sample collectables:
        }

        /*Simulate obstacleProbProbs requiring only one lane*/
        //Sample non-collectables:
        if(lanesOpen>0) {
            if(obs.contains(ObstacleType.STONE)) {
                if (sendObstacle(rockRate)){obstacleProbProbs.add(new ObstacleProb(ObstacleType.STONE, rockRate, rockWidth, rockLength, rockPri, rockCollect));}
            }
        }
        //Sample collectables:
        if(obs.contains(ObstacleType.WATER_DROP)) {
            if (sendObstacle(dropRate)){obstacleProbProbs.add(new ObstacleProb(ObstacleType.WATER_DROP, dropRate, dropWidth, dropLength, dropPri, dropCollect));}
        }

        return obstacleProbProbs;
    }
    public int findLargestPri(List<ObstacleProb> obstacleProbProbs){
        /*Return the index of the obstacle with the largest priority index found in list*/
        /*obstacleProbProbs: list of obstacleProbProbs*/

        int pri = -1000;
        int index = 0;

        for (int i=0; i< obstacleProbProbs.size(); i++){
            if (pri < obstacleProbProbs.get(i).getPriIndex()){
                pri = obstacleProbProbs.get(i).getPriIndex();
                index = i;
            }
        }
        return index;
    }
    public List<ObstacleProb> cutObstacles(List<ObstacleProb> obstacleProbProbs){
        /*Cut required obstacleProbProbs from list
        obstacleProbProbs: list with obstacleProbProbs*/
        List<ObstacleProb> cutObstacleProbProbs = new ArrayList<ObstacleProb>();

        int index;
        int end = obstacleProbProbs.size();
        for (int i=0; i<end; i++){
            index = findLargestPri(obstacleProbProbs);
            cutObstacleProbProbs.add(obstacleProbProbs.get(index));
            obstacleProbProbs.remove(obstacleProbProbs.size()-1);
        }

        while(obstaclesWidth(cutObstacleProbProbs) > lanesOpen()){
            cutObstacleProbProbs.remove(cutObstacleProbProbs.size()-1);
        }

        return cutObstacleProbProbs;
    }
    public ObstacleProb chooseObstacle(List<ObstacleProb> obstacleProbProbs){
        /*Choose the one obstacle with the highest priority index.
        obstacleProbProbs: list with obstacleProbProbs*/

        int index = findLargestPri(obstacleProbProbs);
        return obstacleProbProbs.get(index);
    }
    public List<ObstacleProb> distributeObstacles(List<ObstacleProb> obstacleProbProbs, int lanesOpen){
        /*Distribute obstacle uniformly on the open lanes
        obstacleProbProbs: The obstacleProbProbs to distribute
        lanesOpen: Number of open lanes
         */

        Random random = new Random();
        int n = obstacleProbProbs.size();
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
            obstacleProbProbs.get(i).setLane(lanes.get(i));
        }


        return obstacleProbProbs;

    }
    public List<ObstacleProb> chooseLanes(int lanesOpen, List<ObstacleProb> obstacleProbProbs){
        /*Choose uniformly what lanes to send the obstacleProbProbs to
        lanesOpen: Number of open lanes
        obstacleProbProbs: list of obstacleProbProbs*/

        /*NOT DONE HERE!!!*/

        int n = obstacleProbProbs.size();

        if(n == 3){
            /*distribute singles*/
            obstacleProbProbs = distributeObstacles(obstacleProbProbs, lanesOpen);
        }
        if(n == 2){

        }

        return obstacleProbProbs;

    }
    public int chooseLane(ObstacleProb obstacleProbProb){
        /*Choose the lane the obstacle(non-collectable!) will be sent to*/
        /*obstacleProbProb: ObstacleProb to send*/

        int lanesOpen;
        if(obstacleProbProb.getCollect()==false){lanesOpen = lanesOpen();}
        else{lanesOpen = 0;
            if(lane1 == true){lanesOpen++;}
            if(lane2 == true){lanesOpen++;}
            if(lane3 == true){lanesOpen++;}
        }

        int lane;
        int width = obstacleProbProb.getWidth();
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
    public void blockLanes(ObstacleProb obstacleProbProb){
        /*Block the lanes that obstacleProbProb occupies and set blosk timer
        lane: The lane the obstacleProbProb starts blocking*/

        int lane = obstacleProbProb.getLane();
        int width = obstacleProbProb.getWidth();
        int time = obstacleProbProb.getLength();

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
    public ObstacleProb sendObstacles(){
        /*Need also to update lanes before this*/
        updateLanes();

        /*Check how many lanes are open for obstacles this frame*/
        int connectedLanes = connectedLanes();
        int lanesOpen = lanesOpen();


        /*choose/simulate what obstacleProbProbs to send this frame if there are enough open lanes*/
        List<ObstacleProb> obstacleProbProbs = new ArrayList<ObstacleProb>();
        if(lane1 || lane2 || lane3){
            obstacleProbProbs = simulateObstacles(connectedLanes, obstacleProbProbs, lanesOpen);
        }

        /*If no obstacle was chosen, end function*/
        ObstacleProb chosen = new ObstacleProb(ObstacleType.STONE,1.0,1,10,1,rockCollect);
        if(obstacleProbProbs.size()==0){
            return null;
        }
        else {
            /*Choose only one obstacle with the largest priority index*/
            chosen = chooseObstacle(obstacleProbProbs);

        /*FROM THIS ON, WE FOLLOW CHOICE 2) ABOVE!*/

        /*Distribute uniformly the object over the open lanes*/
        int lane = chooseLane(chosen);
        chosen.setLane(lane);

        /*Set the required lanes to false (block lanes)*/
        /*Set the number of frames the lane is supposed to be closed based on the
        obstacle that is sent to the lane*/
            blockLanes(chosen);
            //Log.w("chosen: ", chosen.printObstacle());
            return chosen;
        }
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

