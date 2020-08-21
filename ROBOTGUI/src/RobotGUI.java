import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class RobotGUI implements ActionListener {

  //global variables
  private static JFrame frame;
  private static JPanel panel;
  private static JTextField robotNameField;
  private static JLabel header, subheader, robotNameId,
      robotTypeId, taskLabel, completeDisplay, success, gifSample;
  private static Icon starterImg;
  private static ButtonGroup radioButtonGroup;
  private static JRadioButton[] buttonArray;
  private static JLabel[] taskLabels, taskDoneLabels;
  private static String[] robotSpecies = {"Unipedal", "Bipedal",
      "Quadrupdeal", "Arachind", "Radial", "Aeronautical"};

  public static void main(String[] args) {
    userInterface();

    buttonArray = new JRadioButton[6];
    radioButtonGroup = new ButtonGroup();

    //radio button for each robot type
    for (int i = 0; i < buttonArray.length; i++) {
      robotType(i, buttonArray, robotSpecies[i]);
      radioButtonGroup.add(buttonArray[i]);
      buttonArray[i].addActionListener(new RobotGUI());
    }

    //robot name and type displayed
    frame.add(panel);
    success = new JLabel("");
    success.setBounds(650, 55, 750, 50);
    success.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    panel.add(success);

    //display incomplete tasks
    taskLabel = new JLabel("To Do:");
    taskLabel.setBounds(10, 275, 150, 30);
    taskLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    panel.add(taskLabel);

    taskLabels = new JLabel[10];
    String[] taskDescription = {"Do the dishes", "Sweep the house", "Do the laundry",
        "Take out the recycling", "Make a sammich",
        "Mow the lawn", "Rake the leaves", "Give the dog a bath", "Bake some cookies",
        "Wash the car"};
    for (int i = 0; i < taskLabels.length; i++) {
      taskLabeler(i, taskDescription[i], 280, taskLabels);
    }

    //display complete tasks
    completeDisplay = new JLabel("Completed Tasks: ");
    completeDisplay.setBounds(10, 500, 700, 25);
    completeDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    panel.add(completeDisplay);

    taskDoneLabels = new JLabel[5];
    for (int i = 0; i < taskDoneLabels.length; i++) {
      taskLabeler(i, "", 500, taskDoneLabels);
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  } //end of main method

  @Override
  public void actionPerformed(ActionEvent e) { //when radio button is clicked
    String name = robotNameField.getText();
    RobotType robot = new RobotType(name, "");
    for (int i = 0; i < buttonArray.length; i++) {
      if (buttonArray[i].isSelected()) {
        robot.type = robotSpecies[i];
      }
    }

    success.setText("Welcome! Your robot's name is: " + robot.name + ". The type of your robot is: "
        + robot.type);

    try { //Pause program briefly
      Thread.sleep(2000);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    }

    TaskOrganizer[] taskList = new TaskOrganizer[10];
    String[] images = {"dishes.gif", "sweeping.gif", "laundry.gif", "recycling.gif", "sandy.png",
        "mowingthelawn.gif", "rakingleaves.gif", "dogbath.gif", "cookiez.gif", "car.gif"};
    int[] milliSecs = {1000, 3000, 10000, 4000, 7000, 20000, 18000, 14500, 8000, 20000};
    for (int i = 0; i < taskList.length; i++) {
      taskList[i] = new TaskOrganizer(taskLabels[i], milliSecs[i],
          new ImageIcon("gifs/" + images[i]));
    }

    //randomly choose task to complete from list
    Random generator = new Random();

    for (int i = 0; i < 5; i++) {
      int taskNum = generator.nextInt(10);
      if (!taskList[taskNum].complete) {
        if (i >= 1 && i <= 3) {
          JOptionPane.showMessageDialog(frame, "Previous Task: \"" +
              taskDoneLabels[i - 1].getText() + "\" is complete! Let's continue.");
        }

        gifSample.setIcon(taskList[taskNum].taskImg);
        JOptionPane
            .showMessageDialog(frame, "Task in Progress: " + taskList[taskNum].taskID.getText()
                + " Time Needed: " + taskList[taskNum].time + " milliseconds. Click to begin!");
        try { //pause until task is complete (based on associated time)
          Thread.sleep(taskList[taskNum].time);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
        for (int j = 0; j < 5; j++) {
          if (i == j) {
            taskDoneLabels[j].setText(taskList[taskNum].taskID.getText());
          }
        }
        //remove task from TO DO list once complete
        taskList[taskNum].taskID.setText("");
        taskList[taskNum].complete = true;
      } else {
        i--;
      }
    }

    //program complete
    gifSample.setIcon(new ImageIcon("gifs/robotDance.gif"));
    JOptionPane.showMessageDialog(frame, "Your robot, " +
        robot.name + ", has successfully completed 5 chores!!!");
  }

  public static void userInterface() {
    //frame: window, panel: layout
    frame = new JFrame();
    panel = new JPanel();
    frame.setSize(1500, 1000);

    starterImg = new ImageIcon("gifs/robot.gif");
    gifSample = new JLabel(starterImg);
    gifSample.setBounds(600, 150, 800, 550);
    panel.add(gifSample);

    panel.setLayout(null);
    frame.setForeground(Color.CYAN);
    frame.setBackground(Color.CYAN);
    panel.setForeground(Color.CYAN);
    panel.setBackground(Color.LIGHT_GRAY);

    header = new JLabel("Welcome to Build-a-Robot!");
    header.setBounds(10, 7, 450, 50);
    header.setBackground(Color.CYAN);
    header.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
    panel.add(header);

    subheader = new JLabel("Get started and watch your robot complete your chores!");
    subheader.setBounds(10, 55, 600, 20);
    subheader.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    panel.add(subheader);

    robotNameId = new JLabel("Enter your Robot Name: ");
    robotNameId.setBounds(10, 85, 250, 35);
    robotNameId.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    panel.add(robotNameId);

    robotNameField = new JTextField();
    robotNameField.setBounds(200, 85, 165, 32);
    panel.add(robotNameField);

    robotTypeId = new JLabel("Select a Robot Type: ");
    robotTypeId.setBounds(10, 118, 240, 35);
    robotTypeId.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    panel.add(robotTypeId);
  }

  public static void robotType(int index, JRadioButton[] buttonArray, String buttonName) {
    buttonArray[index] = new JRadioButton(buttonName);
    buttonArray[index].setBounds(200, 130 + (index * 20), 180, 25);
    buttonArray[index].setActionCommand(buttonName);
    buttonArray[index].setSelected(false);
    panel.add(buttonArray[index]);
  }

  public static void taskLabeler(int index, String task, int startingY, JLabel[] labelArray) {
    labelArray[index] = new JLabel(task);
    labelArray[index].setBounds(200, startingY + (index * 20), 150, 25);
    panel.add(labelArray[index]);
  }
}