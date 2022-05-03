import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundTest {

    @Test
    public void createRoundWithEmptyConstructor() {
        Round r = new Round();
        assertThat(r.getPlayers().size()).isEqualTo(0);
    }

    @Test
    public void createRoundWithOnePlayer() {
        Round r = new Round(new Dealer(), new Player());
        assertThat(r.getDealer()).isNotEqualTo(null);
        assertThat(r.getPlayers().size()).isEqualTo(1);
    }

    @Test
    public void createRoundWith2Players() {
        Round r = new Round(new Dealer(), new Player(), new Player());
        assertThat(r.getDealer()).isNotEqualTo(null);
        assertThat(r.getPlayers().size()).isEqualTo(2);
    }

    @Test
    public void createRoundWith3Players() {
        Round r = new Round(new Dealer(), new Player(), new Player(), new Player());
        assertThat(r.getDealer()).isNotEqualTo(null);
        assertThat(r.getPlayers().size()).isEqualTo(3);
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
        assertThat(r.checkCounts(playersHand, dealersHand)).isEqualTo(Result.PLAYER);
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

//    @Test
//    public void play() {
//        Round r = new Round(new Dealer(new Hand(new Card("10H"), new Card("8H"))),
//                new Player(new Hand(new Card("AH"), new Card("7S"))));
//
//        r.play();
//
//        assertThat(r.getResult()).isEqualTo(Result.PUSH);
//    }
}
