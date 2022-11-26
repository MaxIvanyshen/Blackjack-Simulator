package interactors;

import models.RoundRequestModel;
import models.RoundResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulator.*;

import javax.net.ssl.SNIHostName;

import static org.junit.jupiter.api.Assertions.*;

public class RoundInteractorTest {

    private RoundInteractor interactor;

    @BeforeEach
    public void setUp() {
        this.interactor = new RoundInteractor();
    }

    @Test
    public void testCreatingAnEmptyRoundWithInteractor() throws Exception {
        assertNotNull(interactor.newRound());
    }

    @Test
    public void testCreatingWithStandartDeckForTheRound() throws Exception {
        interactor.newRound();
        interactor.standardDeck();
        assertNotNull(interactor.getRound().getDeck());
        assertEquals(6, interactor.getRound().getDeck().getSizeInDecks());
    }

    @Test
    public void testCreatingCustomDeckForTheRound() throws Exception {
        interactor.newRound();
        interactor.newDeck(4);
        assertEquals(4, interactor.getRound().getDeck().getSizeInDecks());
    }

    @Test
    public void testRunningRoundWithInteractor() throws Exception {
        interactor.newRound();
        interactor.standardDeck();
        interactor.setNewPlayer();
        interactor.setNewDealer();
        interactor.runRound();
        assertNotNull(interactor.getRound().getResult());
    }

    @Test
    public void testSettingRoundToInteractor() throws Exception {
        interactor.setRound(new Round(new Dealer(new Hand(new Card("8H"), new Card("9H"))),
                new Player(new Hand(new Card("10H"), new Card("8S")))));
        interactor.standardDeck();
        interactor.runRound();
        assertEquals(Result.PLAYER, interactor.getRound().getResult());
    }

    @Test
    public void testSendingRequestModelAndReceivingResponseModel() throws Exception {
        RoundRequestModel reqModel = new RoundRequestModel();
        Deck deck = new Deck();
        deck.createDeck(6);
        reqModel.round = new Round(new Dealer(new Hand()), new Player(new Hand()), deck, 100);
        RoundResponseModel resModel = interactor.run(reqModel);

        assertEquals(interactor.getRound().getDeck().getSizeInCards(), resModel.round.getDeck().getSizeInCards());
        assertNotNull(resModel.round.getResult());
    }

}
