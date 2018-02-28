/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author user009
 */
public class Database {

    public static Scanner read = new Scanner(System.in);

    private Connection conn;
    private Statement statement;
    private boolean connected = false;

    public Connection openConnection() {
        if (conn == null) {
            String url = "jdbc:mysql://localhost/";
            String dbName = "users";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "";

            try {
                Class.forName(driver);
                this.conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);

                connected = true;
            } catch (ClassNotFoundException | SQLException sqle) {
                connected = false;
            }
        }
        return conn;
    }
//Add Child

    public String AddChild(String name, String phone) {
        if (connected) {
            try {
                statement = (Statement) conn.createStatement();
                String sql = "Insert Into reader (name,phone) Values('" + name + "','" + phone + "')";
                int rowCount = statement.executeUpdate(sql);
                return "" + rowCount;
            } catch (SQLException sqle) {
                return "Unssuccessful Addition";
            }
        }
        return "Unssuccessful Addition";
    }
//Update Child

    public String UpdateChild(String id) {

        return "Unssuccessful Updatge";
    }
//Delete Child

    public String DeleteChild(String id) {
try{
    String sql = "DELETE FROM vichild where VIChildID = '"+id+"'";
     PreparedStatement pst=conn.prepareStatement(sql);
  pst.executeUpdate();
        
}
     catch (SQLException sqle) {
                return "Unsuccessful Delete";
            }//catch

        return "successful Delete";
    }
//View Result

    public ArrayList<VIChild> ViewResult() {

        return null;
    }
//view children

    public ArrayList<VIChild> ViewChild() {

        return null;
    }
//Child Login

    public int ChildLogin(String id) {
        try {
            return 11;//return The Last stage where the child arraived
        } catch (Exception e) {

        }
        return -1;
    }
//Teacher Login

    public String TeacherLogin(String name, String password) {

        return "Unssuccessful Login";
    }
//GetCurrentStage

    public String getCurrentStage(String id) {

        return "11";//in case child is new
    }

//Start Training1
    public static void StartTraining1() {
        Shape[] shapes = {new Shape("1", "squar", 1), new Shape("2", "triangle", 1), new Shape("3", "circle", 1), new Shape("4", "rectangle", 1)};

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
//Start Testing1

    public void StartTesting1() {
        //Play an audio to describe the purpose of the testing and give instructions for VI child
        int QuestionsNum = 0, TestScore = 0;
        while (QuestionsNum != 4) {
            QuestionsNum++;
            //Play question[QuestionsNum]
            boolean RightBarcode = false;
            while (!RightBarcode) {
                //READ ShapeBarcode
                if (true/*Barcode is recognized*/) {
                    RightBarcode = true;
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

    }//END StartTesting1

//Start Training3 [shatha]
    public void StartTraining3() {
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
    
    //StartTesting3
    public void StartTesting3(){
        //Play an audio to describe the purpose of the testing 
                int QuestionsNum = 0, TestScore = 0, NumOfWrongAnswers;
                boolean RightBarcode1,RightBarcode2;
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

    }//END StartTesting3

    public static void PlaySound(String sound) {
        try {
            Player player;
            File file = new File(sound);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            
            //To Know the duration
            //int duration=0;
            //AudioFile audioFile = AudioFileIO.read(file);
            //duration= audioFile.getAudioHeader().getTrackLength();
             
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

    public static void main(String[] args) {
        StartTraining1();
    }
}//END Database
