import org.junit.jupiter.api.Test;

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
        assertThat(r.getPlayer().getMoney()).isEqualTo(10100);
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
        assertThat(r.getPlayer().getMoney()).isEqualTo(9900);
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
        assertThat(r.getPlayer().getMoney()).isEqualTo(10150);
    }

    @Test
    public void playPush() {
        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("AH"))),
                new Player(new Hand(new Card("AH"), new Card("JS"))));

        r.setDeck(new Deck());
        r.getDeck().createDeck(1);
        r.getDeck().shuffle();
        r.play();

        assertThat(r.getResult()).isEqualTo(Result.PUSH);
    }
}
