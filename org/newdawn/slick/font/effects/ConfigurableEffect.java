package org.newdawn.slick.font.effects;

import java.util.List;

public interface ConfigurableEffect extends Effect {
   void setValues(List var1);

   List getValues();

   public interface Value {
      String getString();

      String getName();

      void showDialog();

      Object getObject();

      void setString(String var1);
   }
}
