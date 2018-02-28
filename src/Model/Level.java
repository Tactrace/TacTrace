/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author user009
 */
public class Level {
    private int level;
    private int stage;
    //private Lesson lesson;
    //private Training training;
    //private Test test;

    //default values when new child start login
    public Level() {
        this.level=1;
        this.stage=1;
    }
    
    //When the child relogin we call this method
    //@return values [11,12,13,21,22,23,31,32,33]
    public int CurrentStage() {
        return Integer.parseInt(this.level+""+this.stage);
    }
    
    //get the current level
    public int getLevel() {
        return level;
    }

    //get the current stage
    public int getStage() {
        return stage;
    }
    
    //Forward to the next Level [1-3]
    //@return true if there is a next level
    //@return false if the level is more than level 3
    public boolean NextLevel() {
        if(this.level <3) {
            this.level++;
            return true;
        }
        return false;
    }
    
    //Forward to the next stage [Lesson(1),Training(2),Test(3)]
    //@return true if the stage is less than 3
    //@return false if stage is more than 3
    public boolean NextStage() {
        if(this.stage<3){
            this.stage++;
            return true;
        }
        return false;
    }
}
