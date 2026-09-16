package week1.class_problems;

public class RockPaperScissors {
    public static void main(String[] args) {
        String player1 = "rock";
        String player2 = "scissors";

        if (player1.equals(player2)) {
            System.out.println("It's a tie!");
        } else if ((player1.equals("rock") && player2.equals("scissors")) ||
                   (player1.equals("scissors") && player2.equals("paper")) ||
                   (player1.equals("paper") && player2.equals("rock"))) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Player 2 wins!");
        }
    }
}