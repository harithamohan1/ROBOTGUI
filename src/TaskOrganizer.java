import javax.swing.Icon;
import javax.swing.JLabel;

public class TaskOrganizer {

  public int time;
  public JLabel taskID;
  public boolean complete = false;
  public Icon taskImg;

  public TaskOrganizer(JLabel taskID, int time, Icon taskImg) {
    this.time = time;
    this.taskID = taskID;
    this.taskImg = taskImg;
  }
}
