import interactors.RoundInteractor;
import models.RoundRequestModel;
import models.RoundResponseModel;
import simulator.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RoundRequestModel reqModel = new RoundRequestModel();
        Deck deck = new Deck();
        deck.createDeck(6);
        deck.shuffle();
        Player player = new Player(new Hand());
        String strategyPath = "";


        player.setStrategyFile("./strategy.txt");

        reqModel.round = new Round(new Dealer(new Hand()), player, deck, 100);

//        RoundInteractor interactor = new RoundInteractor();
//
//        RoundResponseModel resModel = interactor.run(reqModel);
//
//        System.out.println(resModel.round.getResult());

        reqModel.round.deal(reqModel.round.getPlayer());
        reqModel.round.deal(reqModel.round.getDealer());
        reqModel.round.play();
        System.out.println(reqModel.round.getResult());
    }
}
