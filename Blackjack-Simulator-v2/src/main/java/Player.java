import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Gambler {

    private Hand hand;

    public Player() {
        this.hand = new Hand();
    }

    public Player(Hand hand) {
        this.hand = hand;
    }

    @Override
    public Hand getHand() {
        return this.hand;
    }

    @Override
    public String getAction(Hand... opponentHand) {

        if (hand.getCount() < 9 && !hand.cardsAreEqual())
            return "H";

        else if(hand.getCount() < 9 && hand.cardsAreEqual()) {
            try {
                return getActionWrittenInStrategy(opponentHand[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (hand.getCount() >= 9 && hand.getCount() < 17) {
            try {
                return getActionWrittenInStrategy(opponentHand[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(hand.getCount() >= 17 && hand.getCardsNumber() == 2 && (hand.oneCardIsAce() || hand.cardsAreEqual())) {
            try {
                return getActionWrittenInStrategy(opponentHand[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "S";
    }

    private String getActionWrittenInStrategy(Hand opponentHand) throws IOException {
        String action = "S";

        int neededColumn = opponentHand.get(1).getCount() - 1;

        if(!hand.oneCardIsAce() && !hand.cardsAreEqual()) {
            int neededRow = hand.getCount() - 7;
            action = findActionAtNeededColumnAndRow(neededColumn, neededRow);
        }
        else if(hand.oneCardIsAce() && !hand.cardsAreEqual()) {
            int neededRow = hand.getCount() - 2;
            action = findActionAtNeededColumnAndRow(neededColumn, neededRow);
        }
        else if(hand.cardsAreEqual()) {
            int neededRow = 0;
            if(!hand.oneCardIsAce())
                neededRow = 19 + (Integer.parseInt(hand.get(1).getValue()) - 2);
            if(hand.oneCardIsAce())
                neededRow = 27;
            action = findActionAtNeededColumnAndRow(neededColumn, neededRow);
        }

        return action;
    }

    private String findActionAtNeededColumnAndRow(int neededColumn, int neededRow) throws IOException {
        File strategyFile = new File("./strategy.txt");
        Scanner fileScanner = new Scanner(strategyFile);

        String foundRow = "";

        List<String> rows = new ArrayList<>();

        while(fileScanner.hasNextLine())
            rows.add(fileScanner.nextLine());

        fileScanner.close();

        foundRow = rows.get(neededRow);
        System.out.println(foundRow);
        String action = foundRow.split(" ")[neededColumn];

        return action;
    }
}


