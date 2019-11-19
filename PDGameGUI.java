/***********************************************************
 CSCI 502 - Assignment 4 – Fall 2019

 Programmers: Rahul Reddy Gopu (Z1839171)
              Saran Kumar Reddy Padala (Z1840816)

 Section: 1
 TA: Sindhusha Parimi
 Date Due: November 10, 2019
 ************************************************************/

/**This class provides a Graphical User Interface (GUI) for user to play the game. */


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;

public class PDGameGUI extends JFrame implements ActionListener {

    private int numOfClicks = 0;
    private int mainClick = 0;
    //instance variables for PDGameAPP , all methods can access these

    private final DefaultListModel<String> listModelPtr = new DefaultListModel<String>();
    // Default List model is the standard mode for how a JList will be operated, will put in next statement below

    private JList<String> finishedGamesListPtr = new JList<String>(listModelPtr);
    // this  is list on top left side and will  show times of games played that user will click to see stats of a game

    private PDGame currentPDGame = null;
    private String gameStartTimeStr = null;
    private final HashMap<String, GameStat> stats = new HashMap<>(); //keep same HashMap for games played

    private int computerStrategy = 1;  //this will be filled in by the choice made by user in combo box

    //Combo-Box
    private JComboBox<Object> computerStrategyCB = null; //combo box on right side, pointer will be filled in constructor

    //Text Area
    private final JTextArea gameResultsTA = new JTextArea(20, 30); //this is  large text area on right side

    //Text Fields for GUI
    private final JTextField roundsTF = new JTextField(10);
    private final JTextField computerStrategyTF = new JTextField(15);
    private final JTextField playerSentenceTF = new JTextField(10);
    private final JTextField computerSentenceTF = new JTextField(10);
    private final JTextField winnerTF = new JTextField(10);

    //Labels for GUI
    private final JLabel roundsPlayedL = new JLabel("Rounds Played");
    private final JLabel computerStrategyL1 = new JLabel("Computer Strategy");
    private final JLabel computerStrategyL2 = new JLabel("Computer Strategy");
    private final JLabel playerSentenceL = new JLabel("Player Sentence");
    private final JLabel computerSentenceL = new JLabel("Computer Sentence");
    private final JLabel winnerL = new JLabel("Winner");
    private final JLabel decisionL = new JLabel("Your decision this round?");

    //Buttons for GUI
    private final JButton startB = new JButton("Start New Game");
    private final JButton silentB = new JButton("Remain Silent");
    private final JButton betrayB = new JButton("Testify");

    public static void main(String[] args) throws Exception {
        createAndShowGUI();
    }

    private static void createAndShowGUI() throws Exception {
        // Create and set up the window.
        PDGameGUI pdg1 = new PDGameGUI(); //call constructor below to set the window to user
        pdg1.addListeners();     //method will add listeners to buttons

        // Display the window and pack together all the panels, etc

        pdg1.pack();
        pdg1.setVisible(true);

    }

    //CONSTRUCTOR WHERE YOU BUILD THE SWING INTERFACE (partial code follows)
    private PDGameGUI() throws Exception {
        super("Prisoner's Dilemma");  //fills in the menu are of JFrame with message
        currentPDGame = new PDGame(System.getProperty("user.dir") + "//random12.txt");
        setLayout(new BorderLayout());

        // Set up left yellow panel
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBackground(c1);
        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel panel3 = new JPanel(new GridLayout(5, 2));
        panel3.setPreferredSize(new Dimension(500, 150));
        JPanel panel4 = new JPanel(new GridLayout(2, 1));
        JPanel panel5 = new JPanel(new FlowLayout());
        panel5.setBackground(c2);
        JPanel panel6 = new JPanel(new FlowLayout());
        panel6.setBackground(c1);

        String title = "List of Games Played";
        Border border = BorderFactory.createTitledBorder(title);
        panel1.setBorder(border);

        add(panel1, BorderLayout.WEST);
        add(panel2, BorderLayout.EAST);

        //set up JList and put it in a scroll pane for scrolling
        finishedGamesListPtr.setFont(new Font("SansSerif", Font.BOLD, 24));
        finishedGamesListPtr.setVisibleRowCount(10);
        finishedGamesListPtr.setFixedCellWidth(450);
        finishedGamesListPtr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel3.setBackground(c1);
        panel1.add(new JScrollPane(finishedGamesListPtr), BorderLayout.NORTH);
        panel1.add(panel3, BorderLayout.SOUTH);

        panel3.add(roundsPlayedL);
        roundsTF.setEditable(false);
        panel3.add(roundsTF);
        panel3.add(computerStrategyL2);
        computerStrategyTF.setEditable(false);
        panel3.add(computerStrategyTF);
        panel3.add(playerSentenceL);
        playerSentenceTF.setEditable(false);
        panel3.add(playerSentenceTF);
        panel3.add(computerSentenceL);
        computerSentenceTF.setEditable(false);
        panel3.add(computerSentenceTF);
        panel3.add(winnerL);
        winnerTF.setEditable(false);
        panel3.add(winnerTF);

        panel2.add(panel4, BorderLayout.NORTH);
        gameResultsTA.setEditable(false);
        panel2.add(new JScrollPane(gameResultsTA), BorderLayout.SOUTH);

        panel4.add(panel5, BorderLayout.NORTH);
        panel4.add(panel6, BorderLayout.SOUTH);

        panel6.add(decisionL);
        panel6.add(silentB);
        panel6.add(betrayB);

        //Two statements below prepare the combo box with computer strategies, need to convert the strategies array list to an array ,
        // and then it gets placed in combo box
        Object[] strategyArray = currentPDGame.getStrategies().toArray(); //convert AL to array
        computerStrategyCB = new JComboBox<>(strategyArray);   //place array in combo box
        computerStrategyCB.setEditable(false);
        computerStrategyCB.setSelectedIndex(0); //this sets starting value to first string in array
        panel5.add(computerStrategyL1);
        panel5.add(computerStrategyCB);
        panel5.add(startB);
    } //end constructor


    //hook up listeners to buttons
    private void addListeners() {
        startB.addActionListener(this);
        silentB.addActionListener(this);
        betrayB.addActionListener(this);
        computerStrategyCB.addActionListener(this);
        finishedGamesListPtr.addListSelectionListener(this::valueChanged); //the  JLIST event listener code is addListSelectionListener
    }

    //HANDLES WHAT BUTTON WAS CLICKED AND WHAT WAS CHOSEN BY COMBO BOX, and calls appropriate method
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startB) {
            try {
                startGame();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == silentB) {
            cooperate();
        } else if (e.getSource() == betrayB) {
            betray();
        } else if (e.getSource() == computerStrategyCB) {  //when user chooses an item in combo box, this handles it
            computerStrategy = computerStrategyCB.getSelectedIndex() + 1; //fills in this variable up top
        }

        endGame();
    }

    //You need to code these methods that are called by actionPerformed
    private void promptPlayer() {
        gameResultsTA.append("\n1. Cooperate with your partner and remain silent.\n2. Betray and testify against your partner.\n\nWhat is your decision for this round?\n");
    }

    private void cooperate() {
        if (mainClick == 1) {
            this.numOfClicks++;
            currentPDGame.setUserHistory(1);
            String outcome = currentPDGame.playRound(1);
            gameResultsTA.append("\n --- ROUND-" + numOfClicks + " Scores ---");
            gameResultsTA.append(outcome);
            if (numOfClicks != 5) promptPlayer();
        }
    }

    private void betray() {
        if (mainClick == 1) {
            this.numOfClicks++;
            currentPDGame.setUserHistory(2);
            String outcome = currentPDGame.playRound(2);
            gameResultsTA.append("\n --- ROUND-" + numOfClicks + " Scores ---");
            gameResultsTA.append(outcome);
            if (numOfClicks != 5) promptPlayer();
        }
    }

    private void endGame() {
        //at end of this routine, do this to add the game to upper left JList ->
        if (numOfClicks == 5) {
            mainClick = 0;
            numOfClicks = 0;
            listModelPtr.addElement(gameStartTimeStr);
            gameResultsTA.append(currentPDGame.getStats().getScores());
            computerStrategyCB.setEnabled(true);
            startB.setEnabled(true);
        }
    }

    //here is some guidance to start the game when start game is pressed by the user
    private void startGame() throws Exception {

        currentPDGame = new PDGame(System.getProperty("user.dir") + "//random12.txt");
        currentPDGame.setStrategy(computerStrategy);

        gameStartTimeStr = (new Date()).toString();
        stats.put(gameStartTimeStr, currentPDGame.getStats());

        this.mainClick++;
        startB.setEnabled(false);
        computerStrategyCB.setEnabled(false);
        gameResultsTA.setText("** Prisoner's Dilemma ** -5 rounds in this version of game\n");

        promptPlayer();

    }

    //user has clicked on a finished game in upper left JList box,
    //this show results from game
    private void valueChanged(ListSelectionEvent e) {

        if (!finishedGamesListPtr.isSelectionEmpty()) {
            String searchKey = (String) finishedGamesListPtr.getSelectedValue(); //get out time of game and look up in hash map

            roundsTF.setText(Integer.toString(currentPDGame.getStats().getRoundsPlayed()));
            roundsTF.setFont(new Font("SansSerif", Font.PLAIN, 20));

            computerStrategyTF.setFont(new Font("SansSerif", Font.PLAIN, 16));
            computerStrategyTF.setText(" " + stats.get(searchKey).getComputerStrategyStr());

            playerSentenceTF.setFont(new Font("SansSerif", Font.PLAIN, 20));
            playerSentenceTF.setText(" " + String.format("%d %s", stats.get(searchKey).getPlayerYrs(),
                    ((stats.get(searchKey).getPlayerYrs() > 1) ? " years" : " year")));

            computerSentenceTF.setFont(new Font("SansSerif", Font.PLAIN, 20));
            computerSentenceTF.setText(" " + String.format("%d %s", stats.get(searchKey).getComputerYrs(),
                    ((stats.get(searchKey).getComputerYrs() > 1) ? " years" : " year")));

            winnerTF.setFont(new Font("SansSerif", Font.PLAIN, 20));
            winnerTF.setText(stats.get(searchKey).getWinner());
        }
    }

    private Color c2 = new Color(3, 169, 252);  //higher numbers means lighter colors
    private Color c1 = new Color(3, 252, 194);  //higher numbers means lighter colors

}