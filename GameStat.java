/***********************************************************
 CSCI 502 - Assignment 4 – Fall 2019

 Programmers: Rahul Reddy Gopu (Z1839171)
 Saran Kumar Reddy Padala (Z1840816)

 Section: 1
 TA: Sindhusha Parimi
 Date Due: November 10, 2019
 ************************************************************/

class GameStat {

    private int roundsPlayed = 0;
    private int playerYrs = 0;
    private int computerYrs = 0;

    String computerStrategyStr;

    /*This method Increments the stats of the game for every round when invoked */
    void update (int playerYrs, int computerYrs) {
        roundsPlayed++;
        this.playerYrs = this.playerYrs + playerYrs;
        this.computerYrs = this.computerYrs + computerYrs;
    }

    /** ---------------GET and SET methods for GameStat class -------------- */
    public void setComputerStrategyStr(String computerStrategyStr) {
        this.computerStrategyStr = computerStrategyStr;
    }

    public int getPlayerYrs() {
        return playerYrs;
    }

    public int getComputerYrs() {
        return computerYrs;
    }

    public String getComputerStrategyStr() {
        return computerStrategyStr;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
    /**------------------------------------------------------------------------- */

    /**This method Returns the winner of the game.  */
    public String getWinner() {

        String winnerString = ""; 

        if(playerYrs < computerYrs) {
            winnerString = "\n You the Game player.";
        }
        else if (playerYrs > computerYrs) {
            winnerString = "\n Your Partner/Computer.";
        }
        else if (playerYrs == computerYrs) {
            winnerString = "\n Game Tie.";
        }

        return winnerString;
    }

    /* Returns a string message indicating what the final scores are. */
    public String getScores() {
        return "\n -- END OF ROUNDS, GAME OVER --\n Your prison sentence is: " + getPlayerYrs() + "\n Your partner's/Computer prison sentence is: " + getComputerYrs();
    }
}