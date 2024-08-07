package com.elementars.eclient.guirewrite.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextNotes extends JFrame {
   // $FF: synthetic field
   JTextField jt = new JTextField(30);
   // $FF: synthetic field
   JLabel jl = new JLabel();
   // $FF: synthetic field
   JButton jb = new JButton("Set Text");
   // $FF: synthetic field
   JPanel jp = new JPanel();

   public static void initTextBox() {
      new TextNotes();
   }

   public TextNotes() {
      this.setTitle("TextNotes");
      this.setVisible(true);
      this.setSize(400, 200);
      this.setDefaultCloseOperation(1);
      this.jp.add(this.jt);
      this.jt.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = TextNotes.this.jt.getText();
            TextNotes.this.jl.setText(var2);
         }
      });
      this.jp.add(this.jb);
      this.jb.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            String var2 = TextNotes.this.jt.getText();
            StickyNotes.processText(var2);
         }
      });
      this.jp.add(this.jl);
      this.add(this.jp);
   }
}
