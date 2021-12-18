import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GreedMain {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        intro();
        GreedManager game = new GreedManager(initializePlayers(console));
        boolean isNextRound = false;
        do {
            String winner = playOneRound(console, game);
        } while (isNextRound);
    }

    public static void intro() {
        System.out.println("Welcome to the game of Greed!");
        System.out.println("Greed is a press-your-luck dice rolling game with 2 or more players.");
        System.out.println("In the game, each player rolls the dice and tries to earn as many points" +
                " as possible from the result.");
        System.out.println("The player with the most points wins the game!");
        System.out.println();
    }

    public static Set<String> initializePlayers(Scanner console) {
        System.out.print("How many players will be playing? ");
        int numOfPlayers = console.nextInt();
        while (numOfPlayers < 2) {
            System.out.println("Please answer with 2 or more players.");
            System.out.print("How many players will be playing? ");
            numOfPlayers = console.nextInt();
        }

        Set<String> playerNames = new HashSet<>();
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("What is the name of player " + (i + 1) + "? ");
            String playerName = console.nextLine();
            while (!playerNames.contains(playerName)) {
                System.out.println("Please answer with a name that doesn't already exist.");
                System.out.print("What is the name of player " + (i + 1) + "? ");
                playerName = console.nextLine();
            }
            playerNames.add(playerName);
        }

        return playerNames;
    }

    public static String playOneRound(Scanner console, GreedManager game) {
        for (String playerName : game.getPlayerNames()) {
            System.out.println("It is " + playerName + "'s turn! ");

            int[] diceValues = new int[game.SIDES];
            for (int i = 0; i < game.DICE_COUNT; i++) {
                System.out.println("Dice " + (i + 1) + " value: ");
                int diceValue = console.nextInt();
                while (diceValue <= 0 || game.SIDES < diceValue) {
                    System.out.println("A roll of " + diceValue + " is not possible!");
                    System.out.println("Please input a valid value.");
                    System.out.println("Dice " + (i + 1) + " value: ");
                    diceValue = console.nextInt();
                }
                diceValues[i] = diceValue;
            }

            game.put(playerName, diceValues);
        }

        return null;
    }

//    public boolean yesTo(String prompt) {
//        System.out.print(prompt + " (y/n)? ");
//        String response = console.nextLine().trim().toLowerCase();
//        while (!response.equals("y") && !response.equals("n")) {
//            System.out.println("Please answer y or n.");
//            System.out.print(prompt + " (y/n)? ");
//            response = console.nextLine().trim().toLowerCase();
//        }
//        return response.equals("y");
//    }
}