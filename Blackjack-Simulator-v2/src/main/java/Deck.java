import java.awt.image.CropImageFilter;
import java.util.*;

public class Deck {

    private Stack<Card> deck;

    private Card placeholder = new DeckPlaceholder();

    private int placeholderPosition;

    private boolean shuffled;

    private List<String> cardsList = new ArrayList<>(
            Arrays.asList("2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS",
                    "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH",
                    "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC",
                    "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD"));

    public Deck() {
        this.deck = new Stack<Card>();
    }

    public Deck(Stack<Card> deck) {
        this.deck = deck;
    }

    public Card pop() {
        if(deck.size() <= getPlaceholderPosition())
            shuffle();
        return deck.pop();
    }

    public Card peek() {
        return deck.peek();
    }
    public int getSizeInCards() {
        if(shuffled)
            return deck.size() - 1;
        return deck.size();
    }

    public int getSizeInDecks() {
        return getSizeInCards() / 52;
    }

    public int getShuffledDeckSize() {
        return getSizeInCards() + 1;
    }

    public void createDeck(int decksNumber) {
        for(int i = 0; i < decksNumber; i++)
            cardsList.stream().forEach(card -> deck.push(new Card(card)));
    }

    public void shuffle() {
        Collections.shuffle(deck);
        int placeholderMinPosition = Math.round((float)getSizeInCards() / 100 * 65);
        int placeholderMaxPosition = Math.round((float)getSizeInCards() / 100 * 95);
        placeholderPosition = new Random().nextInt(placeholderMinPosition, placeholderMaxPosition);
        deck.insertElementAt(placeholder, placeholderPosition);
        shuffled = true;
    }

    public int getPlaceholderPosition() {
        return placeholderPosition;
    }

    public boolean isEmpty() {
        return deck.size() == 0;
    }
}
