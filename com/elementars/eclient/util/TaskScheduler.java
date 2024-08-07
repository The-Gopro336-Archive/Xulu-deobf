package com.elementars.eclient.util;

import com.elementars.eclient.Xulu;
import java.util.LinkedList;
import java.util.Queue;

public class TaskScheduler {
   // $FF: synthetic field
   private final Queue prioritizedTasks = new LinkedList();
   // $FF: synthetic field
   int delay;
   // $FF: synthetic field
   private final Queue tasks = new LinkedList();

   public void onUpdate() {
      if (this.delay > 0) {
         --this.delay;
      }

      if (this.prioritizedTasks.peek() != null) {
         ((Runnable)this.prioritizedTasks.remove()).run();
         this.delay = (Integer)Xulu.VALUE_MANAGER.getValueByName("Offhand Delay").getValue();
      } else {
         if (this.tasks.peek() != null && this.delay == 0) {
            ((Runnable)this.tasks.remove()).run();
            this.delay = (Integer)Xulu.VALUE_MANAGER.getValueByName("Offhand Delay").getValue();
         }

      }
   }

   public void addTask(Runnable var1) {
      this.tasks.add(var1);
   }

   public void addPrioritizedTask(Runnable var1) {
      this.prioritizedTasks.add(var1);
   }
}
