import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
  JButton button = new JButton("Hello World");
  public Frame() {
    super("Hello World");
    super.add(button);
    Dimension MinimumSize = new Dimension(500,250);
    super.setMinimumSize(MinimumSize);
    super.setVisible(true);
  }
}