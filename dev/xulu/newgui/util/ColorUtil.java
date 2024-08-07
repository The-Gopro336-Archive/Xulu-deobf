package dev.xulu.newgui.util;

import com.elementars.eclient.module.render.NewGui;
import java.awt.Color;

public class ColorUtil {
   public static Color getClickGUIColor() {
      return new Color((Integer)NewGui.red.getValue(), (Integer)NewGui.green.getValue(), (Integer)NewGui.blue.getValue());
   }
}
