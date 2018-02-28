/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import sun.applet.Main;
//import javax.media.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author user009
 */
public class Database {
 public static Scanner read=new Scanner(System.in);
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private final String driver = "com.mysql.jdbc.Driver";
    private Connection conn;
    private Statement state;
    private boolean connected = false;

public Connection openConnection()
{
    if (conn == null) {
            try {

                Class.forName(driver);
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tactrace", USERNAME, PASSWORD);
                connected = true;
                //System.out.println("SUCESS");
            } catch (ClassNotFoundException | SQLException sqle) {
                connected = false;
                //System.out.println("ERROR");
            }
        }//if
        return conn;
}
//Add Child
public String AddChild(String name, String phone)
{     
//        if(connected)
//        {
//            try
//            {
//                statement= (Statement) conn.createStatement();
//                String sql= "Insert Into reader (name,phone) Values('"+name+"','"+phone+"')";
//                int rowCount = statement.executeUpdate(sql);
//                return ""+rowCount;
//            }
//            catch(SQLException sqle)
//            {
//                return "Unssuccessful Addition";
//           }
//       }
return "Unssuccessful Addition";
}
//Update Child
public String UpdateChild(String id)
{
    
    return "Unssuccessful Updatge";
}
//Delete Child
public String DeleteChild(String id)
{
    
   return "Unssuccessful Delete"; 
}
//View Result
public ArrayList<VIChild> ViewResult()
{
    
    return null;
}
//view children
public ArrayList<VIChild> ViewChild()
{
    
    return null;
}
//Child Login
public boolean ChildLogin(String barcode)
{
        boolean checklogin = false;
        if (connected) {
            try {
                String sql = "SELECT AccountBarCode FROM vichild";
                state = conn.createStatement();
                ResultSet set = state.executeQuery(sql);
                while (set.next()) {
                    String childBarcode = set.getString("AccountBarCode");
                    if(barcode.equals(childBarcode)) {
                        checklogin = true;
                    }
                }//while
            }//try
            catch (SQLException sqle) {
                checklogin = false;
                System.out.println(sqle);
            }//catch 
        }
        return checklogin;
}
//Teacher Login
public String TeacherLogin(String name, String password)
{
    
    return "Unssuccessful Login";
}
//GetCurrentStage
public String getCurrentStage(String barcode)
{  String LevelStage="No stage"; 
    if(connected)
        {
            try
            {
              String sql = "SELECT Level, LevelStage FROM vichild WHERE AccountBarCode="+barcode;
                state = conn.createStatement();
                ResultSet set = state.executeQuery(sql);
                while (set.next()) {
                    String Level = set.getInt("Level")+"";
                    String Stage = set.getInt("LevelStage")+"";
                    LevelStage=Level+Stage;
                }//while
            }//try
            catch (SQLException sqle) {
                System.out.println(sqle);
            }//catch 
}
    return LevelStage;
}

//Start Training1
public void StartTraining12()
    {
        Shape[] shapes = {new Shape("1","squar",1),new Shape("2","triangle",1),new Shape("3","circle",1),new Shape("4","rectangle",1)};

       
//Play an audio to describe the purpose of the training and give instructions for VI child2
    String barcode=null;
    int QuestionsNum =0;
    //("training1Q"+QuestionsNum);
    int NumOfWrongAnswers =0;
    boolean RightBarcode= false, ContinueTraining= false;
    //WHILE (QuestionsNum not equals 4)
        while (QuestionsNum != 4) { // asking the child 4 Q           
            QuestionsNum++;
            PlaySound("training1Q"+QuestionsNum+".mp3");          
	//Play question[QuestionsNum]
            while (!RightBarcode) {  
                System.out.println("Enter the barcode:");
                barcode= read.next();
                //READ ShapeBarcode
		if(!barcode.equals(null)){
                   RightBarcode= true;
		   while(NumOfWrongAnswers != 3){
                      if (barcode.equals(shapes[QuestionsNum-1].getID())){
                        PlaySound("Pos1.mp3");                         
                        break;
                       }//END IF
                       else 
                        { 
                          if(NumOfWrongAnswers == 2)
                          {
                            //Play a negative audio message
                            //Go back to lesson
                            break;
                        } //END IF
                          NumOfWrongAnswers++;
                          System.out.println("error");
                          barcode= read.next();
                    //Play a hint
                         }//END ELSE
                     }//while3
                 }//END IF
               else
                {
              System.out.println("error barcode");

            //Play an error message
            }//END ELSE
            }//while2    
        }//while1	
	QuestionsNum= 0; 
        //Play an audio message asking the child if he wants more training questions 
        //ContinueTraining to child answer
	while (ContinueTraining){ 
            QuestionsNum++;
            //Play question[QuestionsNum]
            NumOfWrongAnswers = 0;
            RightBarcode = false;
            while (!RightBarcode){
                //READ ShapeBarcode
		if (true/*Barcode is recognized*/){
                    RightBarcode = true;
                    while (NumOfWrongAnswers != 3){
                        if (true/*VI child puts the right shape*/)
                        {
			//Play a positive audio feedback
                        break;
                        }//END IF
                        else{
                            if (NumOfWrongAnswers == 2){
				//Play a negative audio message
				//Go back to lesson
				break;
                            }//END IF
                        NumOfWrongAnswers++;
			//Play a hint
                        }//END ELSE
                    }//END WHILE
                }//END IF
                else
                {
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
public static void PlaySound(String sound){
    
            try{
                /*
                  // URL url = this.getClass().getClassLoader().getResource(filename);
         String  uri=new File(sound).toURI().toString();
           File file = new File(uri);
                  MediaLocator locator = new MediaLocator(uri);
         player = Manager.createPlayer(locator);
         player.addControllerListener(new ControllerListener() {
            public void controllerUpdate(ControllerEvent event) {
               if (event instanceof EndOfMediaEvent) {
                  player.stop();
                  player.close();
               }
            }
         });
         player.realize();
         player.start();
         ********************************************   
         JFXPanel j =new JFXPanel();
          
           String  uri=new File(sound).toURI().toString();
           File file = new File(uri);
          
           MediaPlayer player = new MediaPlayer(new Media(uri));
           
           //long endtime = (long) (player.getAudioSpectrumInterval()*1000);
           //System.out.println("end time= "+endtime);
           
            //while(!player.getCurrentTime().equals(endtime)){
            while(player.getCurrentTime().lessThanOrEqualTo(player.getTotalDuration())){
            player.play();
            break;
            }*/
            }
            catch(Exception ex){
              JOptionPane.showMessageDialog(null, ex);
            } 
}
public void StartTesting13()
{
    //Play an audio to describe the purpose of the testing and give instructions for VI child
    int QuestionsNum= 0; 
    int TestScore= 0;
    while (QuestionsNum != 4){
	QuestionsNum++;
	//Play question[QuestionsNum]
	boolean RightBarcode= false;
	while (!RightBarcode)
        {
	//READ ShapeBarcode
            if (true/*Barcode is recognized*/)
            {
		RightBarcode= true;
            if (true/*VI child puts the right shape*/)
            {
                //Play a positive audio feedback congratulating the child
                TestScore++;
            }//END IF
            else
            {
                //Play a negative audio message informing the child that he made a mistake
            }//END ELSE
            }//END IF
            else
            {
                //Play an audio error message
            }//END ELSE
        }//END WHILE
    }//END WHILE
    if (true/*TestScore < 80%*/)
    {
        //Play a negative audio message …
	//Go back to the lesson 
    }//END IF
    else
    {
	//Play a positive audio message …
	//Update the LevelStage for this child to lesson 2
	//Update the level for this child to level 2
	//Move on to the next level
    }//END ELSE 	

}//END OF StartTesting1

}

}
