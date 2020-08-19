#ifdef megaUnoShield
  #ifdef megaUnoShield_2_8_inc
    #define SCREEN_WIDTH 240
    #define SCREEN_HEIGHT 320
  #else
    #define SCREEN_WIDTH 320
    #define SCREEN_HEIGHT 480
  #endif
  
  //#include "libs_h/MCUFRIEND_kbv/UTFTGLUE.h"              
  #include <UTFTGLUE.h>              
  //#include <MCUFRIEND_kbv.h>              
  //#include "libs_h/MCUFRIEND_kbv/UTFTGLUE.h"              
  UTFTGLUE myGLCD(0,A2,A1,A3,A4,A0);
  
  byte red = 0, green = 0, blue = 0;
  
  void setup_displayDriver(){
    myGLCD.InitLCD();
    //myGLCD.setFont(SmallFont);
    //myGLCD.wrap(false)
    myGLCD.setRotation(PORTRAIT);
  
    //myGLCD.clrScr();
    setDrawColor(0, 0, 0);
    myGLCD.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);  

    
    //drawString("Test ", 10,10,255,255,255,1);
  }

  void setDrawColor(byte red_set, byte green_set, byte blue_set){
    red = red_set;
    green = green_set; 
    blue = blue_set;
    
    myGLCD.setColor(red, green, blue);
  }
  
  void drawPixel(int x, int y, byte red_set, byte green_set, byte blue_set){

    /*
    if(
      red_set != red ||
      green_set != green ||
      blue_set != blue
      ) setDrawColor(red, green, blue);*/

    setDrawColor(red_set, green_set, blue_set);
    //setDrawColor(red, green, blue);
    myGLCD.drawPixel(x,y);
  }
  
#endif
