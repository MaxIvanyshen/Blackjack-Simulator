package simulator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import simulator.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundTest {

    @Test
    public void createRoundWithEmptyConstructor() {
        Round r = new Round();
    }

    @Test
    public void createRoundWithPlayer() {
        Round r = new Round(new Dealer(), new Player(new Hand(new Card("AH"), new Card("10H"))));
        assertThat(r.getDealer()).isNotEqualTo(null);
        assertThat(r.getPlayer().getHand().get(1).getCard()).isEqualTo("AH");
    }

    @Test
    public void setAndGetBet() {
        Round r = new Round();
        r.setBet(100);
        assertThat(r.getBet()).isEqualTo(100);
    }

    @Test
    public void determineResultPUSH() {
        Round r = new Round();
        Hand playersHand = new Hand(new Card("AH"), new Card("9S"));
        Hand dealersHand = new Hand(new Card("QH"), new Card("KD"));
        assertThat(r.determineResult(playersHand, dealersHand)).isEqualTo(Result.PUSH);
    }

    @Test
    public void determineResultPLAYER() {
        Round r = new Round();
        Hand playersHand = new Hand(new Card("AH"), new Card("9S"));
        Hand dealersHand = new Hand(new Card("8H"), new Card("KD"));
        assertThat(r.determineResult(playersHand, dealersHand)).isEqualTo(Result.PLAYER);
    }

    @Test
    public void determineResultDEALER() {
        Round r = new Round();
        Hand playersHand = new Hand(new Card("AH"), new Card("9S"));
        Hand dealersHand = new Hand(new Card("AH"), new Card("KD"));
        assertThat(r.determineResult(playersHand, dealersHand)).isEqualTo(Result.DEALER);
    }

    @Test
    public void checkCounts() {
        Round r = new Round();
        Hand playersHand = new Hand(new Card("AH"), new Card("9S"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("6H"), new Card("8H"));
        r.checkCounts(playersHand, dealersHand);
        assertThat(r.getResult()).isEqualTo(Result.PLAYER);
    }

    @Test
    public void setResult() {
        Round r = new Round();
        Hand playersHand = new Hand(new Card("AH"), new Card("7S"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("8H"));
        r.setResult(r.determineResult(playersHand, dealersHand));
        assertThat(r.getResult()).isEqualTo(Result.PUSH);
    }

    @Test
    public void deck() {
        Round r = new Round();
        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        assertThat(r.getDeck().peek().getCard()).isNotEqualTo("AD");
    }

    @Test
    public void hit() {
        Round r = new Round();
        Hand hand = new Hand(new Card("2H"), new Card("AS"));
        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.hit(hand);
        assertThat(hand.getCardsNumber()).isEqualTo(3);
    }

    @Test
    public void doubleHit() {
        Round r = new Round();
        Hand hand = new Hand(new Card("2H"), new Card("AS"));
        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.hit(hand);
        r.hit(hand);
        assertThat(hand.getCardsNumber()).isEqualTo(4);
    }

    @Test
    public void deal() {
        Round r = new Round();
        Gambler d = new Dealer(new Hand());
        Gambler p = new Player(new Hand());

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();

        r.deal(d);
        r.deal(p);

        assertThat(d.getHand().getCardsNumber()).isEqualTo(2);
        assertThat(p.getHand().getCardsNumber()).isEqualTo(2);
        assertThat(d.getHand().get(2).isHidden).isTrue();
    }

    @Test
    public void play() {
        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("7H"))),
                new Player(new Hand(new Card("9H"), new Card("JS"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PLAYER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1100);
    }

    @Test
    public void playDealerBlackjack() {
        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("AH"))),
                new Player(new Hand(new Card("7H"), new Card("JS"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.DEALER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(900);
    }

    @Test
    public void playPlayerBlackjack() {
        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("8H"))),
                new Player(new Hand(new Card("AH"), new Card("JS"))));

        r.setDeck(new Deck());
        r.setBet(100);
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PLAYER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1150);
    }

    @Test
    public void playPush() {
        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("7H"))),
                new Player(new Hand(new Card("9H"), new Card("8S"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PUSH);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1000);
    }

    @Disabled
    @Test // not always passes cause dealer may win in the split, though this test in made to pass in most of the times
    public void split() {
        Round r = new Round(new Dealer(new Hand(new Card("9H"), new Card("AH"))),
                new Player(new Hand(new Card("8H"), new Card("8S"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.setBet(100);
        r.play();

        assertThat(r.splitted).isEqualTo(true);
        assertThat(r.getPlayer().getMoney()).isEqualTo(800);
    }

    @Test
    public void twoRounds() {
        Round r = new Round(new Dealer(new Hand(new Card("8H"), new Card("9H"))),
                new Player(new Hand(new Card("10H"), new Card("8S"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PLAYER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1100);

        r.getPlayer().setHand(new Hand(new Card("10H"), new Card("8S")));
        r.getDealer().setHand(new Hand(new Card("AH"), new Card("10H")));
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.DEALER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1000);
    }

    @Test
    public void threeRounds() {
        Round r = new Round(new Dealer(new Hand(new Card("8H"), new Card("9H"))),
                new Player(new Hand(new Card("10H"), new Card("8S"))));


        Deck d = new Deck();
        d.createDeck(6);
        d.shuffle();
        r.setDeck(d);
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PLAYER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1100);

        r = new Round(new Dealer(new Hand(new Card("AH"), new Card("10H"))),
                new Player(new Hand(new Card("10H"), new Card("8S"))));

        r.setDeck(d);
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.DEALER);
        assertThat(r.getPlayer().getMoney()).isEqualTo(900);

        r = new Round(new Dealer(new Hand(new Card("JH"), new Card("7H"))),
                new Player(new Hand(new Card("10H"), new Card("7S"))));

        r.setDeck(d);
        r.setBet(100);
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PUSH);
        assertThat(r.getPlayer().getMoney()).isEqualTo(1000);
    }

    @Test
    public void testDealingToPlayersDuringRound() throws Exception {
        Round r = new Round(new Dealer(new Hand()),
                new Player(new Hand()));

        r.getPlayer().setStrategyFile("./strategy.txt");

        Deck d = new Deck();
        d.createDeck(6);
        d.shuffle();
        r.setDeck(d);
        r.setBet(100);

        r.deal(r.getPlayer());
        r.deal(r.getDealer());

        r.play();
        assertThat(r.getResult()).isInstanceOf(Result.class);
    }

    @Test
    public void testClearingHands() throws Exception {
        Round r = new Round(new Dealer(new Hand()),
                new Player(new Hand()));

        r.getPlayer().setStrategyFile("./strategy.txt");


        Deck d = new Deck();
        d.createDeck(6);
        d.shuffle();
        r.setDeck(d);
        r.setBet(100);

        r.deal(r.getDealer());
        r.deal(r.getPlayer());

        r.play();
        assertThat(r.getResult()).isInstanceOf(Result.class);

        Hand playerHand = r.getPlayer().getHand();
        Hand dealerHand = r.getDealer().getHand();

        r.clearHands();
        r.play();
        for(int i = 0; i < r.getPlayer().getHand().getCardsNumber(); i++) {
            assertThat(r.getPlayer().getHand().get(i+1).getCard()).isNotEqualTo(playerHand.get(i+1).getCard());
        }
        for(int i = 0; i < r.getDealer().getHand().getCardsNumber(); i++) {
            if(!r.getDealer().getHand().get(i+1).getCard().equals("Hidden card"))
                assertThat(r.getDealer().getHand().get(i+1).getCard()).isNotEqualTo(dealerHand.get(i+1).getCard());
        }
    }
}
