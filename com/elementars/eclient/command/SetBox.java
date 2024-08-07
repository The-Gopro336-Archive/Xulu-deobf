package com.elementars.eclient.command;

import dev.xulu.settings.Value;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetBox extends JFrame {
   // $FF: synthetic field
   JButton jb = new JButton("Set");
   // $FF: synthetic field
   JPanel jp = new JPanel();
   // $FF: synthetic field
   JTextField jt = new JTextField(30);
   // $FF: synthetic field
   Value setting;
   // $FF: synthetic field
   JLabel jl = new JLabel();

   public SetBox(final Value var1) {
      this.setting = var1;
      this.setTitle("Setting");
      this.setVisible(true);
      this.setSize(400, 200);
      this.setDefaultCloseOperation(1);
      this.jp.add(this.jt);
      this.jt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = SetBox.this.jt.getText();
            SetBox.this.jl.setText(var2);
         }
      });
      this.jp.add(this.jb);
      this.jb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1x) {
            String var2 = SetBox.this.jt.getText();
            var1.setValue(var2);
         }
      });
      this.jp.add(this.jl);
      this.add(this.jp);
   }

   public static void initTextBox(Value var0) {
      new SetBox(var0);
   }
}
