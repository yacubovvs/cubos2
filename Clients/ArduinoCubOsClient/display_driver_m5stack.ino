#ifdef display_M5Stack

#include <M5Stack.h>

void updatescreen_displayDriver(){
  M5.update();
}

void driver_clearScreen(){
  M5.Lcd.fillScreen(get_m5ColorFromPallette(0, 0, 0));
}

uint32_t get_m5ColorFromPallette(byte r, byte g, byte b){
  return (((( r*31/255) <<11)|( g*31/255) <<6)|( b*31/255) <<0);
  //return (((( r ) <<11)|( g ) <<6)|( b ) <<0);
}

void setup_displayDriver(){
  M5.begin();
  // M5.Lcd.setRotation(3);
  M5.Lcd.fillScreen(TFT_BLACK);
  M5.Lcd.setTextSize(1);
  M5.Lcd.setTextColor(TFT_WHITE);
  M5.Lcd.setCursor(0, 0);
}

void drawPixel(int x, int y, byte red, byte green, byte blue){
  uint32_t color = get_m5ColorFromPallette(red, green, blue);
  M5.Lcd.drawPixel(x, y, color);
}

#endif
