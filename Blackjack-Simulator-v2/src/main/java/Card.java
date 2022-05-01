import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Card {

    private String card;

    private List<String> cardsList = new ArrayList<>(
            Arrays.asList("2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS",
            "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH",
            "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC",
            "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD"));

    public Card() {

        Random r = new Random();

        this.card = cardsList.get(r.nextInt(52));
    }

    public Card(String card) {
        if(cardsList.contains(card))
            this.card = card;
        else
            throw new RuntimeException("Not a card");
    }

    public String getCard() {
        return card;
    }


    public char getSuit() {
        boolean is10 = card.length() == 3;
        if(is10)
            return card.toCharArray()[2]; // {'1', '0', 'H'}
        return card.toCharArray()[1];     // {'3', 'H'}
    }

    public String getValue() {
        boolean is10 = card.length() == 3;
        if(is10)
            return card.substring(0, 2); // {'1', '0', 'H'}

        return card.substring(0, 1);     // {'3', 'H'}
    }

    public int getCount() {

        boolean is10 = card.length() == 3;
        boolean isAce = card.toCharArray()[0] == 'A';

        if(is10) {
            return Integer.parseInt(getValue());
        }

        int numberValue = 0;

        try {

            numberValue = Integer.parseInt(getValue());

        } catch(NumberFormatException e) {

            if(isAce) {
                return 11;
            }

            return 10;
        }

        return numberValue;
    }

    public int getCount(int handCount) {
        boolean isAce = card.toCharArray()[0] == 'A';
        if(!isAce) {
            return getCount();
        }
        if(handCount > 10) {
            return 1;
        }
        return 11;
    }

}
