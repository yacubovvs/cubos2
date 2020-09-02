#ifdef controls_m5stack
  #include <M5Stack.h>
#endif

void initControls(){  
}

bool buttons[] = {false, false, false, false};

void checkAndSendControls(){  
  //beep();
  #ifdef controls_m5stack
    if(M5.BtnA.read()){
      if(buttons[0]==false){
        beep();
        buttons[0]=true;
      }
    }else buttons[0]=false;
    
    if(M5.BtnB.read()){
      if(buttons[1]==false){
        beep();
        buttons[1]=true;
      }
    }else buttons[1]=false;
    
    if(M5.BtnC.read()){
      if( buttons[2]==false){
        beep();
        buttons[2]=true;
      }
    }else buttons[2]=false;
    
  #endif
}

#ifdef controls_m5stack
  void beep(){
    M5.Speaker.beep();
    delay(50);
    M5.Speaker.mute();
  }
#endif
