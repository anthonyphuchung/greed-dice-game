/**
 * Anthony Chung
 *
 * This program allows the user to play a game of Greed, a press-your-luck dice rolling game.
 * The user will initially be prompted for the number of players and the name of each player.
 * The user can play an infinite number of rounds
 */

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GreedMain {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        intro();
        GreedManager game = new GreedManager(initializePlayers(console));
        int round = 1;
        boolean isNextRound = false;
        do {
            playOneRound(round, console, game);
            displayLeaderboard(game);
            isNextRound = continueGame(console);
            round++;
        } while (isNextRound);

        displayWinners(game);
    }

    /**
     * Prints introductory information about the game of Greed.
     */
    public static void intro() {
        System.out.println("Welcome to the game of Greed!");
        System.out.println("Greed is a press-your-luck dice rolling game with 2 or more players.");
        System.out.println("In the game, each player rolls the dice and tries to earn as many points" +
                " as possible from the result.");
        System.out.println("The player with the most points wins the game!");
        System.out.println();
    }

    /**
     * Prompts the user for the number of players and the name of each player. Returns the set of
     * player names.
     * @param console - used for user input
     * @return - the set of player names.
     */
    public static Set<String> initializePlayers(Scanner console) {
        System.out.print("How many players will be playing? (input an integer): ");
        int numOfPlayers = console.nextInt(); // console.nextInt() does need read newline character
        console.nextLine();
        while (numOfPlayers < 2) {
            System.out.println("Please answer with 2 or more players.");
            System.out.print("How many players will be playing? (input an integer): ");
            numOfPlayers = console.nextInt();
            console.nextLine();
        }

        Set<String> playerNames = new HashSet<>();
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("What is the name of player " + (i + 1) + "? ");
            String playerName = console.nextLine();
            while (playerNames.contains(playerName)) {
                System.out.println("Please answer with a name that doesn't already exist.");
                System.out.print("What is the name of player " + (i + 1) + "? ");
                playerName = console.nextLine();
            }
            playerNames.add(playerName);
        }

        return playerNames;
    }

    /**
     * Plays one round of Greed by prompting each player for the values of each dice on their roll.
     *
     * @param round - the round number the players are on.
     * @param console - used for user input.
     * @param game - the game that is currently being played.
     */
    public static void playOneRound(int round, Scanner console, GreedManager game) {
        for (String playerName : game.getPlayerNames()) {
            System.out.println("It is " + playerName + "'s turn! ");


            int[] diceValues = new int[GreedManager.DICE_COUNT];
            for (int i = 0; i < GreedManager.DICE_COUNT; i++) {
                System.out.print("Dice " + (i + 1) + " value: ");
                int diceValue = console.nextInt();
                console.nextLine();
                while (diceValue <= 0 || GreedManager.SIDES < diceValue) {
                    System.out.println("A roll of " + diceValue + " is not possible!");
                    System.out.println("Please input a valid value.");
                    System.out.print("Dice " + (i + 1) + " value: ");
                    diceValue = console.nextInt();
                    console.nextLine();
                }
                diceValues[i] = diceValue;
            }

            game.put(playerName, diceValues);
        }
        System.out.println("Round " + round + " has finished!");
    }

    /**
     * Displays the leaderboard where players with the highest scores are on the top and players with lower
     * scores are on the bottom. If two or more players are tied for the highest score, the player with the
     * lexicographically smaller name will be closer to the top of the leader board.
     * @param game - the game that is currently being played.
     */
    public static void displayLeaderboard(GreedManager game) {
        System.out.println("LEADEBOARD");
        System.out.println("----------------------------------");
        String leaderboard = game.getLeadboard();
        leaderboard = leaderboard.substring(1, leaderboard.length() - 1);
        String[] players = leaderboard.split(",");
        for (int i = 0; i < players.length; i++) {
            System.out.println((i + 1) + ". " + players[i].trim());
        }
    }

    /**
     * Displays the name of the winner(s) of the game.
     * @param game - the game that is currently being played.
     */
    public static void displayWinners(GreedManager game) {
        List<String> winners = game.getWinners();
        StringBuilder sb = new StringBuilder();
        System.out.println("THE WINNERS OF THIS GAME OF GREED ARE");
        System.out.println("----------------------------------");
        sb.append(winners.get(0));
        for (int i = 1; i < winners.size(); i++) {
            sb.append(", " + winners.get(i));
        }
        System.out.println(sb);
    }

    /**
     * Returns false if the user inputs q. Otherwise, return true.
     * @param console - used for user input
     * @return - alse if the user inputs q. Otherwise, return true.
     */
    public static boolean continueGame(Scanner console) {
        System.out.println("Would you like to play another round?");
        System.out.println ("Enter (q) to quit game ");
        System.out.print("Enter any other input to continue ");
        String response = console.nextLine().trim().toLowerCase();
        System.out.println();
        return !response.equals("q");
    }
}