import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import java.util.Collections;
import java.lang.NumberFormatException;
import java.util.Random;

public class Dealer implements Person {
  public static int dealerWinner = 0;
  public static int playerWinner = 0;
  public int count = 0;
  private int cardNumber = 0;
  private int round = 0;
  private int delimiterPosition;
  public boolean roundIsOn = false;

  private boolean doubled = false;

  public Winner result;

  private List<Deck> stack;
  private Stack<Card> deck;

  public List<Card> hand = new ArrayList<Card>();

  public Player player;

  public Dealer() {
    stack = new ArrayList<>();
    for (int i = 0; i < 6; ++i) {
      stack.add(new Deck());
    }
  }

  public Dealer(Player p) {
    stack = new ArrayList<>();
    for (int i = 0; i < 6; ++i) {
      stack.add(new Deck());
    }
    this.player = p;
    p.setDealer(this);
  }

  public List<Deck> getStack() {
    return this.stack;
  }

  public void game() {
    System.out.println("ROUND STARTED!");
    round++;
    roundIsOn = true;
    if (round == 1)
      shuffle();
    if (roundIsOn) {

      deal();
      System.out.println("Dealer: ");
      printHand();
      System.out.println("Player: ");
      player.printHand();
    }

    round();
  }

  public void round() {
    while (roundIsOn) {
      String s = askPlayer();
      if (s.equals("H")) {
        hit();
        player.countPoints();
        checkCount();
      } else if (s.equals("D")) {
        doubled = true;
        player.bet *= 2;
        hit();
        player.countPoints();
        checkCount();
        roundIsOn = false;
        detectWinner();
      } else if (s.equals("SP")) {
        // playerSplitting();
        System.out.println("SPlit");
        roundIsOn = false;
      } else if (s.equals("S")) {
        playerStanding();
        roundIsOn = false;
      }
    }
    return;
  }

  public void shuffle() {
    this.deck = new Stack<>();
    for (int i = 0; i < 6; ++i) {
      Collections.shuffle(stack.get(i).getDeck());
    }
    for (int i = 0; i < stack.size(); ++i) {
      Deck deck = stack.get(i);
      for (int j = 0; j < deck.getDeck().size(); ++j) {
        this.deck.push(deck.getDeck().get(j));
      }
    }
    Random r = new Random();
    delimiterPosition = 270 + r.nextInt(16);
    deck.insertElementAt(new Card(true), delimiterPosition);
  }

  public void setPlayer(Player p) {
    this.player = p;
  }

  public void hit() {
    Card c = this.deck.pop();
    player.hand.add(c);
    player.countPoints();
    System.out.println("Player's hand after hit: ");
    player.printHand();

    checkCount();
  }

  public void oneMore() {
    this.hand.add(this.deck.pop());

    // System.out.println("Person: ");
    // p.printHand();

    System.out.println("Dealer's hand after hit: ");
    printHand();

    countPoints();
    checkCount();
  }

  public void deal() {
    if (delimiterPosition - cardNumber <= 10) {
      shuffle();
    }
    player.hand.add(this.deck.pop());
    cardNumber++;
    this.hand.add(this.deck.pop());
    cardNumber++;
    player.hand.add(this.deck.pop());
    cardNumber++;
    Card hiddenCard = this.deck.pop();
    hiddenCard.hide();
    this.hand.add(hiddenCard);
    cardNumber++;

    countPoints();
    player.countPoints();

    // System.out.println("Dealer's hand: ");
    // printHand();
    // System.out.println("Player's hand: ");
    // player.printHand();
    checkCount();
  }

  public void checkCount() {
    // System.out.println("Array size: " + this.hand.size());
    if (this.hand.get(1).hidden) {
      if (count == 10) {
        if (this.hand.get(1).getValue().equals("A")) {
          if (player.hand.size() == 2 && player.count == 21) {
            result = Winner.PUSH;
            roundIsOn = false;
            endRound();
          } else {
            System.out.println("BLACKJACK");
            result = Winner.DEALER;
            roundIsOn = false;
            endRound();
          }
        }
      }
      // System.out.println("Player's count: " + player.count);
      if (player.hand.size() == 2 && player.count == 21) {
        System.out.println("BLACKJACK");
        player.bet *= 1.5;
        result = Winner.PLAYER;
        roundIsOn = false;
        endRound();
      } else if (player.count > 21) {
        result = Winner.DEALER;
        roundIsOn = false;
        endRound();
      } else
        return;
    } else {
      // System.out.println("Player's count: " + player.count);
      // System.out.println("Dealer's count: " + this.count);
      if (player.count > 21) {
        result = Winner.DEALER;
        endRound();
      } else if (count > 21) {
        result = Winner.PLAYER;
        endRound();
      } else
        return;
    }

  }

  public void playerStanding() {
    this.hand.get(1).show();
    countPoints();
    if (count < 17) {
      oneMore();
      countPoints();
      checkCount();
      playerStanding();
    } else if (count > 17) {
      countPoints();
      checkCount();
      detectWinner();
    }
  }

  @Override
  public void printHand() {
    for (int i = 0; i < this.hand.size(); ++i) {
      System.out.print(this.hand.get(i).getCard() + " ");
    }
    System.out.print("\n");
  }

  @Override
  public void countPoints() {
    int c = 0;
    for (int i = 0; i < this.hand.size(); ++i) {
      if (this.hand.get(i).hidden) {
        continue;
      }
      try {
        int a = Integer.parseInt(this.hand.get(i).getValue());
        c += a;
      } catch (NumberFormatException e) {
        if (this.hand.get(i).getValue().equals("A")) {
          c += 11;
        } else {
          c += 10;
        }
      }
    }
    if (c > 21) {
      for (int i = 0; i < this.hand.size(); ++i) {
        if (this.hand.get(i).getValue().equals("A")) {
          c -= 10;
          if (c < 21)
            continue;
        }
      }
    }

    count = c;
  }

  private void endRound() {

    if(result == Winner.PLAYER) {
      player.money += player.bet;
    }
    else if(result == Winner.DEALER) {
      player.money -= player.bet;
    }

    printResult();
    // System.out.println("size before clear: " + this.hand.size());
    this.hand.clear();
    // System.out.println("size after clear: " + this.hand.size());
    player.hand.clear();
    player.count = 0;
    count = 0;
    roundIsOn = false;
    // Main.printSummary();
  
    if(doubled) {
      player.bet /= 2;
    }

    Main.lastWinner = result;

    System.out.println("ROUND ENDED");

    System.out.println("Player's money: " + player.money);
    // System.exit(0);
    // Main.roundEnded();
    // System.exit(0);
  }

  public void detectWinner() {
    if (count > player.count)
      result = Winner.DEALER;
    if (count < player.count)
      result = Winner.PLAYER;
    if (count == player.count)
      result = Winner.PUSH;
    endRound();
  }

  public String askPlayer() {
    String str = "";
    try {

      str = player.decide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  public void printResult() {

    if (result == Winner.PLAYER)
      Main.winCount++;
    if (result == Winner.DEALER)
      Main.loseCount++;
    System.out.println(result);
  }

}
