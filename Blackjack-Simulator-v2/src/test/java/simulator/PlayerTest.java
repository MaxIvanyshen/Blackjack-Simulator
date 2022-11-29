package simulator;

import org.junit.jupiter.api.Test;
import simulator.Card;
import simulator.Gambler;
import simulator.Hand;
import simulator.Player;

import static org.assertj.core.api.Assertions.*;

public class PlayerTest {

    @Test
    public void createPlayerWithEmptyHand() {
        Gambler p = new Player();
        assertThat(((Player) p).getMoney()).isEqualTo(1000);
        assertThat(p.getHand().getCardsNumber()).isEqualTo(0);
    }

    @Test
    public void createPlayerWithHand() {
        Gambler p = new Player(new Hand(new Card("AH"), new Card("10S")));
        assertThat(p.getHand().getCardsNumber()).isEqualTo(2);
        assertThat(p.getHand().getCount()).isEqualTo(21);
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
        Player p = new Player(new Hand(new Card("2H"), new Card("10D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

    @Test
    public void getActionWhenCountIsHigherThan8AndLowerThan17_2() {
        Gambler p = new Player(new Hand(new Card("10H"), new Card("7H")));
        Hand dealersHand = new Hand(new Card("9H"), new Card("JS"));
        assertThat(p.getAction(dealersHand)).isEqualTo("S");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreDifferentAndThereIsNoAce() {
        Player p = new Player(new Hand(new Card("9H"), new Card("2D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("D");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreDifferentAndThereIsAce() {
        Player p = new Player(new Hand(new Card("AH"), new Card("9D")));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("S");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreEqual() {
        Player p = new Player(new Hand(new Card("8H"), new Card("8D")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("SP");
    }

    @Test
    public void getActionWrittenInStrategyWhenCardsAreAces() {
        Player p = new Player(new Hand(new Card("AH"), new Card("AD")));
        Hand dealersHand = new Hand(new Card("2H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("SP");
    }

    @Test
    public void getActionWith3Cards() {
        Player p = new Player(new Hand(new Card("6H"), new Card("2D")));
        p.getHand().add(new Card("2H"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }

    @Test
    public void getActionWith4Cards() {
        Player p = new Player(new Hand(new Card("6H"), new Card("2D")));
        p.getHand().add(new Card("2H"));
        p.getHand().add(new Card("6S"));
        Hand dealersHand = new Hand(new Card("10H"), new Card("4H"));
        p.setStrategyFile("./strategy.txt");
        assertThat(p.getAction(dealersHand)).isEqualTo("H");
    }
}
