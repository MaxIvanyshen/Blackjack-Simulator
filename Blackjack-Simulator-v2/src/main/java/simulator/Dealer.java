package simulator;

public class Dealer implements Gambler {

    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Dealer(Hand hand) {
        this.hand = hand;
    }

    @Override
    public Hand getHand() {
        return hand;
    }

    @Deprecated
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public String getAction(Hand... opponentHand) {
        if(hand.getCount() >= 17)
            return "S";
        return "H";
    }
}
