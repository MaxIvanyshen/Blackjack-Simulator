import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Gambler {

    private final Hand hand;

    private long money = 10000;

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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    @Override
    public String getAction(Hand... opponentHand) {

        boolean notBasicCase = hand.getCount() > 9 && hand.getCount() < 17;
        boolean countHigherOrEqualTo17 = hand.getCount() >= 17;
        boolean countLowerThan9 = hand.getCount() < 9;
        boolean needToParseStrategyFile = (countLowerThan9 && hand.cardsAreEqual())
                                        || notBasicCase
                                        || (countHigherOrEqualTo17 && hand.getCardsNumber() == 2
                                            && (hand.oneCardIsAce()
                                            || hand.cardsAreEqual()));

        if (countLowerThan9 && !hand.cardsAreEqual())
            return "H";

        else if(needToParseStrategyFile) {
            try {
                return getActionFromStrategyFile(opponentHand[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "S";
    }

    private String getActionFromStrategyFile(Hand opponentHand) throws IOException {

        int neededColumn = opponentHand.get(1).getCount() - 1;
        int neededRow;

        if(hand.cardsAreEqual()) {
            if(hand.oneCardIsAce())
                neededRow = 27;
            else
                neededRow = 19 + (Integer.parseInt(hand.get(1).getValue()) - 2);
        } else {
            if(hand.oneCardIsAce())
                neededRow = hand.getCount() - 2;
            else
                neededRow = hand.getCount() - 7;
        }

        return findActionAtNeededColumnAndRow(neededColumn, neededRow);
    }

    private String findActionAtNeededColumnAndRow(int neededColumn, int neededRow) throws IOException {
        File strategyFile = new File("./strategy.txt");
        Scanner fileScanner = new Scanner(strategyFile);

        List<String> rowsList = new ArrayList<>();

        while(fileScanner.hasNextLine())
            rowsList.add(fileScanner.nextLine());

        fileScanner.close();

        return rowsList.get(neededRow).split(" ")[neededColumn];
    }

}


