package com.magicaeludos.mobile.magicaeludos.implementation.activities;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.Log;

import com.magicaeludos.mobile.magicaeludos.R;
import com.magicaeludos.mobile.magicaeludos.framework.Audio;
import com.magicaeludos.mobile.magicaeludos.framework.BGM;
import com.magicaeludos.mobile.magicaeludos.framework.Content;
import com.magicaeludos.mobile.magicaeludos.framework.GUIhandler;
import com.magicaeludos.mobile.magicaeludos.framework.GameSetting;
import com.magicaeludos.mobile.magicaeludos.framework.Grid;
import com.magicaeludos.mobile.magicaeludos.framework.Layout;
import com.magicaeludos.mobile.magicaeludos.framework.MotherActivity;
import com.magicaeludos.mobile.magicaeludos.framework.ObstacleType;
import com.magicaeludos.mobile.magicaeludos.framework.Probability;

import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler;
import com.magicaeludos.mobile.magicaeludos.framework.TouchHandler.TouchEvent;
import com.magicaeludos.mobile.magicaeludos.framework.Water;
import com.magicaeludos.mobile.magicaeludos.implementation.Background;
import com.magicaeludos.mobile.magicaeludos.implementation.Dummy;
import com.magicaeludos.mobile.magicaeludos.implementation.GameAudio;
import com.magicaeludos.mobile.magicaeludos.implementation.Obstacle;
import com.magicaeludos.mobile.magicaeludos.implementation.ObstacleHandler;
import com.magicaeludos.mobile.magicaeludos.implementation.Player;
import com.magicaeludos.mobile.magicaeludos.implementation.Village;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Anders on 10.02.2016.
 */
public class GameContent implements Content{

    private MotherActivity activity;
    private Layout layout;
    private Probability prop;
    private Grid grid;
    private TouchHandler touchHandler;
    private GUIhandler guIhandler;
    private Background background;
    private ObstacleHandler obstacles;
    private GameSetting gameSetting;
    private Paint paint = new Paint();


    //Time
    private double gameTime = 0;
    private double currentGameTime = 0;
    private double startTime = 0;
    private double timeElapsed = 0;

    //Pause
    private boolean running = false;

    //GameObjects
    private Player player;
    private double speed;
    public Water water;
    private int waterDropAmount;
    private int hitCounter;
    private boolean ending = false;

    //Audio
    private Audio gameAudio;
    private BGM backgroundMusic;


    //Test
    ArrayList<Dummy> dummies;


    public GameContent(MotherActivity activity, Layout layout) {
        this.activity = activity;
        this.layout = layout;
        this.grid = new Grid(this);
        this.touchHandler = new TouchHandler(layout, activity.getScreenWidth(), activity.getScreenHeight());
        this.gameAudio = new GameAudio(activity);
        this.obstacles = new ObstacleHandler(this);
        speed = activity.getScreenHeight()/100*1;

        initializeGameSettings();

//        startGame(); //TODO: 3.. 2.. 1.. Countdown before startingGame ?
    }

    private void initializeGameSettings(){

        //Game Settings. Contains the variables for game difficulty
        gameSetting = new GameSetting(this, activity.getIntent().getIntExtra(activity.getString(R.string.level), 0));

        //Player
        player = new Player(this, grid.getPlayerLane(2),grid.getColWidth()/4*3,grid.getInnerHeight()*3, BitmapFactory.decodeResource(activity.getResources(), R.drawable.animate_avatar));

        //Water
        water = gameSetting.getWater();

        //GUI handler
        this.guIhandler = new GUIhandler(this,grid);

        //Probability
        prop = new Probability(gameSetting.getUsableObstacles(), gameSetting.getGameDifficulty());

        //Images
        background = new Background(this, BitmapFactory.decodeResource(activity.getResources(), R.drawable.bck_africa));

        //Audio
        backgroundMusic = gameAudio.createMusic(activity.getBaseContext(), R.raw.running_music);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(activity.getVillage().getBgmLevel());

        //Testing
//        dummies = new ArrayList<>();
//        dummies.add(new Dummy(this, new Point(grid.getLane(1).x, grid.getLane(1).y + grid.getRowHeight() * 5), grid.getColWidth(), grid.getRowHeight(), Color.RED));
//        dummies.add(new Dummy(getActivity(),grid.getLane(2),grid.getColWidth(),grid.getRowHeight()));
//        dummies.add(new Dummy(this, new Point(grid.getLane(3).x, grid.getLane(3).y + grid.getRowHeight() * 5), grid.getColWidth(), grid.getRowHeight(), Color.RED));

    }

    /**
     * Updates the game status. Update is ran every tick of the thread.
     * (How many ticks are based on the AndroidThread class current FPS setting)
     * Update shoud contain everything that needs to be updated in the game (movement, calculations.. everything!)
     */
    @Override
    public void update() {
        if(running) {
            runGameTime();
            double test = prop.probExp(0.5, 1.0 / 30.0);
//        Log.w("GameContent", "Dette er test variabelen2: "+ test);
            List<TouchEvent> touchEvents = touchHandler.getTouchEvents();
            player.update(touchEvents);
            background.update();
            obstacles.update();
            //Check here if player and Object collides: ?
//            water.addCleanWater(1);
            //Updates the GUI:
            guIhandler.update();
        }
    }

    /**
     * The draw method draws all the sprites, items, text, images that should be drawn (shown) on screen.
     * Draw is triggered after update in the AndroidThread, but it is important that there is no "update" here as
     * drawing is suposed to be seperated from updating (to prevent logical errors),
     * but there is also an option to skip frames which causes draw not to be drawn while the game will still update.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        //BACK
        //Draws the background
//        canvas.drawBitmap(temporaryBackground,new Rect(0,0,temporaryBackground.getWidth(),temporaryBackground.getWidth()),grid.getScreenBorders(),paint);
        background.draw(canvas);

        //MIDDLE
        paint.setColor(Color.WHITE);
        paint.setTextSize(this.getActivity().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.large_text));
        canvas.drawText("Game Time: " + currentGameTime, 100, 100, paint);
//        canvas.drawText("Time Elapsed: " + timeElapsed, 100, 120, paint);
        paint = new Paint();

        //Test
//        for (Dummy dummy: dummies
//                ) {
//            dummy.draw(canvas);
//        }
        //Test Ends

        obstacles.draw(canvas);
        player.draw(canvas);
        grid.draw(canvas);

        //FRONT
        //Draws the GUI:
        guIhandler.draw(canvas);

    }

    /**
     * Counts game time in seconds
     */
    private void runGameTime(){
        timeElapsed = System.currentTimeMillis() - startTime;
        int seconds = (int) (timeElapsed / 1000);
        currentGameTime = gameTime - seconds;

        //TODO: Change this if endGame location
        if(currentGameTime <= 0) {
            spawnVillage();
//            endGame();
        }

    }

    protected void startGame(){
        if(!running){
            startTime = System.currentTimeMillis();
            running = true;
            startBackgroundAudio();
        }
    }


    public void endGame(){
        running = false;
        stopBackgroundAudio();
        Intent intent = new Intent(activity, AfterGameActivity.class);
        intent.putExtra("cleanWater", water.getCleanWater());
        intent.putExtra("dirtyWater", water.getDirtyWater());
        intent.putExtra("dirtyWaterMultiplier", activity.getVillage().getDirtyWaterMultiplier());
        updateVillage();
        activity.goTo(intent);

    }

    private void spawnVillage() {
        if (ending == false){
            ending = true;
            Obstacle village = new Obstacle(this,
                    BitmapFactory.decodeResource(this.getActivity().getResources(),
                            R.drawable.stone_small),0, 3, 5, ObstacleType.VILLAGE);
            obstacles.add(village);
        }
    }


    //Updates the village after end game and updates all variables
    private void updateVillage(){
        //Updating current values
        Village village = activity.getVillage();

        //Increasing number of runs by 1
        village.setTotalAmountOfRuns(village.getTotalAmountOfRuns() + 1);

        //Calculating total water amount and adding it to the village
        int totalWater = water.getCleanWater() + (int)(water.getDirtyWater() * village.getDirtyWaterMultiplier());
        village.addTotalWater(totalWater);

        //Adds a new top score to mostWater in on run
        if(totalWater > village.getMostWaterInOneRun()){
            village.setMostWaterInOneRun(totalWater);
        }

        int runsLeftToday = village.getRunsLeftToday();
        //If this is the last run of the day
        if(runsLeftToday == 1){
            village.setCurrentDay(village.getCurrentDay() + 1);
            village.setRunsLeftToday(village.getNUMBEROFRUNSPRDAY());


            //FEEDING THE VILLAGERS

            int currentNumberOfVillagers = village.getNrOfVillagers();
            int totalWaterLeft = village.getTotalWater() - (currentNumberOfVillagers*village.getAMOUNTOFWATERPRVILLAGER());
            //If you do not have enough water
            //Remove all water
            //Set number of villagers down one level
            Log.w("GameContent", "OverflowWater: " + (village.getTotalWater() - (currentNumberOfVillagers * village.getAMOUNTOFWATERPRVILLAGER())));
            if(totalWaterLeft < 0){
                village.setTotalWater(0);
                int newAmountOfVillagers = 0;
                //Cannot fall below 1 villager
                if(currentNumberOfVillagers == 1 ){
                    newAmountOfVillagers = 1;
                }else {
                    ArrayList<Integer> villagerMilestones = village.getVillagerMilestones();
                    for (int i = 0; i < villagerMilestones.size(); i++){
                        if (currentNumberOfVillagers == villagerMilestones.get(i)) {
                            if(i == 0){
                                newAmountOfVillagers = 1;
                                break;
                            }else {
                                newAmountOfVillagers = villagerMilestones.get(i-1);
                                break;
                            }
                        }
                    }
                }
                village.setNrOfVillagers(newAmountOfVillagers);
            }
            //If you have enough water
            //Remove all water required for villagers
            else if( totalWaterLeft == 0){
                village.setTotalWater(0);
                village.setNrOfVillagers(village.getNrOfVillagers());
            }else if (totalWaterLeft > 0) {
                //If the amount of excessive water is enough to go up on level then set the number of villagers up one level.
                int nrOfVillagersFedByExcessiveWater = totalWaterLeft/village.getAMOUNTOFWATERPRVILLAGER();
                Log.w("GameContent", "TotalWaterLeft: " + totalWaterLeft + " ExcessiveVillagers: " + nrOfVillagersFedByExcessiveWater);
                int newAmountOfVillagers = 0;
                ArrayList<Integer> villagerMilestones = village.getVillagerMilestones();
                for (int i = 0; i < villagerMilestones.size(); i++){
                    Log.w("GameContent","exsessive + currentVillagers = "+ (nrOfVillagersFedByExcessiveWater + currentNumberOfVillagers)+ " Milestone: "+ villagerMilestones.get(i));
                    if(nrOfVillagersFedByExcessiveWater + currentNumberOfVillagers < villagerMilestones.get(i)){
                        if(i == 0){ //Below the lowest milestone
                            newAmountOfVillagers = village.getNrOfVillagers();
                            village.setNrOfVillagers(newAmountOfVillagers);
                            int waterLeftAfterNewAmountOfVillagers = totalWaterLeft - ((newAmountOfVillagers - currentNumberOfVillagers) * village.getAMOUNTOFWATERPRVILLAGER());
                            Log.w("GameContent", " (1)WaterLeftAfterNewAmountOfVillagers: " + waterLeftAfterNewAmountOfVillagers);
                            village.setTotalWater(waterLeftAfterNewAmountOfVillagers);
                            break;
                        }else if(i > 0) {
                            newAmountOfVillagers = villagerMilestones.get(i - 1);
                            village.setNrOfVillagers(newAmountOfVillagers);
                            int waterLeftAfterNewAmountOfVillagers = totalWaterLeft - ((newAmountOfVillagers - currentNumberOfVillagers) * village.getAMOUNTOFWATERPRVILLAGER());
                            Log.w("GameContent", " (2)WaterLeftAfterNewAmountOfVillagers: " + waterLeftAfterNewAmountOfVillagers);
                            village.setTotalWater(waterLeftAfterNewAmountOfVillagers);
                            break;
                        }
                    }
                    //The above code does not consider the case where the milestone is above the highest available milestone so this "if clause" handles that case.
                    //If all other milestones is to low:
                    if(i == villagerMilestones.size()-1){
                        //If the current milestone has been before
                        if(villagerMilestones.get(i) > village.getNrOfVillagers()){
                            newAmountOfVillagers = villagerMilestones.get(i);
                            village.setNrOfVillagers(newAmountOfVillagers);
                            int waterLeftAfterNewAmountOfVillagers = totalWaterLeft - ((newAmountOfVillagers - currentNumberOfVillagers) * village.getAMOUNTOFWATERPRVILLAGER());
                            Log.w("GameContent", " (3)WaterLeftAfterNewAmountOfVillagers: " + waterLeftAfterNewAmountOfVillagers);
                            village.setTotalWater(waterLeftAfterNewAmountOfVillagers);
                        }else{
                            //If there is no more VillagerMilestones:
                            //Set number of villagers to the highest possivble milestone
                            Log.w("GameContent","No more milestones, highest milestone selected");
                            newAmountOfVillagers = villagerMilestones.get(i);
                            village.setNrOfVillagers(newAmountOfVillagers);
                            int waterLeftAfterNewAmountOfVillagers = totalWaterLeft - ((newAmountOfVillagers - currentNumberOfVillagers) * village.getAMOUNTOFWATERPRVILLAGER());
                            Log.w("GameContent", " (4)WaterLeftAfterNewAmountOfVillagers: " + waterLeftAfterNewAmountOfVillagers);
                            village.setTotalWater(waterLeftAfterNewAmountOfVillagers);
                        }


                    }
                }


            }
        }
        //Else if this is not the last run
        else if(runsLeftToday > 1){
            village.setRunsLeftToday(runsLeftToday - 1);

        }else{
            Log.e("GameContent", "ERROR: THIS SHOUD NOT HAPPEN, CHECK updateVillage() METHOD");
        }

        //Saves the village data to storage
        village.saveVillageData();
    }


    public MotherActivity getActivity() {
        return activity;
    }

    public Grid getGrid() {
        return grid;
    }

    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    public double getSpeed(){return speed;}

    public void setGameSpeed(int speed){
        this.speed = activity.getScreenHeight()/(double)500*speed;
    }

    public int getBackgroundHeight(){return background.getBackgroundHeight();}

    public Player getPlayer() {
        return player;
    }

    public void setGameTime(double gameTime) {
        this.gameTime = gameTime;
    }

    public void setWaterDropAmount(int waterDropAmount) { //TODO: MOVE to Water class
        this.waterDropAmount = waterDropAmount;
    }

    public int getWaterDropAmount() {
        return waterDropAmount;
    }

    public Probability getProp(){return prop;}

    public void incrementHitCounter(){hitCounter+=1;}

    public int getHitCounter(){return hitCounter;};

    public Audio getGameAudio() {
        return gameAudio;
    }

    public void startBackgroundAudio(){
        backgroundMusic.play();
    }
    public void stopBackgroundAudio(){
        backgroundMusic.stop();
    }
    public void pauseBackgroundAudio(){
        backgroundMusic.pause();
    }


    public boolean isRunning() {
        return running;
    }

    public boolean getEnding(){return ending;}
}
