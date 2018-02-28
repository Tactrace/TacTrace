/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import static Model.Database.read;
import View.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lusidOSC.LusidClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import lusidOSC.LusidObject;
/**
 *
 * @author user009
 */
public class LusidOSCJavaApp {

    //initialize all objects
    LusidClient lusidClient;
    //setup env
    int width = 8, height = 6;
    
    //Stages Variables
    int QuestionsNum, TestScore, NumOfWrongAnswers, CurrentLevelStage;
    boolean RightBarcode1, RightBarcode2, ContinueTraining, logedin;
    String barcode1, barcode2;
    //Shape[] shapes;
    Shape[] shapes1 = {new Shape("1", "squar", 1), new Shape("2", "triangle", 1), new Shape("3", "circle", 1), new Shape("4", "rectangle", 1)};
    Shape[] shapes2 = {new Shape("1", "squar", 1), new Shape("2", "triangle", 1), new Shape("3", "circle", 1), new Shape("4", "rectangle", 1)};
    Shape[] shapes3 = {new Shape("1", "squar", 1), new Shape("2", "triangle", 1), new Shape("3", "circle", 1), new Shape("4", "rectangle", 1)};

    Database db=new Database();
    
    //current state for added object
    ArrayList<LusidObject> lusidArr = new ArrayList<LusidObject>();
	
    /*************************************Modify***
    Shape shape = new Shape();
    Task task = new Task();
    Player player =	new Player();

    //setup env
    int width = 8;
    int height = 6;

	//boolean isTask = false;
	
	// advance level has isSubTask
	boolean isSubTask = false;
	
	// sub task id
	int subTaskID;
	
	// a flag to indecate if answer is provided after each task
	private volatile boolean isAnswered = false;
	
	//an object to hold the answer for the current Task
	Shape[] taskShape;
	
	double oldDist ;

**********************************************************/		
	public LusidOSCJavaApp(){
		// create the client, on port 3333.
		lusidClient = new LusidClient(this, 3333);
		//System.out.println("lusid osc java app ");
/*************************************************************/
		//System.out.println(View.getisRunning());
		while(MainFrame.isIsRunning()){
			//System.out.println("is running ");
			
                        
			while(!logedin){
                            //read barcode
                            
                            CurrentLevelStage = db.ChildLogin(barcode1);
                            if(logedin)
                                break;
                        }//END WHILE
			//check level + stage
                    switch (CurrentLevelStage) {
                        case 11:
                            StartLesson1();
                            break;
                        case 12:
                            StartTraining1();
                            break;
                        case 13:
                            StartTesting1();
                            break;
                        case 21:
                            StartLesson2();
                            break;
                        case 22:
                            StartTraining2();
                            break;
                        case 23:
                            StartTesting2();
                            break;
                        case 31:
                            StartLesson3();
                            break;
                        case 32:
                            StartTraining3();
                            break;
                        case 33:
                            StartTesting3();
                            break;
                        default:
                            break;
                    }//END SWITCH

		/*
                    while(!isAnswered || (!isAnswered && !isSubTask) ){	
        		try {
                		Thread.sleep(5000);
				//ask again
				//task.getTask(1,taskShape );
			} catch (InterruptedException e) {
                            // TODO Auto-generated catch block

                            }
                    }
                    */
		}//END MainFrame
                //if(!MainFrame.isIsRunning())
                    //new TeacherController();
		}//END Constructor
	
	// -------------------------------------------------------------------
	// these methods are called whenever a LusidOSC event occurs.
	// -------------------------------------------------------------------
	// called when an object is added to the scene
	public void addLusidObject(LusidObject lObj) {
		//System.out.println("add lusid object");
		
		//when object is added we add an instance of the object to lusidObj arraylist
		lusidArr.add(lObj);
		
		// if we are in exploring level
		if(true/*!View.getisTask()*/){
			try{
				//String name = shape.getName(lObj.getUniqueID());
				 //System.out.println(name);
				 //System.out.println(shape.getDesc(lObj.getUniqueID()));
				 //String audio = shape.getAud(lObj.getUniqueID());
				 //Task.playAudio(audio);
			}
			catch(Error e)
			{
				//System.out.println("no shape");
			}
		} else {
			if(true/*!isSubTask*/){
				//checkAnswer();
			}
			
			
		}
	//if(lObj.getUniqueID().equals("0x111111AA1111"))	
          //      System.out.println("you put the right shape");
        //else
          //      System.out.println("error");
	  //System.out.println("add object: "+lObj.getUniqueID());
	  //System.out.println("  location = ("+lObj.getX()+","+lObj.getY()+","+lObj.getZ()+")");
	  //System.out.println("  rotation = ("+lObj.getRotX()+","+lObj.getRotY()+","+lObj.getRotZ()+")");
	  //System.out.println("      data = ("+lObj.getEncoding()+","+lObj.getData()+")");
	  //System.out.println("#######################################");
	  //System.out.println(lusidArr.size());
	  
	}
	// called when an object is removed from the scene
	public void removeLusidObject(LusidObject lObj) {
		lusidArr.remove(lObj);
		//System.out.println("remove object: "+lObj.getUniqueID());
		
	}
	// called when an object is moved
	public void updateLusidObject (LusidObject lObj) {
		//System.out.println("update object: "+lObj.getUniqueID());
		
		ArrayList<LusidObject> currentLusidList = new ArrayList<>(Arrays.asList(lusidClient.getLusidObjects()));
		
		
		//System.out.println("current size "+currentLusidList.size());
		
		//System.out.println("update object: "+lObj.getUniqueID());
		if(isSubTask && currentLusidList.size() > 1 ){
			switch(subTaskID){
			case 1:
				//System.out.println(calculateDistance(currentLusidList) +" case 1 "+ oldDist);
				if(oldDist - calculateDistance(currentLusidList) > 10  ){
					System.out.println("horray");
					isSubTask = false;
					correctAnswer();
				} else {
					//wrongAnswer();
				}
				break;
			case 2:
				if(calculateDistance(currentLusidList) - oldDist > 10  ){
					System.out.println("horray");
					isSubTask = false;
					correctAnswer();
				} 
				break;
			}
			
		}
	}
	
	public void clearLusidObj(){
		lusidArr.clear();
	}
	
	public LusidObject getAddedObj(){
		return lusidArr.isEmpty() ? null : lusidArr.get(lusidArr.size()-1);
	}
	
	public void checkAnswer(){
		switch(player.getlevel()){
		case 1:
		try{	
                    if(getAddedObj().getUniqueID().startsWith(taskShape[0].getUID())){
				correctAnswer();
			}
			else{
				wrongAnswer();
			}
                }catch(NullPointerException e){ 
                
                }
			break;
		case 2:
			// check if the first object is correct
			if(lusidArr.size() == 1){
				if(getAddedObj().getUniqueID().startsWith(taskShape[0].getUID())){
				// if the first object correct, ask for the other one
					//add the other one
					System.out.println("Add the other one");
				} else {
					wrongAnswer();
				}
			} else {
				LusidObject lastObject = getAddedObj();
				if(lastObject.getUniqueID().startsWith(taskShape[0].getUID())){
					correctAnswer();
				}
				else{
					wrongAnswer();
				}
			}
			break;
		case 3:
			// check if the first object is correct
						if(lusidArr.size() == 1){
							if(getAddedObj().getUniqueID().startsWith(taskShape[0].getUID())){
							// if the first object correct, ask for the other one
								//add the other one
								System.out.println("Add the other shape");
							} else {
								wrongAnswer();
							}
						} else {
							LusidObject lastObject = getAddedObj();
							if(lastObject.getUniqueID().startsWith(taskShape[1].getUID())){
								//set isSubTask
								//isSubTask = true;
								//save current state
								oldDist = calculateDistance(lusidArr);
								System.out.println("DISTENCE " +calculateDistance(lusidArr));
								correctAnswer();
								
							}
							else{
								wrongAnswer();
							}
						}
						break;
			
		}
		
	}
	
	public double calculateDistance(ArrayList<LusidObject> oldState){
		
		    LusidObject lObj1 = oldState.get(0);
		    // shift the X and Y so they are centered on the screen.
		    int x1 = width/2 + lObj1.getX();
		    int y1 = height/2 - lObj1.getY();
		    float rotation1 = lObj1.getRotZ();
		    
		        LusidObject lObj2 = oldState.get(1);
		        // shift the X and Y so they are centered on the screen.
		        int x2 = width/2 + lObj2.getX();
		        int y2 = height/2 - lObj2.getY();
		        float rotation2 = lObj2.getRotZ();

		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public void correctAnswer(){
		System.out.println("thats correct");
		//View.setTask("CORRECT");
		//give positive feedback
		Task.playAudio("correct-"+new Random().nextInt(1));
		
		//increment score
		player.answerCorrect();
		
		//if(!isSubTask){
			//set the flag to true
			isAnswered = true;
		//}
		
	}
	
	public void wrongAnswer(){
		System.out.println("Try again");
		//View.setTask("SORRY .. TRY AGAIN");
		//Try again audio
		Task.playAudio("tryAgain");
		
		player.answerWrong();
	}
	
private void StartLesson1(){

}//END StartLesson 1
private void StartTraining1(){

//Play an audio to describe the purpose of the training and give instructions for VI child2
        String barcode = null;
        int QuestionsNum = 0;
        //("training1Q"+QuestionsNum);
        int NumOfWrongAnswers = 0;
        boolean RightBarcode, ContinueTraining = false;
        //WHILE (QuestionsNum not equals 4)
        while (QuestionsNum != 4) { // asking the child 4 Q           
            QuestionsNum++;
            System.out.println("Playing soundtrack:" + QuestionsNum);
            PlaySound("training1Q" + QuestionsNum + ".mp3");
            //Play question[QuestionsNum]
            RightBarcode = false;
            while (!RightBarcode) {
                System.out.println("Enter the barcode:");
                barcode = read.next();
                //READ ShapeBarcode
                if (!barcode.equals(null)) {
                    RightBarcode = true;
                    while (NumOfWrongAnswers != 3) {
                        System.out.println("Wrong:" + NumOfWrongAnswers);
                        if (barcode.equals(shapes[QuestionsNum - 1].getID())) {
                            System.out.println("playing Pos1");
                            PlaySound("Pos1.mp3");
                            break;
                        }//END IF
                        else if (NumOfWrongAnswers == 2) {
                            //Play a negative audio message
                            //Go back to lesson
                            break;
                        } //END IF
                        else {
                            NumOfWrongAnswers++;
                            System.out.println("error");
                            barcode = read.next();
                            //Play a hint
                        }//END ELSE
                    }//while3
                }//END IF
                else {
                    System.out.println("error barcode");
                }
            }//while2    
        }//while1	
        QuestionsNum = 0;
        //System.exit(1);
        //Play an audio message asking the child if he wants more training questions 
        //ContinueTraining to child answer
        while (ContinueTraining) {
            QuestionsNum++;
            //Play question[QuestionsNum]
            NumOfWrongAnswers = 0;
            RightBarcode = false;
            while (!RightBarcode) {
                //READ ShapeBarcode
                if (true/*Barcode is recognized*/) {
                    RightBarcode = true;
                    while (NumOfWrongAnswers != 3) {
                        if (true/*VI child puts the right shape*/) {
                            //Play a positive audio feedback
                            break;
                        }//END IF
                        else {
                            if (NumOfWrongAnswers == 2) {
                                //Play a negative audio message
                                //Go back to lesson
                                break;
                            }//END IF
                            NumOfWrongAnswers++;
                            //Play a hint
                        }//END ELSE
                    }//END WHILE
                }//END IF
                else {
                    //Play an audio error message
                }//END ELSE
            }//END WHILE
            //Play an audio message asking the child if he wants more training questions 
            //ContinueTraining to child answer
        }//END WHILE
        //Update the LevelStage for this child to testing 1
        //Move to testing 1
}//END OF StartTraining1
private void StartTesting1(){
//Play an audio to describe the purpose of the testing and give instructions for VI child
        QuestionsNum = 0;
        TestScore = 0;
        while (QuestionsNum != 4) {
            QuestionsNum++;
            //Play question[QuestionsNum]
            RightBarcode1 = false;
            while (!RightBarcode1) {
                //READ ShapeBarcode
                if (true/*Barcode is recognized*/) {
                    RightBarcode1 = true;
                    if (true/*VI child puts the right shape*/) {
                        //Play a positive audio feedback congratulating the child
                        TestScore++;
                    }//END IF
                    else {
                        //Play a negative audio message informing the child that he made a mistake
                    }//END ELSE
                }//END IF
                else {
                    //Play an audio error message
                }//END ELSE
            }//END WHILE
        }//END WHILE
        if (true/*TestScore < 80%*/) {
            //Play a negative audio message …
            //Go back to the lesson 
        }//END IF
        else {
            //Play a positive audio message …
            //Update the LevelStage for this child to lesson 2
            //Update the level for this child to level 2
            //Move on to the next level
        }//END ELSE 	
}//END StartTesting 1
private void StartLesson2(){

}//END StartLesson 2
private void StartTraining2(){

}//END StartTraining 2
private void StartTesting2(){

}//END StartTesting 2
private void StartLesson3(){

}//END StartLesson 3
private void StartTraining3(){
    //Play an audio to describe the purpose of the training 
        String barcode;
        int QuestionsNum = 0, NumOfWrongAnswers;
        boolean RightBarcode1, RightBarcode2, ContinueTraining;
        while (QuestionsNum != 8) {
            //Give instructions for VI child
            QuestionsNum++;
            NumOfWrongAnswers = 0;
            RightBarcode1 = false;
            while (!RightBarcode1) {
                //READ ShapeBarcode
                if (true/*Barcode is recognized*/) {
                    RightBarcode1 = true;
                    //Play question[QuestionsNum]
                    RightBarcode2 = false;
                    while (!RightBarcode2) {
                        //READ ShapeBarcode
                        if (true/*Barcode is recognized*/) {
                            RightBarcode2 = true;
                            while (NumOfWrongAnswers != 3) {
                                if (true/*VI child puts the right shapes*/) {
                                    //Play a positive audio feedback
                                    break;
                                }//END IF
                                else if (NumOfWrongAnswers == 2) {
                                    //Play a negative audio message Go back to lesson Break
                                }//END ELSE IF
                                else {
                                    NumOfWrongAnswers++;
                                    //Play a hint
                                }//END ELSE
                            }//END WHILE
                        }//ENDIF
                        else {
                            //Play an error message 
                        }//END ELSE 
                    }//END WHILE 
                }//END IF 
                else {
                    //Play an error message
                }//END ELSE
            }//END WHILE
        }//END WHILE
        //Play an audio message asking the child if he wants more training questions 
        ContinueTraining = false;//child answer 
        while (ContinueTraining) {
            if (true/*VI child wants more training questions*/) {
                QuestionsNum = 0;
                //Give instructions for VI child 
                QuestionsNum++;
                NumOfWrongAnswers = 0;
                RightBarcode1 = false;
                while (!RightBarcode1) {
                    //READ ShapeBarcode
                    if (true/*Barcode is recognized*/) {
                        RightBarcode1 = true;
                        //Play question[QuestionsNum]
                        RightBarcode2 = false;
                        while (!RightBarcode2) {
                            //READ ShapeBarcode
                            if (true/*Barcode is recognized*/) {
                                RightBarcode2 = true;
                                while (NumOfWrongAnswers != 3) {
                                    if (true/*VI child puts the right shapes*/) {
                                        //Play a positive audio feedback  Break
                                        break;
                                    }//END IF
                                    else if (NumOfWrongAnswers == 2) {
                                        //Play a negative audio message 
                                        //Go back to lesson Break
                                        break;
                                    }//END ELSE IF
                                    else {
                                        NumOfWrongAnswers++;
                                    }//END ELSE
                                    //Play a hint 
                                }//END WHILE 
                            }//ENDIF
                            else {
                                //Play an error message 
                            }//END ELSE 
                        }//END WHILE 
                    }//END IF 
                    else {
                        //Play an error message
                    }//END ELSE
                }//END WHILE
            }//END IF
            //Play an audio asking if the child want to continue training 
            if (true/*the child said NO*/) {
                ContinueTraining = false;
            }//END IF
        }//END WHILE
        //Update the LevelStage for this  child to testing3
        //Go to the testing 3
}//END StartTraining 3
private void StartTesting3(){
    //Play an audio to describe the purpose of the testing 
                QuestionsNum = TestScore = NumOfWrongAnswers = 0;
                RightBarcode1 = RightBarcode2 = false;
               while (QuestionsNum != 8){
                       //Give instructions for VI child
                           QuestionsNum++;
                           NumOfWrongAnswers = 0;
                           RightBarcode1 = false;
               while (!RightBarcode1){
                       //READ ShapeBarcode
                               if (true/*Barcode is recognized*/){
                                RightBarcode1 = true;
                                //Play question[QuestionsNum]		
                                 RightBarcode2 = false;
                               while (!RightBarcode2){
                                    //READ ShapeBarcode
                                    if (true/*Barcode is recognized*/){
                                        RightBarcode2 = true;
                                        while (NumOfWrongAnswers != 3){
                                            if (true/*VI child puts the right shapes*/){
                                                //Play a positive audio feedback
                                                TestScore++;
                                                break;
                                            }//END IF
                                            else{
                                                //Play a negative audio message
                                            }//END ELSE
                                        }//END WHILE
                                    }//ENDIF
                                    else{
                                        //Play an error message
                                    }//END ELSE
                               }//END WHILE
                               }//END IF
                               else{
                                   //Play an error message
                               }//END ELSE
               }//END WHILE
               }//END WHILE
               if (TestScore < 80){
                    //Play a negative audio message
                    //Go back to the lesson 
                }//END IF
               else{
               //Play a positive audio feedback congratulating the child because he finished all the levels
               }//END ELSE
}//END StartTesting 3

    /**
     *
     * @param sound
     */
    public void PlaySound(String sound) {
        try {
            Player player;
            File file = new File(sound);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            /**
             ** To Know the duration
             * 
             */
            //int duration=0;
            //AudioFile audioFile = AudioFileIO.read(file);
            //duration= audioFile.getAudioHeader().getTrackLength();
            /**
             * 
             */
            //Adding delay after the sound
            if (sound.contains("Pos")) {
                player.play();
                TimeUnit.SECONDS.sleep(1);
            } else {
                player.play();
            }
        } catch (FileNotFoundException | JavaLayerException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//END PlaySound
}//End Class
