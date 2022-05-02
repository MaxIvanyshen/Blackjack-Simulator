import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandTest {

    @Test
    public void createNewHandWithEmptyConstructor() {
        Hand hand = new Hand();

        hand.add(new Card("AH"));
        hand.add(new Card("10H"));

        assertThat(hand.get(1).getCard()).isEqualTo("AH");
        assertThat(hand.get(2).getCard()).isEqualTo("10H");
    }

    @Test
    public void createNewHandWithPassingCardsToTheConstructor() {
        Hand hand = new Hand(new Card("AH"), new Card("10H"));

        assertThat(hand.get(1).getCard()).isEqualTo("AH");
        assertThat(hand.get(2).getCard()).isEqualTo("10H");
    }

    @Test
    public void createNewHandWithPassingListOfCardsToTheConstructor() {
        List<Card> cards = new ArrayList<>(Arrays.asList(new Card("AH"), new Card("10H")));
        Hand hand = new Hand(cards);

        assertThat(hand.get(1).getCard()).isEqualTo("AH");
        assertThat(hand.get(2).getCard()).isEqualTo("10H");
    }

    @Test
    public void getCountOfTheHand() {
        Hand hand = new Hand(new Card("AH"), new Card("5S"));
        assertThat(hand.getCount()).isEqualTo(16);
    }

    @Test
    public void getCountOfTheHandWith3Cards() {
        Hand hand = new Hand(new Card("AH"), new Card("AS"));
        hand.add(new Card("8H"));
        assertThat(hand.getCount()).isEqualTo(20);
    }

    @Test
    public void getCountOfTheHandWith2Aces() {
        Hand hand = new Hand(new Card("AH"), new Card("AS"));
        assertThat(hand.getCount()).isEqualTo(12);
    }

    @Test
    public void getCardsNumberInHandOf2() {
        Hand hand = new Hand(new Card("AH"), new Card("AS"));
        assertThat(hand.getCardsNumber()).isEqualTo(2);
    }

    @Test public void getCardsNumberInHandOf3() {
        Hand hand = new Hand(new Card("AH"), new Card("AS"));
        hand.add(new Card("3H"));
        assertThat(hand.getCardsNumber()).isEqualTo(3);
    }
}
