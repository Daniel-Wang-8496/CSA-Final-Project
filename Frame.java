import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Frame {
	private int iMoney = 10, iMoral = 0, iSocialCredit = 0, iWeek = 1; //These variables represent the user's money amount, moral points, social credit value, and the week number
	private String sStartingText; //This variable holds the text that greets the user in the starting screen
	private String[] Situations = new String[20]; //This array holds the text for each situation
	private String[][] Options = new String[4][20]; //This array holds the options for each situation. The first dimension represents the option number and the second represents the week number
	private int[][][] OptionScores = new int[3][4][20]; //This array holds the score changes for each decision. The first dimension represents the type of score (money, moral, or social), the second dimension represents the option number, and the third represents the week number. 
	private String[][] Outcomes = new String[4][20]; //This array holds the outcomes for each decision. The first dimension represents the option number and the second represents the week number.
	private JFrame frame = new JFrame("Cool Guy at The Highschool"); //Create a JFrame with the name of our game
	private JPanel panel = new JPanel(new GridBagLayout()); //Create a JPanel with a GridBagLayout
	private JButton Next = new JButton("Next"); //Create a Next Button with the text "Next" but do not add it to the pane yet
	//create buttons for each option without any text or without adding them to the pane
	private JButton Option1 = new JButton();
	private JButton Option2 = new JButton();
	private JButton Option3 = new JButton();
	private JButton Option4 = new JButton();
	private GridBagConstraints Constraints = new GridBagConstraints(); //Create a GridBagConstraint object that will be used to set the constraints for every object in the layout before adding it
	private Font Font40 = new Font("Times New Roman", Font.PLAIN, 40); //Create a new font that most of the buttons can use
	
    public Frame() throws IOException { //This is the default constructor that creates the window and triggers other classes
    	ReadFiles(); //Read all the file to populate the arrays
    	
    	frame.add(panel); //add the panel to the frame
    	
    	//set the constraints for the starting text to fill up available space vertically and horizontally with vertical padding
	    Constraints.fill = GridBagConstraints.BOTH;
	    Constraints.gridx = 0;
	    Constraints.gridy = 0;
	    Constraints.gridwidth = 1;
	    Constraints.ipady = 150;
	    Constraints.weightx = 0.5;
	    
	    JTextArea StartingTextLabel = new JTextArea(sStartingText); //create a new JTextArea for the starting text. We do not use a JLabel because JLabels do not automatically support text wrapping
      Color color = new Color(250,237,40,100);
	    StartingTextLabel.setBackground(color); //Make the JTextArea yellow
	    
	    //Set the TextArea font to the font predefined, set it to wrap at each line, turn off the ability to edit it, and have no preferred size
	    StartingTextLabel.setFont(Font40);
	    StartingTextLabel.setLineWrap(true);
	    StartingTextLabel.setWrapStyleWord(true);
	    StartingTextLabel.setEditable(false);
	    StartingTextLabel.setPreferredSize(null);
	    
	    panel.add(StartingTextLabel, Constraints); //add the TextArea to the panel with set constraints
	    
	    Constraints.gridy = 1; //Change the constraint to be one line down
	    Constraints.ipady = 20; //Decrease the padding to 20
	    Constraints.weighty = 0.5;
	    Dimension FrameSize = new Dimension(1000,750); //Create a dimension for the frame size
	    //set the frame size and make it so that the user cannot resize the window
	    frame.setSize(FrameSize);
	    frame.setResizable(false);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when the window is closed, quit the program
	    AskQuestion();
  }
    
    public void ReadFiles() throws IOException{
    	File file = new File("Scenarios.txt");
        Scanner reader = new Scanner(file);
        sStartingText = reader.nextLine();
        for (int iWeekIndex = 0; iWeekIndex < 20; iWeekIndex ++) {
        	Situations[iWeekIndex] = reader.nextLine();
        	//System.out.println("Week " + iWeekIndex + ": " + Situations[iWeekIndex]);
        	for (int iOptionIndex = 0; iOptionIndex < 4; iOptionIndex ++) {
        		Options[iOptionIndex][iWeekIndex] = reader.nextLine();
        		//System.out.println("\nOption" + iOptionIndex + ": " + Options[iOptionIndex][iWeekIndex]);
        		for (int iScoreIndex = 0; iScoreIndex < 3; iScoreIndex++) {
        			OptionScores[iScoreIndex][iOptionIndex][iWeekIndex] = Integer.parseInt(reader.nextLine());
        		}
        		Outcomes[iOptionIndex][iWeekIndex] = reader.nextLine();
        	}
        }
    }
    
    public void AskQuestion() {
    	JButton StartButton = new JButton("Start Playing"); //Create a start button
    	StartButton.setFont(Font40); //set the button font
	    panel.add(StartButton, Constraints); //Add the start button to the panel with the constraints defined in the default constructor
	    frame.setVisible(true); //Make the frame visible
    	StartButton.addActionListener(new ActionListener(){ //Add an action listener to the button that waits for it to be clicked
    		@Override
    		public void actionPerformed(ActionEvent e) { //When the button is clicked . . .
       			DisplayQuestion();
    		}
    	});
    }
    
    public void DisplayQuestion() {
    	panel.removeAll(); //remove the label and button from the panel
			
		//reset the constraints to fill horizontally starting in the top left cell
		Constraints.gridx=0;
		Constraints.gridy=0;
		Constraints.ipadx=0;
		Constraints.ipady=0;
		Constraints.gridwidth = 1;
		Constraints.weightx = 0.1;
		Constraints.weighty = 0;
		Constraints.fill = GridBagConstraints.HORIZONTAL;
		
		//define the size of 1/2 the screen
		Dimension dimHalfs = new Dimension(frame.getBounds().width/2, 100);
		Constraints.insets = new Insets(2,2,2,2);
		
	    JTextArea WeekLabel = new JTextArea("Week " + iWeek + " out of 20"); //create a new label that tells the user the week number
	    WeekLabel.setFont(Font40); //set the label's font
	    WeekLabel.setLineWrap(true); //set the line wrap
	    WeekLabel.setWrapStyleWord(true); //set the line wrap to be between words, not between letters
	    WeekLabel.setEditable(false); //make it so the text area cannot be edited
	    //set the size of the label to take up half the screen width
	    WeekLabel.setPreferredSize(dimHalfs);
	    WeekLabel.setMinimumSize(dimHalfs);
	    WeekLabel.setMaximumSize(dimHalfs);
	    
	    WeekLabel.invalidate(); //mark this label as not up to date
	    panel.add(WeekLabel, Constraints); //add the label to the panel
	    
		Constraints.gridx=1; //set the constraints to put the next element in the second column
		JTextArea MoneyMoral = new JTextArea("Money Points: " + iMoney + "\nMoral Points: " + iMoral); //set the label text to state the money and moral points
		
		//set the label's font and wrap as before. Set the size to take up half the screen.
		MoneyMoral.setFont(Font40);
		MoneyMoral.setLineWrap(true);
		MoneyMoral.setWrapStyleWord(true);
		MoneyMoral.setEditable(false);
		MoneyMoral.setPreferredSize(dimHalfs);
		MoneyMoral.setMinimumSize(dimHalfs);
		MoneyMoral.setMaximumSize(dimHalfs);
		
		MoneyMoral.invalidate(); //mark the label as not up to date and add it to the panel
	    panel.add(MoneyMoral, Constraints);
	    
	    //create a text area with the situation on the next line and set it up to take up the whole line
		Constraints.gridx=0;
		Constraints.gridy=1;
		Constraints.weightx = 0.5;
		Constraints.weighty = 0.5;
		Constraints.fill = GridBagConstraints.BOTH;
		Constraints.gridwidth=2;
		Constraints.ipady=100; //add extra padding
		JTextArea Situation = new JTextArea(Situations[iWeek - 1]);
		Situation.setFont(new Font("Times New Roman", Font.PLAIN, 48)); //set the font to TNR 48
		//set the JTextArea properties as before
		Situation.setLineWrap(true);
		Situation.setWrapStyleWord(true);
		Situation.setEditable(false);
		Situation.setPreferredSize(null);
		Situation.invalidate(); //mark the text area as not updated
	    panel.add(Situation, Constraints); //add it to the panel
	    
	    Constraints.gridy=2;
		Constraints.gridwidth=1;
		Constraints.ipady=0;
		Constraints.weightx = 0.1;
		Constraints.fill = GridBagConstraints.BOTH;
		Option1 = new JButton("<html>" + Options[0][iWeek-1]);
		Option1.setFont(Font40);
		Option1.setPreferredSize(dimHalfs);
		Option1.setMinimumSize(dimHalfs);
		Option1.setMaximumSize(dimHalfs);
		Option1.invalidate();
	    panel.add(Option1, Constraints);
	    
	    Constraints.gridx=1;
		Option2 = new JButton("<html>" + Options[1][iWeek - 1]);
		Option2.setFont(Font40);
		Option2.setPreferredSize(dimHalfs);
		Option2.setMinimumSize(dimHalfs);
		Option2.setMaximumSize(dimHalfs);
		Option2.invalidate();
	    panel.add(Option2, Constraints);
	    
	    if (!Options[2][iWeek-1].equals("N/A")) {
		    Constraints.gridx=0;
		    Constraints.gridy=3;
			Option3 = new JButton("<html>" + Options[2][iWeek - 1]);
			Option3.setFont(Font40);
			Option3.setPreferredSize(dimHalfs);
			Option3.setMinimumSize(dimHalfs);
			Option3.setMaximumSize(dimHalfs);
			Option3.invalidate();
		    panel.add(Option3, Constraints);
		    
		    Constraints.gridx=1;
			Option4 = new JButton("<html>" + Options[3][iWeek - 1]);
			Option4.setFont(Font40);
			Option4.setPreferredSize(dimHalfs);
			Option4.setMinimumSize(dimHalfs);
			Option4.setMaximumSize(dimHalfs);
			Option4.invalidate();
		    panel.add(Option4, Constraints);
	    }
	    
	    panel.revalidate();
		panel.repaint();
		ButtonClicks();
    }
    
    public void ButtonClicks() { //this method has the action listeners for all four buttons
    	Option1.addActionListener(new ActionListener() { //All of the buttons work the same way. When the button is clicked, the scores are incremented and the outcome is displayed with the appropriate message.

			@Override
			public void actionPerformed(ActionEvent e) {
				iMoney += OptionScores[0][0][iWeek-1];
				iMoral += OptionScores[1][0][iWeek-1];
				iSocialCredit += OptionScores[2][0][iWeek-1];
				DisplayOutcome(Outcomes[0][iWeek-1], false, null);
			}
    		
    	});
    	
    	Option2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iMoney += OptionScores[0][1][iWeek-1];
				iMoral += OptionScores[1][1][iWeek-1];
				iSocialCredit += OptionScores[2][1][iWeek-1];
				DisplayOutcome(Outcomes[1][iWeek-1], false, null);
			}
    		
    	});
    	
    	Option3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iMoney += OptionScores[0][2][iWeek-1];
				iMoral += OptionScores[1][2][iWeek-1];
				iSocialCredit += OptionScores[2][2][iWeek-1];
				DisplayOutcome(Outcomes[2][iWeek-1], false, null);
			}
    		
    	});
    	
    	Option4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				iMoney += OptionScores[0][3][iWeek-1];
				iMoral += OptionScores[1][3][iWeek-1];
				iSocialCredit += OptionScores[2][3][iWeek-1];
				DisplayOutcome(Outcomes[3][iWeek-1], false, null);
			}
    		
    	});
    }
    
    public void DisplayOutcome(String sOutcome, boolean bEnding, Icon image) {
    	panel.removeAll();
    	Constraints.fill = GridBagConstraints.HORIZONTAL;
	    Constraints.gridx = 0;
	    Constraints.gridy = 0;
	    Constraints.gridwidth = 1;
	    Constraints.ipady = 0;
	    Constraints.weightx = 0.1;
	    Constraints.weighty = 0.;
    	//define the size of 1/2 the screen
    	Dimension dimHalfs = new Dimension(frame.getBounds().width/2, 100);
    	if (bEnding)
    		iWeek = 20;
    	JTextArea WeekLabel = new JTextArea("Week " + iWeek + " out of 20"); //create a new label that tells the user the week number
	    WeekLabel.setFont(Font40); //set the label's font
	    WeekLabel.setLineWrap(true); //set the line wrap
	    WeekLabel.setWrapStyleWord(true); //set the line wrap to be between words, not between letters
	    WeekLabel.setEditable(false); //make it so the text area cannot be edited
	    //set the size of the label to take up half the screen width
	    WeekLabel.setPreferredSize(dimHalfs);
	    WeekLabel.setMinimumSize(dimHalfs);
	    WeekLabel.setMaximumSize(dimHalfs);
	    
	    WeekLabel.invalidate(); //mark this label as not up to date
	    panel.add(WeekLabel, Constraints); //add the label to the panel
	    
		Constraints.gridx=1; //set the constraints to put the next element in the second column
		JTextArea MoneyMoral = new JTextArea("Money Points: " + iMoney + "\nMoral Points: " + iMoral); //set the label text to state the money and moral points
		
		//set the label's font and wrap as before. Set the size to take up half the screen.
		MoneyMoral.setFont(Font40);
		MoneyMoral.setLineWrap(true);
		MoneyMoral.setWrapStyleWord(true);
		MoneyMoral.setEditable(false);
		MoneyMoral.setPreferredSize(dimHalfs);
		MoneyMoral.setMinimumSize(dimHalfs);
		MoneyMoral.setMaximumSize(dimHalfs);
		
		MoneyMoral.invalidate(); //mark the label as not up to date and add it to the panel
	    panel.add(MoneyMoral, Constraints);
	    
	    JTextArea SituationLabel = new JTextArea(sOutcome);
	    SituationLabel.setFont(Font40);
	    SituationLabel.setLineWrap(true);
	    SituationLabel.setWrapStyleWord(true);
	    SituationLabel.setEditable(false);
	    SituationLabel.setPreferredSize(null);
	    SituationLabel.invalidate();
	    if (bEnding) {
	    	JPanel imageAndText = new JPanel(new GridBagLayout());
	    	Constraints.gridy = 0;
	    	imageAndText.add(SituationLabel, Constraints);
	    	Constraints.gridy = 1;
	    	Constraints.weighty = 0.5;
	    	Constraints.fill = GridBagConstraints.HORIZONTAL;
	    	JLabel imageLabel = new JLabel(image);
	    	imageLabel.setSize(1000, 100);
	    	imageAndText.add(imageLabel, Constraints);
	    	Constraints.fill = GridBagConstraints.BOTH;
	    	Constraints.gridx=0;
		    Constraints.gridy = 1;
		    Constraints.gridwidth = 2;
		    Constraints.ipady = 150;
		    Constraints.weightx = 0.5;
		    Constraints.weighty = 0.5;
	    	panel.add(imageAndText, Constraints);
	    } else {
	    	Constraints.fill = GridBagConstraints.BOTH;
	    	Constraints.gridx=0;
		    Constraints.gridy = 1;
		    Constraints.gridwidth = 2;
		    Constraints.ipady = 150;
		    Constraints.weightx = 0.5;
		    Constraints.weighty = 0.5;
	    	panel.add(SituationLabel, Constraints);
	    }
	    Constraints.gridy = 3;
	    Constraints.ipady = 200;
	    Constraints.weighty = 0;
	    if (bEnding) {
	    	Next = new JButton("Game Over");
	    	Constraints.ipady = 0;
	    }
	    
	    Constraints.fill = GridBagConstraints.HORIZONTAL;
	    Next.setFont(Font40);
	    Next.invalidate();
	    panel.add(Next, Constraints);
	    panel.revalidate();
	    panel.repaint();
	    if (bEnding) {
	    	frame.setEnabled(false);
	    }
    	iWeek++;
	    Next.addActionListener(new ActionListener(){ //Add an action listener to the button that waits for it to be clicked
    		@Override
    		public void actionPerformed(ActionEvent e) { //When the button is clicked . . .
    			if (iWeek<21) {
    				DisplayQuestion();
    			} else {
    				try {
						Outcomes();
					} catch (IOException e1) {
						System.out.println("Error reading image");
					}
    			}
    		}
    	}); 
    }
    

    //this method will calculate and print out the user's outcome 
    public void Outcomes() throws IOException{
       //special ending
      if(iSocialCredit >= 6){
         DisplayOutcome("You were kidnapped in the night by the CCP, and you were taken to the glorious Republic of China. There, Xi Jinping greeted you as an honorable guest and you were placed on the prestigious Social Credit Committee along with the Wok and John Xina. You have bing chilling every since.", true, new ImageIcon(ImageIO.read(new File("SpecialEnding.png"))));
        }
        //low money low moral
        else if(iMoney <= 8) {
          if(iMoral <= -2){
             DisplayOutcome("Without any money or morality, you do not end up going to college. However, you need a means of making a living, so you decide to ask your cousin for advice since you know that he’s rich.You end up in jail for selling drugs and deviously licking toilets with your cousin.", true, new ImageIcon(ImageIO.read(new File("LowMoneyLowMoral.png"))));
            }
            //low money high moral
          else if (iMoral >= -1){
        	  DisplayOutcome("You end up living with your parents and help them do chores and even get to try out extreme ironing in real life circumstances. You also decide to get into the pro gaming industry, playing clash royale with royale giant elite barbarian cycle.", true, new ImageIcon(ImageIO.read(new File("LowMoneyHighMoral.png"))));
               }

          //high money low moral
          }else if(iMoney >= 9){
              if(iMoral <= -2){
                  DisplayOutcome("You end up as a high-profile banker whose profits are mostly due to sus operations such as running a drug cartel and counterfeit operations. You also make bank from deviously licking soap dispensers from elementary schools. With the little amount of moral points that you have, you decide to pour your kindness into warning people about their cars extended warranty.", true, new ImageIcon(ImageIO.read(new File("HighMoneyLowMoral.png"))));
                  }
                 //high money high moral
                 else if(iMoral >= -1){
              	   DisplayOutcome("You end up a good businessman with a loving spouse, children, and pets. You have plenty of money and do not need to struggle ever again. You are living the American dream. With all of your extra money, you decide to donate it to charity, and become a notable sponsor.", true, new ImageIcon(ImageIO.read(new File("HighMoneyHighMoral.png"))));
        }
      } 
   }
}