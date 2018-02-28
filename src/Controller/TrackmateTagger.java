package Controller;

import processing.core.*; 
import processing.xml.*; 

import processing.pdf.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class TrackmateTagger extends PApplet {

/*
 TrackmateTaggger is a simple processing application that creates unique 
 tags for the Trackmate project.  
 
 See http://trackmate.sourceforge.net for more information.
 Copyright (c) 2008 Adam Kumpf <kumpf@media.mit.edu>, MIT Media Lab
 	
 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */



boolean updateDraw = false;
boolean isFirstTime = true;

PImage multiImg;
int X_MARKERS = 1;
int Y_MARKERS = 1;

int xM = 0;
int yM = 0;

boolean showInfo = false;
MisterButton mrButtonCreate;
MisterButton mrButtonNumTags;
MisterButton mrButtonFormats;

String[] FORMATS = {
  "PDF  (Best)", "PNG Image"};
int formatIndex = 0;

PFont littleFont;
PFont normalFont;
PFont cardFontA;
PFont cardFontB;

PGraphics outputGraphics = new PGraphics();
float     outputDPI      = 72;
float     outputScale    = 1.0f;
String    filenameToSave = "";

public void setup(){
  size(800, 600);
  frameRate(15); 
  littleFont = loadFont("AlBayan-8.vlw");
  normalFont = loadFont("LucidaGrande-12.vlw");
  cardFontA  = loadFont("HelveticaNeue-Bold-40.vlw");
  cardFontB  = loadFont("HelveticaNeue-20.vlw");
  textFont(normalFont);
  multiImg = new PImage(600*X_MARKERS, 600*Y_MARKERS);
  ellipseMode(CENTER);

  mrButtonNumTags = new MisterButton("1", 680, 30, 95, 25, color(16, 16, 128));
  mrButtonFormats = new MisterButton(FORMATS[formatIndex], 680, 70, 95, 25, color(16, 16, 128));
  mrButtonCreate  = new MisterButton("Create Tag", 625, 110, 150, 25, color(16, 128, 16));
}

int   TRANS_XY = 15;
float SCALE_XY = 2.85f;

public void draw(){
  if(isFirstTime){
    background(255);
    noStroke();
    fill(255);
    textFont(normalFont);
    fill(32, 32, 32);
    text("Tags:", 625, 50);
    text("Format:", 625, 90);
    isFirstTime = false;
  }

  textFont(normalFont);
  mrButtonNumTags.drawMisterButton();
  mrButtonCreate.drawMisterButton();
  mrButtonFormats.drawMisterButton();

  if(updateDraw){
    if(xM<X_MARKERS){
      if(yM<Y_MARKERS){
        //for(int xM=0; xM<X_MARKERS; xM++){
        //for(int yM=0; yM<Y_MARKERS; yM++){
        background(255);
        mrButtonNumTags.drawMisterButton();
        mrButtonCreate.drawMisterButton();
        mrButtonFormats.drawMisterButton();
        noStroke();
        fill(255);
        textFont(normalFont);
        fill(32, 32, 32);
        text("Tags:", 625, 50);
        text("Quality:", 625, 90);
        textFont(littleFont);

        int data[] = new int[7];
        data[0] = 0x00FF & (int)random(256);
        data[1] = 0x00FF & (int)random(256);
        data[2] = 0x00FF & (int)random(256);
        data[3] = 0x00FF & (int)random(256);
        data[4] = 0x00FF & (int)random(256);
        data[5] = 0x00FF & (int)random(256);
        int sum = data[0]+data[1]+data[2]+data[3]+data[4]+data[5];
        data[6] = (0x0000FF & ~sum); // inverted checksum (inversion keeps allZero error case from happening).
        pushMatrix();
        {
          // Create the tag and draw it to the screen...
          drawTagToPGraphics(g, TRANS_XY, TRANS_XY, SCALE_XY, data, showInfo);
          // create the tag again, drawing it to the output PGraphic (PDF, PNG, etc.)
          drawTagToPGraphics(outputGraphics, outputDPI*xM+TRANS_XY*outputScale, outputDPI*yM+TRANS_XY*outputScale, SCALE_XY*outputScale, data, showInfo);
        }
        popMatrix();

        if(X_MARKERS == 1 && Y_MARKERS == 1){
          // just creating a single tag,
          // show detailed info about the tag's data...
          strokeWeight(1.0f);
          stroke(0);
          noFill();
          textFont(normalFont);
          text("tag data:", 625, 200);
          stroke(128);
          line(625, 200, 775, 200);
          fill(0);
          for(int i=0; i<7; i++){
            String dname = "data["+i+"]: ";
            if(i == 6){
              dname = "chksum: ";
            }
            float w = textWidth(dname);
            text(dname, 720-w, 220+20*i);
            text(data[i], 725, 220+20*i);
          }
          // index indicator
          text(""+(yM+xM*Y_MARKERS+1)+"/"+(X_MARKERS*Y_MARKERS), 625, 580);
        }

        // now uptdate the image that displays on the screen 
        // (showing progress when there are many tags).
        loadPixels();
        for(int x=0; x<600; x++){
          for(int y=0; y<600; y++){
            //outputImg.pixels[x+600*y] = pixels[x+width*y];
            multiImg.pixels[(x+xM*600)+(600*X_MARKERS)*(y+600*yM)] = pixels[x+width*y];
          }
        }
        // --------- now end for loops of xM and yM
        yM++;
      }
      else{
        yM = 0;
        xM++;
      }
      copy(multiImg, 0, 0, 600*X_MARKERS, 600*X_MARKERS, 0, 0, 600, 600);
    }
    else{
      copy(multiImg, 0, 0, 600*X_MARKERS, 600*X_MARKERS, 0, 0, 600, 600);
      xM = 0;
      noStroke();
      fill(0);
      textFont(normalFont);
      text("saving...", 650, 380);
      repaint();

      // draw a footer on the PGraphics
      drawFooterInfoToPGraphics(outputGraphics, TRANS_XY*outputScale, outputDPI*Y_MARKERS+TRANS_XY*outputScale, SCALE_XY*outputScale, showInfo);

      // finalize and save the PGraphics to a file.
      if(formatIndex == 0){
        // FORMAT is PDF
        // automatically saved to file when ended.
        outputGraphics.dispose();
        outputGraphics.endDraw();
      }
      if(formatIndex == 1){
        // FORMAT is PNG
        outputGraphics.endDraw();
        outputGraphics.save(filenameToSave); 
        outputGraphics.dispose();
      }

      isFirstTime = false;
      updateDraw = false;
    }

  }
  else{
    // don't need to updateDraw
    noStroke();
    fill(255); // cover up last "saving..." text
    rect(650, 365, 75, 40);
  }

}

public void mouseClicked(){
  if(mrButtonCreate.containsPoint(mouseX, mouseY)){
    println("getting filename via dialog...");
    FileDialog fdSave = new FileDialog(frame, "Save Tags As...", FileDialog.SAVE);
    String defaultFilename = "TrackmateTags_"+(X_MARKERS*Y_MARKERS)+".pdf";
    if(formatIndex == 1){
      defaultFilename = "TrackmateTags_"+(X_MARKERS*Y_MARKERS)+"_300DPI.png";
    }
    fdSave.setFile(defaultFilename);
    fdSave.show();
    if(fdSave.getFile() != null){
      String fname = fdSave.getFile(); 
      String dirname = fdSave.getDirectory();
      int lastIndex = fname.lastIndexOf(".");
      if(lastIndex > 0){
        fname = fname.substring(0, lastIndex);
      }
      String fullPathname = "not specified...";
      if(formatIndex == 0){
        // FORMAT is PDF
        fullPathname   = dirname+fname+".pdf";
        outputDPI      = 72.0f;
        outputScale    = outputDPI/600.0f;
        // add extra space at bottom to show a note (DPI info, etc.)
        outputGraphics = createGraphics((int)(outputDPI*max(2, X_MARKERS)), (int)(outputDPI*(Y_MARKERS+1)), PDF, fullPathname);
        outputGraphics.beginDraw();
      }
      if(formatIndex == 1){
        // FORMAT is PNG Image
        fullPathname   = dirname+fname+".png";
        outputDPI      = 300.0f;
        outputScale    = outputDPI/600.0f;
        // add extra space at bottom to show a note (DPI info, etc.)
        outputGraphics = createGraphics((int)(outputDPI*max(2, X_MARKERS)), (int)(outputDPI*(Y_MARKERS+1)), JAVA2D, fullPathname);
        outputGraphics.beginDraw();
        outputGraphics.background(255, 255, 255);
      }

      println("Creating and saving to filename = " + fullPathname);
      filenameToSave = fullPathname;
      updateDraw = true;
    }
    else{
      println("Save aborted...");
    }



  }
  if(mrButtonNumTags.containsPoint(mouseX, mouseY)){
    int newSize = X_MARKERS + 1;
    if(newSize > 7){
      newSize = 1;
    }
    if(newSize == 1){
      mrButtonCreate.text = "Create Tag";
    }
    else{
      mrButtonCreate.text = "Create Tags";
    }
    X_MARKERS = newSize;
    Y_MARKERS = newSize;
    multiImg = new PImage(600*X_MARKERS, 600*Y_MARKERS);
    xM = 0;
    yM = 0;
    mrButtonNumTags.text = ""+(newSize*newSize);
  }
  if(mrButtonFormats.containsPoint(mouseX, mouseY)){
    formatIndex = (formatIndex+1) % FORMATS.length;
    mrButtonFormats.text = FORMATS[formatIndex];
  }
}

public void mouseMoved(){
  if(mrButtonCreate.containsPoint(mouseX, mouseY) || 
    mrButtonNumTags.containsPoint(mouseX, mouseY) ||
    mrButtonFormats.containsPoint(mouseX, mouseY)){
    cursor(HAND);
  }
  else{
    cursor(ARROW);
  }
}

public void keyPressed(){
  if(key == 'i' || key == 'I'){
    showInfo = !showInfo;
    if(showInfo){
      int newSize = 1;
      mrButtonCreate.text = "Create Tag";
      X_MARKERS = newSize;
      Y_MARKERS = newSize;
      multiImg = new PImage(600*X_MARKERS, 600*Y_MARKERS);
      xM = 0;
      yM = 0;
      mrButtonNumTags.text = ""+(newSize*newSize);
    }
  }
}


//#################################################
//              UI Elements CODE
//#################################################
class MisterButton{
  String text = "";
  int x;
  int y; 
  int w;
  int h;
  boolean enabled = true;
  int borderColor = color(128, 128, 128);
  public MisterButton(String text, int x, int y, int w, int h){
    this.text = text;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;    
  } 
  public MisterButton(String text, int x, int y, int w, int h, int borderColor){
    this.text = text;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;    
    this.borderColor = borderColor;
  } 
  public void drawMisterButton(){
    int d = 5;
    noStroke();
    fill(16);
    rect(x, y+d, w, h-2*d+1);
    rect(x+d, y, w-2*d+1, h);
    strokeWeight(1.0f);
    if(enabled){
      stroke(borderColor);
    }
    else{
      stroke(64);
    }
    smooth();
    arc(x+d, y+d, 2*d, 2*d, PI, 3*PI/2);
    arc(x+w-d, y+d, 2*d, 2*d, 3*PI/2, 2*PI);
    arc(x+d, y+h-d, 2*d, 2*d, PI/2, PI);
    arc(x+w-d, y+h-d, 2*d, 2*d, 0, PI/2);
    line(x+d, y, x+w-d, y);
    line(x+d, y+h, x+w-d, y+h);
    line(x, y+d, x, y+h-d);
    line(x+w, y+d, x+w, y+h-d);    
    noSmooth();
    if(enabled){
      fill(255);
    }
    else{
      fill(128);
    }
    int tw = (int)textWidth(text);
    text(text, x+(w-tw)/2, y+h-5);
  }
  public boolean containsPoint(int x, int y){
    if(x > this.x && x < (this.x+this.w) && y > this.y && y < (this.y+h)){
      return true;
    }
    else{
      return false;
    }
  }
}

// ############################################
// TAG CREATION CODE
// ############################################

public void drawTagToPGraphics(PGraphics pg, float x_offset, float y_offset, float xy_scale, int data[], boolean showExtraInfo){
  if(data.length != 7){
    println("Invalid data length.. aborting..");
    return;
  }

  pg.hint(ENABLE_NATIVE_FONTS);
  pg.textFont(littleFont);

  pg.pushMatrix();
  {
    pg.translate(x_offset, y_offset);
    pg.scale(xy_scale); // 3.0 = 1" at 600dpi, 2.85 = 0.95" at 600dpi

    // draw thin outer ring to show required area for tag.
    pg.strokeWeight(1.0f);
    pg.noFill();
    pg.stroke(220);
    pg.ellipse(100, 100, 200, 200);
    pg.stroke(128, 128, 255);
    pg.strokeWeight(2.0f);
    pg.rect(-5, -5, 210, 210);
    pg.strokeWeight(1.0f);

    // draw the common structure for every tag 
    // (alternating black and white circles and marks for orientation)
    pg.fill(255);
    pg.noStroke();
    pg.smooth();
    pg.ellipse(100, 100, 200, 200); 
    pg.fill(0);
    pg.arc(100, 100, 208, 208, (-0.5f)/40.0f*TWO_PI, (0.5f)/40.0f*TWO_PI);
    pg.arc(100, 100, 200, 200, (5-0.25f)/40.0f*TWO_PI, (5+0.25f)/40.0f*TWO_PI);
    pg.arc(100, 100, 208, 208, (10-0.5f)/40.0f*TWO_PI, (10+0.5f)/40.0f*TWO_PI);
    pg.ellipse(100, 100, 150, 150);
    pg.fill(255);
    pg.ellipse(100, 100, 80, 80);
    pg.fill(0);
    pg.ellipse(100, 100, 50, 50); 
    pg.fill(255);
    pg.ellipse(100, 100, 5, 5);


    // print on the info (dark), for users own reference.
    for(int i=0; i<7; i++){
      pg.fill(16);
      pg.pushMatrix();
      pg.translate(100, 100);
      pg.rotate((i)/7.0f*TWO_PI+PI/2);
      pg.text(data[i], -5, -58);
      pg.popMatrix();
      pg.noFill();
    }

    // analog data corner spots
    for(int i=0; i<4; i++){
      pg.stroke(64);
      pg.strokeWeight(1);
      pg.pushMatrix();
      pg.translate(100, 100);
      pg.rotate((i)/4.0f*TWO_PI+PI/4);
      pg.ellipse(120, 0, 30, 30);
      pg.popMatrix();
      pg.noFill();
    }

    // draw in the data !!!
    pg.noFill();
    pg.strokeCap(SQUARE);
    pg.strokeWeight(14.0f);
    boolean lastWasOne = false;
    boolean isOne = false;
    for(int i=0; i<32; i++){
      int bit = i % 8;
      int byt = i / 8;
      isOne = ((data[byt] & (0x01<<bit)) != 0);
      if(isOne){
        pg.stroke(255);
      }
      else{
        pg.stroke(0);
      }
      pg.arc(100, 100, 155, 155, (i-0.5f)/32.0f*TWO_PI, (i+0.5f)/32.0f*TWO_PI);
      if(isOne && lastWasOne){
        pg.arc(100, 100, 155, 155, (i-1.0f)/32.0f*TWO_PI, (i)/32.0f*TWO_PI);
      }
      if(!isOne && !lastWasOne){
        pg.arc(100, 100, 155, 155, (i-1.0f)/32.0f*TWO_PI, (i)/32.0f*TWO_PI);
      }
      lastWasOne = isOne;
    }

    for(int i=0; i<24; i++){
      int bit = i % 8;
      int byt = i / 8 + 4;
      isOne = ((data[byt] & (0x01<<bit)) != 0);
      if(isOne){
        pg.stroke(255);
      }
      else{
        pg.stroke(0);
      }
      pg.arc(100, 100, 90, 90, (i-0.5f)/24.0f*TWO_PI, (i+0.5f)/24.0f*TWO_PI);
      if(isOne && lastWasOne){
        pg.arc(100, 100, 90, 90, (i-1.0f)/24.0f*TWO_PI, (i)/24.0f*TWO_PI);
      }
      if(!isOne && !lastWasOne){
        pg.arc(100, 100, 90, 90, (i-1.0f)/24.0f*TWO_PI, (i)/24.0f*TWO_PI);
      }
      lastWasOne = isOne;
    }

    // show additional overlays for explanation if desired...
    if(showExtraInfo){
      pg.strokeWeight(16);
      pg.noFill();
      pg.stroke(0, 0, 255, 128);
      pg.ellipse(100, 100, 184, 184); // outside ring orientation marks
      pg.strokeWeight(12.5f);
      pg.stroke(255, 0, 0, 128);
      pg.ellipse(100, 100, 155, 155); // outter data (5 bytes)
      pg.stroke(0, 255, 0, 128);
      pg.ellipse(100, 100, 90, 90); // inner data (3 bytes + 1 byte checksum)
      pg.fill(0);
      for(int i=0; i<32; i++){
        int bit = i % 8;
        int byt = i / 8;
        pg.pushMatrix();
        pg.translate(100, 100);
        pg.rotate((i)/32.0f*TWO_PI);
        pg.text(i, 73, 3);
        pg.popMatrix();
      }
      for(int i=0; i<24; i++){
        int bit = i % 8;
        int byt = i / 8;
        pg.pushMatrix();
        pg.translate(100, 100);
        pg.rotate((i)/24.0f*TWO_PI);
        pg.text((i+32), 39, 3);
        pg.popMatrix();
      }
      // analog data corner spots
      pg.strokeWeight(1);
      pg.fill(128, 128, 0);
      for(int i=0; i<4; i++){
        pg.stroke(128);
        pg.fill(92, 255, 255);
        pg.pushMatrix();
        pg.translate(100, 100);
        pg.rotate((i)/4.0f*TWO_PI+PI/4);
        pg.ellipse(120, 0, 30, 30);
        pg.fill(0);
        pg.text("a."+i, 113, 3);
        pg.popMatrix();
      }
      pg.fill(0);
      pg.pushMatrix();
      pg.translate(100, 100);
      pg.rotate((0)/40.0f*TWO_PI);
      pg.text("A", 90, 3);
      pg.popMatrix();
      pg.pushMatrix();
      pg.translate(100, 100);
      pg.rotate((5)/40.0f*TWO_PI);
      pg.text("B", 90, 3);
      pg.popMatrix();
      pg.pushMatrix();
      pg.translate(100, 100);
      pg.rotate((10)/40.0f*TWO_PI);
      pg.text("C", 90, 3);
      pg.popMatrix();
    }
  }
  pg.popMatrix();
}

public void drawFooterInfoToPGraphics(PGraphics pg, float x_offset, float y_offset, float xy_scale, boolean showExtraInfo){
  pg.hint(ENABLE_NATIVE_FONTS);
  pg.textFont(littleFont);
  pg.pushMatrix();
  {
    pg.translate(x_offset, y_offset);
    pg.scale(xy_scale); // 3.0 = 1" at 600dpi, 2.85 = 0.95" at 600dpi
    pg.stroke(0);
    pg.textFont(cardFontA);
    pg.fill(32);
    pg.text("Tags for Trackmate", 20, 90);
    pg.textFont(cardFontB);
    pg.fill(96);
    pg.text("an easy-to-build tangible user interface.", 40, 115);  
    pg.fill(40, 20, 214);
    pg.text("http://trackmate.sourceforge.net", 40, 140);
    pg.fill(255, 0, 0);
    pg.text("Each tag should be 1\"x1\" when printed.", 20, 180);
  }
  pg.popMatrix();
}



















  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "TrackmateTagger" });
  }
}
