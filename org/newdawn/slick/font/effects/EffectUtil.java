package org.newdawn.slick.font.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

public class EffectUtil {
   // $FF: synthetic field
   private static BufferedImage scratchImage = new BufferedImage(256, 256, 2);

   public static ConfigurableEffect.Value booleanValue(String var0, final boolean var1, final String var2) {
      return new EffectUtil.DefaultValue(var0, String.valueOf(var1)) {
         public Object getObject() {
            return Boolean.valueOf(this.value);
         }

         public void showDialog() {
            JCheckBox var1x = new JCheckBox();
            var1x.setSelected(var1);
            if (this.showValueDialog(var1x, var2)) {
               this.value = String.valueOf(var1x.isSelected());
            }

         }
      };
   }

   public static Color fromString(String var0) {
      return var0 != null && var0.length() == 6 ? new Color(Integer.parseInt(var0.substring(0, 2), 16), Integer.parseInt(var0.substring(2, 4), 16), Integer.parseInt(var0.substring(4, 6), 16)) : Color.white;
   }

   public static ConfigurableEffect.Value floatValue(String var0, final float var1, final float var2, final float var3, final String var4) {
      return new EffectUtil.DefaultValue(var0, String.valueOf(var1)) {
         public Object getObject() {
            return Float.valueOf(this.value);
         }

         public void showDialog() {
            JSpinner var1x = new JSpinner(new SpinnerNumberModel((double)var1, (double)var2, (double)var3, 0.10000000149011612D));
            if (this.showValueDialog(var1x, var4)) {
               this.value = String.valueOf(((Double)var1x.getValue()).floatValue());
            }

         }
      };
   }

   public static String toString(Color var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("color cannot be null.");
      } else {
         String var1 = Integer.toHexString(var0.getRed());
         if (var1.length() == 1) {
            var1 = String.valueOf((new StringBuilder()).append("0").append(var1));
         }

         String var2 = Integer.toHexString(var0.getGreen());
         if (var2.length() == 1) {
            var2 = String.valueOf((new StringBuilder()).append("0").append(var2));
         }

         String var3 = Integer.toHexString(var0.getBlue());
         if (var3.length() == 1) {
            var3 = String.valueOf((new StringBuilder()).append("0").append(var3));
         }

         return String.valueOf((new StringBuilder()).append(var1).append(var2).append(var3));
      }
   }

   public static ConfigurableEffect.Value colorValue(String var0, Color var1) {
      return new EffectUtil.DefaultValue(var0, toString(var1)) {
         public Object getObject() {
            return EffectUtil.fromString(this.value);
         }

         public void showDialog() {
            Color var1 = JColorChooser.showDialog((Component)null, "Choose a color", EffectUtil.fromString(this.value));
            if (var1 != null) {
               this.value = EffectUtil.toString(var1);
            }

         }
      };
   }

   public static ConfigurableEffect.Value optionValue(String var0, final String var1, final String[][] var2, final String var3) {
      return new EffectUtil.DefaultValue(var0, var1.toString()) {
         private String getValue(int var1x) {
            return var2[var1x].length == 1 ? var2[var1x][0] : var2[var1x][1];
         }

         public String toString() {
            for(int var1x = 0; var1x < var2.length; ++var1x) {
               if (this.getValue(var1x).equals(this.value)) {
                  return var2[var1x][0].toString();
               }
            }

            return "";
         }

         public Object getObject() {
            return this.value;
         }

         public void showDialog() {
            int var1x = -1;
            DefaultComboBoxModel var2x = new DefaultComboBoxModel();

            for(int var3x = 0; var3x < var2.length; ++var3x) {
               var2x.addElement(var2[var3x][0]);
               if (this.getValue(var3x).equals(var1)) {
                  var1x = var3x;
               }
            }

            JComboBox var4 = new JComboBox(var2x);
            var4.setSelectedIndex(var1x);
            if (this.showValueDialog(var4, var3)) {
               this.value = this.getValue(var4.getSelectedIndex());
            }

         }
      };
   }

   public static ConfigurableEffect.Value intValue(String var0, final int var1, final String var2) {
      return new EffectUtil.DefaultValue(var0, String.valueOf(var1)) {
         public void showDialog() {
            JSpinner var1x = new JSpinner(new SpinnerNumberModel(var1, -32768, 32767, 1));
            if (this.showValueDialog(var1x, var2)) {
               this.value = String.valueOf(var1x.getValue());
            }

         }

         public Object getObject() {
            return Integer.valueOf(this.value);
         }
      };
   }

   public static BufferedImage getScratchImage() {
      Graphics2D var0 = (Graphics2D)scratchImage.getGraphics();
      var0.setComposite(AlphaComposite.Clear);
      var0.fillRect(0, 0, 256, 256);
      var0.setComposite(AlphaComposite.SrcOver);
      var0.setColor(Color.white);
      return scratchImage;
   }

   private abstract static class DefaultValue implements ConfigurableEffect.Value {
      // $FF: synthetic field
      String value;
      // $FF: synthetic field
      String name;

      public String getString() {
         return this.value;
      }

      public String getName() {
         return this.name;
      }

      public String toString() {
         return this.value == null ? "" : this.value.toString();
      }

      public DefaultValue(String var1, String var2) {
         this.value = var2;
         this.name = var1;
      }

      public void setString(String var1) {
         this.value = var1;
      }

      public boolean showValueDialog(final JComponent var1, String var2) {
         EffectUtil.ValueDialog var3 = new EffectUtil.ValueDialog(var1, this.name, var2);
         var3.setTitle(this.name);
         var3.setLocationRelativeTo((Component)null);
         EventQueue.invokeLater(new Runnable() {
            public void run() {
               Object var1x = var1;
               if (var1x instanceof JSpinner) {
                  var1x = ((DefaultEditor)((JSpinner)var1).getEditor()).getTextField();
               }

               ((JComponent)var1x).requestFocusInWindow();
            }
         });
         var3.setVisible(true);
         return var3.okPressed;
      }
   }

   private static class ValueDialog extends JDialog {
      // $FF: synthetic field
      public boolean okPressed = false;

      public ValueDialog(JComponent var1, String var2, String var3) {
         this.setDefaultCloseOperation(2);
         this.setLayout(new GridBagLayout());
         this.setModal(true);
         if (var1 instanceof JSpinner) {
            ((DefaultEditor)((JSpinner)var1).getEditor()).getTextField().setColumns(4);
         }

         JPanel var4 = new JPanel();
         var4.setLayout(new GridBagLayout());
         this.getContentPane().add(var4, new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
         var4.setBackground(Color.white);
         var4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
         JTextArea var5 = new JTextArea(var3);
         var4.add(var5, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
         var5.setWrapStyleWord(true);
         var5.setLineWrap(true);
         var5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
         var5.setEditable(false);
         JPanel var8 = new JPanel();
         this.getContentPane().add(var8, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 0, new Insets(5, 5, 0, 5), 0, 0));
         var8.add(new JLabel(String.valueOf((new StringBuilder()).append(var2).append(":"))));
         var8.add(var1);
         JPanel var6 = new JPanel();
         this.getContentPane().add(var6, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
         JButton var7 = new JButton("OK");
         var6.add(var7);
         var7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               ValueDialog.this.okPressed = true;
               ValueDialog.this.setVisible(false);
            }
         });
         var7 = new JButton("Cancel");
         var6.add(var7);
         var7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
               ValueDialog.this.setVisible(false);
            }
         });
         this.setSize(new Dimension(320, 175));
      }
   }
}
