package interactors;

import models.RoundRequestModel;
import models.RoundResponseModel;
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

    public void setNewPlayer() {
        round.setPlayer(new Player(new Hand()));
    }

    public void setNewDealer() {
        round.setDealer(new Dealer(new Hand()));
    }

    public void runRound() {
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

    public RoundResponseModel run(RoundRequestModel reqModel) {
        round = reqModel.round;
        runRound();
        return new RoundResponseModel(round.getResult(), round.getDeck(), round.getPlayer());
    }
}
