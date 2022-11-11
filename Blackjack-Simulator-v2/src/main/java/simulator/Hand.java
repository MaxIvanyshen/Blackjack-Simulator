package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

    private List<Card> cardsInHandList;

    {
        cardsInHandList = new ArrayList<>();
    }

    public Hand() {}

    public Hand(Card... cards) {
        Collections.addAll(cardsInHandList, cards);
    }

    public Hand(List<Card> hand) {
        this.cardsInHandList = hand;
    }

    public void add(Card c) {
        cardsInHandList.add(c);
    }

    public Card get(int cardNumber) {
        return cardsInHandList.get(cardNumber - 1);
    }

    public int getCount() {
        int count = 0;

        for(int i = 0; i < cardsInHandList.size(); i++)
            count += cardsInHandList.get(i).getCount(count);

        if(count > 21) {
            for(int i = 0; i < cardsInHandList.size(); i++) {
                if (cardsInHandList.get(i).getValue().equals("A")) {
                    count -= 10;
                    break;
                }
            }
        }

        return count;
    }

    public int getCardsNumber() {
        return cardsInHandList.size();
    }

    public void clear() {cardsInHandList.clear();}

    public boolean oneCardIsAce() {
        return get(1).getValue().equals("A") || get(2).getValue().equals("A");
    }


    public boolean cardsAreEqual() {
        return get(1).getValue().equals(get(2).getValue());
    }
}
