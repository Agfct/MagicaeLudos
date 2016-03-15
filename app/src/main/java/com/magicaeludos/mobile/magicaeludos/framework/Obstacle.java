package com.magicaeludos.mobile.magicaeludos.framework;

/**
 * Created by Andreas on 07.03.2016.
 */
public class Obstacle {
    String name; /*Name of the object*/
    double rate; /*Arrival rate of object*/
    int width; /*Number of lanes the object is covering*/
    int length; /*Number of frames the object blocks a lane. Might also block some space on the path*/
    int priIndex; /*Prioritizing index used to choose between obstacles if they want to enter at same time*/
    int lane; /*If an obstacle is sent to the game, we give it a lane (1,2 or 3) to be sent in to. If
                an obstacle of width 2 is sent, a value of lane=2 means it covers its length from lane 2.*/

    public Obstacle(String name, double rate, int width, int length, int priIndex){
        this.name = name;
        this.rate = rate;
        this.width = width;
        this.length = length;
        this.priIndex = priIndex;
        lane = 0;
    }

    public String getName(){
        return name;
    }
    public double getRate(){
        return rate;
    }
    public int getWidth(){return width;}
    public int getLength(){return length;}
    public int getPriIndex(){return priIndex;}
    public int getLane(){return lane;}
    public String printObstacle(){
        String obstacle = "Name: "+name+", Rate: "+rate+", Width: "+width+", Length: "+length;
        return obstacle;
    }

    public void setLane(int n){lane = n;}
}
