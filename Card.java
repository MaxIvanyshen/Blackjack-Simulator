public class Card {
  private char suit;
  private String value="";
  public boolean hidden = false;
  public boolean delimiter = false;

  public String getCard() {
    if(hidden) {
      return "Hidden Card";
    }
    else {
      String card = "";
      card+=this.value;
      card+=this.suit;
      return card;
    }
  }

  public void delimiter() {
    this.delimiter = true;
  }

  public Card(boolean d) {
    delimiter = d;
  }

  public Card(String card) {
    if(card.length()==3) {
      this.suit = card.charAt(2);
      this.value += card.split("")[0]+card.split("")[1];
    }
    else {
      this.suit = card.charAt(1);
      this.value+=card.charAt(0);
    }
  }

  public String getValue() {
    return this.value;
  }

  public char getSuit() {
    return this.suit;
  }

  public void hide() {
    hidden = true;
  }

  public void show() {
    hidden = false;
  }
}
