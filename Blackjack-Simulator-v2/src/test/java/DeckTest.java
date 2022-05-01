import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class DeckTest {

    private Deck deck = new Deck();

    @Test
    public void getEmptyDeckSizeInCards() {
        assertThat(deck.getSizeInCards()).isEqualTo(0);
    }

    @Test
    public void getOneDeckSizeInCards() {
        deck.createDeck(1);
        assertThat(deck.getSizeInCards()).isEqualTo(52);
    }

    @Test
    public void getTwoDecksSizeInCards() {
        deck.createDeck(2);
        assertThat(deck.getSizeInCards()).isEqualTo(104);
    }

    @Test
    public void getOneDeckSizeInDecks() {
        deck.createDeck(1);
        assertThat(deck.getSizeInDecks()).isEqualTo(1);
    }

    @Test
    public void getTwoDecksSizeInDecks() {
        deck.createDeck(2);
        assertThat(deck.getSizeInDecks()).isEqualTo(2);
    }

    @Test
    public void shuffleOneDeck() {
        deck.createDeck(1);
        deck.shuffle();

        assertThat(deck.peek().getCard()).isNotEqualTo("AD");
    }

    @Test
    public void shuffleTwoDecks() {
        deck.createDeck(2);
        deck.shuffle();

        assertThat(deck.peek().getCard()).isNotEqualTo("AD");
    }

    @Test
    public void shuffleSixDecks() {
        deck.createDeck(6);
        deck.shuffle();

        assertThat(deck.peek().getCard()).isNotEqualTo("AD");
    }

    @Test
    public void pop() {
        deck.createDeck(1);
        deck.pop();
        assertThat(deck.peek().getCard()).isEqualTo("KD");
    }

    @Test
    public void peek() {
        deck.createDeck(1);
        assertThat(deck.peek().getCard()).isEqualTo("AD");
    }

    @Test
    public void getShuffledDeckSize() {
        deck.createDeck(6);
        deck.shuffle();

        assertThat(deck.getShuffledDeckSize()).isEqualTo(313);
    }

    @Test
    public void cardPlaceholderPosition() {
        deck.createDeck(6);
        deck.shuffle();

        int placeholderMinPosition = Math.round((float)deck.getSizeInCards() / 100 * 65);
        int placeholderMaxPosition = Math.round((float)deck.getSizeInCards() / 100 * 95);

        assertThat(deck.getPlaceholderPosition()).isBetween(placeholderMinPosition, placeholderMaxPosition);
    }
}
