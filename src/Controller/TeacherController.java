/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Database;

/**
 *
 * @author user009
 */
public class TeacherController {
    
    private String DeleteChild(String id){
       Database db= new Database();
        db.openConnection(); 
       return db.DeleteChild(id);
    }//end Delete
    
    private String AddChild(){
        
        return "";
    }
    
    private String UpdateChild(){
        
        return "";
    }
}
