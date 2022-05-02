import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cardsInHandList;

    {
        cardsInHandList = new ArrayList<>();
    }

    public Hand() {}

    public Hand(Card first, Card second) {
        cardsInHandList.add(first);
        cardsInHandList.add(second);
    }

    public Hand(List<Card> hand) {
        this.cardsInHandList = hand;
    }

    public void add(Card c) {
        cardsInHandList.add(c);
    }

    public Card get(int cardNumber) {
        return cardsInHandList.get(cardNumber-1);
    }

    public int getCount() {
        int count = 0;

        for(int i = 0; i < cardsInHandList.size(); i++) {
            count += cardsInHandList.get(i).getCount(count);
        }

        return count;
//        return cardsInHandList.stream().mapToInt(card -> card.getCount()).sum();
    }

    public int getCardsNumber() {
        return cardsInHandList.size();
    }

    public boolean oneCardIsAce() {
        return get(1).getValue().equals("A") || get(2).getValue().equals("A");
    }


    public boolean cardsAreEqual() {
        return get(1).getValue().equals(get(2).getValue());
    }
}
