package interactors;

import simulator.*;

public class RoundInteractor {

    private Round round;
    public Round newRound() {
        this.round = new Round();
        return getRound();
    }

    public void standardDeck() {
        Deck standardDeck = new Deck();
        standardDeck.createDeck(6);
        round.setDeck(standardDeck);
    }

    public Round getRound() {
        return round;
    }

    public void runRound() {
        round.setPlayer(new Player(new Hand()));
        round.setDealer(new Dealer(new Hand()));
        round.deal(round.getPlayer());
        round.deal(round.getDealer());
        round.play();
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public void newDeck(int decksNumber) {
        Deck customDeck = new Deck();
        customDeck.createDeck(decksNumber);
        round.setDeck(customDeck);
    }
}
