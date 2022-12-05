import interactors.RoundInteractor;
import models.RoundRequestModel;
import models.RoundResponseModel;
import simulator.*;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        RoundRequestModel reqModel = new RoundRequestModel();
        Deck deck = new Deck();
        deck.createDeck(6);
        deck.shuffle();
        Player player = new Player(new Hand());
        String filePath = "./strategy.txt";

        System.out.println(filePath);
        player.setStrategyFile(filePath);

        reqModel.round = new Round(new Dealer(new Hand()), player, deck, 100);

        RoundInteractor interactor = new RoundInteractor();

        RoundResponseModel resModel = interactor.run(reqModel);
        System.out.println(resModel.round.getResult());
    }
}
