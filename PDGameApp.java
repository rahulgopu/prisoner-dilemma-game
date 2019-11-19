/***********************************************************
CSCI 502 - Assignment 3 – Fall 2019

Programmers: Rahul Reddy Gopu (Z1839171)
             Saran Kumar Reddy Padala (Z1840816)

Section: 1
TA: Sindhusha Parimi
Date Due: October 13, 2019
************************************************************/

/**This class provides an interface for user input. */

import java.util.*;

class PDGameApp {

    private static final int[] compStrategy = {1, 2, 3, 4};
    private static final int[] playerDecision = {1, 2};
    private static final String[] finalDecision = {"n", "y", "N", "Y"};
    
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        
        PDGame pdGameObj  = null;
        GameStat statObj;
        Map < String, GameStat > stats = new HashMap < String, GameStat > ();
        boolean wannaPlay = true;

        while (wannaPlay) {
            
            try{
                pdGameObj = new PDGame(System.getProperty("user.dir") + "//random12.txt");
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            
            System.out.println("\n***Starting A Game of Prisoner's Dilemma *** -5 rounds in this version of game \n");
            System.out.println("--HERE ARE STRATEGIES AVAILABLE FOR THE COMPUTER");
            
            for (String str : pdGameObj.getStrategies())
	        { 		      
	           System.out.println(str); 		
            }

            int strategy = 0;

            while( Arrays.binarySearch(compStrategy, strategy) < 0 ) {
                System.out.print("Select a strategy from above list (only numbers from 1 to 4) for the Computer to use in the 5 rounds : ");
                strategy = input.nextInt();
            }

            pdGameObj.setStrategy(strategy);
            
            statObj = pdGameObj.getStats();
            stats.put(new java.util.Date().toString(), statObj);
            
            /**The game starts here with 5 rounds */

            for (int i = 0; i < 5; i++) {

                System.out.print("\nBEGIN A ROUND - Here are your 2 choices\n\n1. Remain silent.\n2. Betray and testify against.");
                
                int decision = 0;

                while( Arrays.binarySearch(playerDecision, decision) < 0 ) {
                    System.out.print("\n\n----What is your decision in this round (only numbers 1 or 2)? ");
                    decision = input.nextInt();
                }
            
                pdGameObj.setUserHistory(decision);

                String outcome = pdGameObj.playRound(decision);
                System.out.println(outcome);
            }

            System.out.println(pdGameObj.getStats().getScores());
            System.out.println(statObj.getWinner());
            
            String nextGame = "";
            while( Arrays.binarySearch(finalDecision, nextGame) < 0 ) {
                System.out.print("\n--Would you like to play another game (y/n)? \n          (Please give either 'y' or 'n') :");
                nextGame = input.next();
            }
            
            if (nextGame.equalsIgnoreCase("n")) {
                wannaPlay = false;
            }
            else if (nextGame.equalsIgnoreCase("y")) {
                wannaPlay = true;
            }
            else {
                System.out.println("Please enter valid input.");
            } 
            
            
            System.out.println("\n                    Summary of games and session times:\n");
            for(String key: stats.keySet())
            {
                System.out.println(key);
                System.out.println(stats.get(key).getWinner() + "The computer used " + "'" + stats.get(key).getComputerStrategyStr() + "' strategy." + "\n");
            }
        }
    }
}