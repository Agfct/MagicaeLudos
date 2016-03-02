package com.magicaeludos.mobile.magicaeludos.framework;

import java.util.Random;

/**
 * Created by Andreas on 24.02.2016.
 */
public class Probability {
    double rockRate = 1.0;
    double timeStep = 1.0/30.0;


    /*Random rand = new Random();
    return  Math.log(1-rand.nextDouble())/(-rate);*/

    public double probExp(double rate, double timeStep){
        /*Using a exponential arrival times with given rate, compute the probability that
         an arrival happens in a given timeStep.
         rate: Rate of arrivals
         timeStep: length of time steps*/

        return 1 - Math.exp(-rate*timeStep);
    }
    public boolean sendRock(double rockRate){
        boolean rock = true;
        Random rand = new Random();

        double acceptProb = probExp(rockRate, timeStep);
        if (rand.nextDouble() < acceptProb){
            rock = true;
        }
        return rock;
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