package ua.ivanyshen.blackjacksimulator;

import interactors.RoundInteractor;
import lombok.NoArgsConstructor;
import models.RoundRequestModel;
import models.RoundResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import simulator.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class MainController {

    @Value("classpath:strategy.txt")
    Resource strategyFile;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<RoundResponseModel> index() {
        RoundRequestModel reqModel = new RoundRequestModel();
        Deck deck = new Deck();
        deck.createDeck(6);
        deck.shuffle();
        Player player = new Player(new Hand());
        String strategyPath = "";
        try {
            strategyPath = strategyFile.getFile().getPath();
            System.out.println("/home/max/Documents/Coding/Projects/Blackjack-Simulator/blackjack-simulator-backend/target/classes/strategy.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.setStrategyFile(strategyPath);
        reqModel.round = new Round(new Dealer(new Hand()), player, deck, 100);

        RoundInteractor interactor = new RoundInteractor();

        RoundResponseModel resModel = interactor.run(reqModel);
        return ResponseEntity.ok(resModel);
    }

}
