import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class PlayerTest {

    @Test
    public void createPlayerWithEmptyHand() {
        Gambler p = new Player();
        assertThat(p.getHand().getCardsNumber()).isEqualTo(0);
    }

    @Test
    public void createPlayerWithHand() {
        Gambler p = new Player(new Hand(new Card("AH"), new Card("10S")));
        assertThat(p.getHand().getCardsNumber()).isEqualTo(2);
    }

    @Test
    public void getHand() {
        Gambler p = new Player(new Hand(new Card("AH"), new Card("10S")));

        assertThat(p.getHand().get(1).getCard()).isEqualTo("AH");
        assertThat(p.getHand().get(2).getCard()).isEqualTo("10S");
    }

    @Test
    public void getActionWhenCountIs17OrHigher() {
        Gambler p = new Player(new Hand(new Card("10H"), new Card("KD")));
        assertThat(p.getAction()).isEqualTo("S");
    }

    @Test
    public void getActionWhenCountIsLowerThan9() {
        Gambler p = new Player(new Hand(new Card("2H"), new Card("3D")));
        assertThat(p.getAction()).isEqualTo("H");
    }

    @Test
    public void getActionWhenCountIsHigherThan8AndLowerThan17() {
        Gambler p = new Player(new Hand(new Card("2H"), new Card("10D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreDifferentAndThereIsNoAce() {
        Gambler p = new Player(new Hand(new Card("9H"), new Card("2D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("D");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreDifferentAndThereIsAce() {
        Gambler p = new Player(new Hand(new Card("AH"), new Card("9D")));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("S");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreEqual() {
        Gambler p = new Player(new Hand(new Card("2H"), new Card("2D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreAces() {
        Gambler p = new Player(new Hand(new Card("AH"), new Card("AD")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("SP");
    }

    @Test
    public void getActionWith3Cards() {
        Gambler p = new Player(new Hand(new Card("6H"), new Card("2D")));
        p.getHand().add(new Card("2H"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

    @Test
    public void getActionWith4Cards() {
        Gambler p = new Player(new Hand(new Card("6H"), new Card("2D")));
        p.getHand().add(new Card("2H"));
        p.getHand().add(new Card("6S"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

}
