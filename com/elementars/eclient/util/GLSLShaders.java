package com.elementars.eclient.util;

public enum GLSLShaders {
   // $FF: synthetic field
   SUNSETWAVES("/shaders/sunsetwaves.fsh"),
   // $FF: synthetic field
   REDGLOW("/shaders/redglow.fsh"),
   // $FF: synthetic field
   SNAKE("/shaders/snake.fsh"),
   // $FF: synthetic field
   WAIFU("/shaders/waifu.fsh");

   // $FF: synthetic field
   String s;
   // $FF: synthetic field
   JUPITER("/shaders/jupiter.fsh"),
   // $FF: synthetic field
   PINWHEEL("/shaders/pinwheel.fsh"),
   // $FF: synthetic field
   DJ("/shaders/dj.fsh"),
   // $FF: synthetic field
   TRIANGLE("/shaders/triangle.fsh"),
   // $FF: synthetic field
   STORM("/shaders/storm.fsh"),
   // $FF: synthetic field
   SPACE2("/shaders/space2.fsh"),
   // $FF: synthetic field
   BLUEGRID("/shaders/bluegrid.fsh"),
   // $FF: synthetic field
   SKY("/shaders/sky.fsh"),
   // $FF: synthetic field
   BLUENEBULA("/shaders/bluenebula.fsh"),
   // $FF: synthetic field
   PURPLEGRID("/shaders/purplegrid.fsh"),
   // $FF: synthetic field
   CLOUDS("/shaders/clouds.fsh"),
   // $FF: synthetic field
   SPACE("/shaders/space.fsh"),
   // $FF: synthetic field
   XULU("/shaders/xulu.fsh"),
   // $FF: synthetic field
   BLUEVORTEX("/shaders/bluevortex.fsh"),
   // $FF: synthetic field
   MATRIX("/shaders/matrix.fsh"),
   // $FF: synthetic field
   ICYFIRE("/shaders/icyfire.fsh"),
   // $FF: synthetic field
   CITY("/shaders/city.fsh"),
   // $FF: synthetic field
   MATRIXRED("/shaders/matrixred.fsh"),
   // $FF: synthetic field
   AWESOME("/shaders/awesome.fsh"),
   // $FF: synthetic field
   MINECRAFT("/shaders/minecraft.fsh"),
   // $FF: synthetic field
   CAVE("/shaders/cave.fsh"),
   // $FF: synthetic field
   PURPLEMIST("/shaders/purplemist.fsh");

   public String get() {
      return this.s;
   }

   private GLSLShaders(String var3) {
      this.s = var3;
   }
}
