import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {
    
    private Card initializedCard(String card) {
        if(card != null)
            return new Card(card);
        return new Card();
    }
    
    @Test
    public void createNewRandomCard() {
        Card c = initializedCard(null);

        assertThat(c.getCard()).isNotEqualTo(null);
    }

    @Test
    public void createExistingCard() {
        Card c = initializedCard("2H");

        assertThat(c.getCard()).isEqualTo("2H");
    }

    @Test
    public void createNotExistingCard() {

        String notExistingCard = "11H";

        Exception e = assertThrows(RuntimeException.class, () -> {
            Card c = initializedCard(notExistingCard);
        });

        String expectedMessage = "Not a card";
        String actualMessage = e.getMessage();

        assertThat(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getSuitOf10() {
        Card c = initializedCard("10H");

        assertThat(c.getSuit()).isEqualTo('H');
    }

    @Test
    public void getSuitOfAnotherCard() {
        Card c = initializedCard("2H");

        assertThat(c.getSuit()).isEqualTo('H');
    }

    @Test
    public void getValueOf10() {
        Card c = initializedCard("10S");

        assertThat(c.getValue()).isEqualTo("10");
    }

    @Test
    public void getValueOfAnotherCard() {
        Card c = initializedCard("3H");

        assertThat(c.getValue()).isEqualTo("3");
    }

    @Test
    public void getCountOfCardBefore10() {
        Card c = initializedCard("9H");

        assertThat(c.getCount()).isEqualTo(9);
    }

    @Test
    public void getCountOf10() {
        Card c = initializedCard("10H");

        assertThat(c.getCount()).isEqualTo(10);
    }

    @Test
    public void getCountOfCharacterCards() {
        Card c = initializedCard("JH");

        assertThat(c.getCount()).isEqualTo(10);
    }

    @Test
    public void getCountOfAce() {
        Card c = initializedCard("AH");

        assertThat(c.getCount()).isEqualTo(11);
    }

    @Test
    public void getCountOfAceIfHandCountIsGreaterThan10() {
        Card c = initializedCard("AH");

        assertThat(c.getCount(11)).isEqualTo(1);
    }

}
