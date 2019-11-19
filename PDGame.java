/***********************************************************
 CSCI 502 - Assignment 4 – Fall 2019

 Programmers: Rahul Reddy Gopu (Z1839171)
 Saran Kumar Reddy Padala (Z1840816)

 Section: 1
 TA: Sindhusha Parimi
 Date Due: November 10, 2019
 ************************************************************/

/*This class represents the game itself and controls the logic for it.*/

import java.util.*;
import java.io.*;

class PDGame {

    ArrayList<Integer> userHistory = new ArrayList<Integer>();
    ArrayList<String> computerStrategies = new ArrayList<String>();
    GameStat gameStatPtr = new GameStat();
    private int strategy;
    Scanner input;

    /**PDGame class Constructor which initialize the Scanner to read from a text file and initialises computer strategies ArrayList */
    public PDGame(String file) throws Exception {
        computerStrategies.add("Reads Strategy From Input File");
        computerStrategies.add("Tit-For-Tat");
        computerStrategies.add("Tit-For-Two-Tats");
        computerStrategies.add("Random Choice by Computer");

        input = new Scanner(new File(file));    
    }

    /**This method controls all the logic of the game  
     * and assigns prison sentence years for player and computer.
    */
    public String playRound(int decision) {

        String outcome = "";
        int computerDecision = figureComputerDecision(strategy);

        if(decision == 1 && computerDecision == 1) {
            gameStatPtr.update(2, 2);
            String str = "\nYou and your partner remain silent.\nYou both get 2 years in prison.\n";
            outcome = str;
        }
        else if (decision == 2 && computerDecision == 1) {
            gameStatPtr.update(1, 5);
            String str = "\nYou testify against your partner and they remain silent.\nYou get 1 year in prison and they get 5 years.\n";
            outcome = str;   
        }
        else if (decision == 1 && computerDecision == 2) {
            gameStatPtr.update(5, 1);
            String str = "\nYou remain silent and they testify against you.\nYou get 5 years in prison and they get 1 year.\n";
            outcome = str;
        }
        else if (decision == 2 && computerDecision == 2) {
            gameStatPtr.update(3, 3);
            String str = "\nYou and your partner testified against each other.\nYou both get 3 years in prison.\n";
            outcome = str;
        }

        return outcome;
    }

    /**This method figures out the decision of computer.
     * Takes an int variable and returns int variable
     */
    private int figureComputerDecision(int strategy) {
        
        int decision = 0;

        switch(strategy) {
            case 1: 
                decision = randomIntFromFile();
                break;
            case 2: 
                decision = titForTatStrategy();
                break;
            case 3: 
                decision = titForTwoTatsStrategy();
                break;
            case 4: 
                decision = randomComputerStrategy();
                break;
        }

        return decision;
    }

    /** This method returns random 1 and 2 from an input file */
    private int randomIntFromFile() {
        
        int decision = input.nextInt();
        return decision;
    }

    /** This method returns random 1 or 2 */
    private int randomComputerStrategy() {
        return (int)(Math.random() * 2) + 1;
    }

    /** This method returns either 1 or 2 based on Tit-for-Tat strategy */
    private int titForTatStrategy() {

        int decision = 0;

        if(gameStatPtr.getRoundsPlayed() == 0){
            decision = 1;
        }
        else if (gameStatPtr.getRoundsPlayed() >= 1) {
            int userDecision = userHistory.get(userHistory.size() - 2);
            decision = userDecision;
        }

        return decision;
    }

    /** This method returns either 1 or 2 based on Tit-for-Two-Tats strategy */
    private int titForTwoTatsStrategy() {

        int decision = 0; 

        if(gameStatPtr.getRoundsPlayed() == 0 || gameStatPtr.getRoundsPlayed() == 1){
            decision = 1;
        }
        else if (gameStatPtr.getRoundsPlayed() >= 2) {
            if(userHistory.get(userHistory.size() - 2) == 2 && userHistory.get(userHistory.size() - 3) == 2) {
                decision = 2;
            }
            else {
                decision = 1; 
            }
        }

        return decision;
    }

    /* Returns an ArrayList of strings, in the array list are the 5 strategies, one can choose for the computer (File, tit for tat, etc) */
    public ArrayList<String> getStrategies() {
        return computerStrategies;
    }
    
    /*---------    USER HISTORY SET METHOD   -------------*/ 
    public void setUserHistory(int userDecision) {
        this.userHistory.add(userDecision);
    } 

    /**Get method for the GameStat data member */
    public GameStat getStats() {
        return gameStatPtr;
    }

    /* Set method for the strategy data member. This also calls the GameStat setter with the string obtained from the ArrayList at the appropriate index. */
    public void setStrategy(int strategy) {
        this.strategy = strategy;
        gameStatPtr.setComputerStrategyStr(getStrategies().get(strategy - 1));
    }

    
}