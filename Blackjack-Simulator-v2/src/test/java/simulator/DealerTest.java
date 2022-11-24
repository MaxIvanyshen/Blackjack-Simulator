package simulator;

import org.junit.jupiter.api.Test;
import simulator.Card;
import simulator.Dealer;
import simulator.Gambler;
import simulator.Hand;

import static org.assertj.core.api.Assertions.*;

public class DealerTest {

    @Test
    public void getHand() {
        Gambler d = new Dealer(new Hand(new Card("AH"), new Card("10S")));

        assertThat(d.getHand().get(1).getCard()).isEqualTo("AH");
        assertThat(d.getHand().get(2).getCard()).isEqualTo("10S");
    }

    @Test
    public void getActionWhenNeedToHit() {
        Gambler d = new Dealer(new Hand(new Card("6H"), new Card("6S")));
        assertThat(d.getAction()).isEqualTo("H");
    }

    @Test
    public void getActionWhenNeedToStand() {
        Gambler d = new Dealer(new Hand(new Card("10H"), new Card("8S")));
        assertThat(d.getAction()).isEqualTo("S");
    }
}
