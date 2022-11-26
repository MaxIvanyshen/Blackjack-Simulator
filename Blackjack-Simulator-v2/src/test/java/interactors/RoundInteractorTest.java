package interactors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simulator.*;

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

}
