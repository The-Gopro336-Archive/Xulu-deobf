package com.elementars.eclient.guirewrite.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WelcomeNotes extends JFrame {
   // $FF: synthetic field
   JLabel jl = new JLabel();
   // $FF: synthetic field
   JButton jb = new JButton("Set Welcome Message (use NAME to mark the name)");
   // $FF: synthetic field
   JPanel jp = new JPanel();
   // $FF: synthetic field
   JTextField jt = new JTextField(30);

   public static void initTextBox() {
      new WelcomeNotes();
   }

   public WelcomeNotes() {
      this.setTitle("Welcome Message");
      this.setVisible(true);
      this.setSize(400, 200);
      this.setDefaultCloseOperation(1);
      this.jp.add(this.jt);
      this.jt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = WelcomeNotes.this.jt.getText();
            WelcomeNotes.this.jl.setText(var2);
         }
      });
      this.jp.add(this.jb);
      this.jb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = WelcomeNotes.this.jt.getText();
            Welcome.handleWelcome(var2);
         }
      });
      this.jp.add(this.jl);
      this.add(this.jp);
   }
}
