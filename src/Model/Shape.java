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
public class Shape {
 private String SID;
 private String name;
 private int size;

    public String getID() {
        return SID;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Shape() {
    }

    public Shape(String SID, String name, int size) {
        this.SID = SID;
        this.name = name;
        this.size = size;
    }

 
 
}
