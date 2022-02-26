import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Player implements Person {
  public static int count = 0;

  public List<Card> hand = new ArrayList<Card>();

  private List<String> strat = new ArrayList<String>();

  {
    try {
      File file = new File("./strategy.txt");
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        strat.add(sc.nextLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Dealer dealer;

  public Player() {
  }

  public Player(Dealer dealer) {
    this.dealer = dealer;
  }

  public void setDealer(Dealer dealer) {
    this.dealer = dealer;
  }

  public void hit() {
    dealer.hit();
  }

  public void stand() {
    dealer.playerStanding();
  }

  public void doubleHand() {
    hit();
    stand();
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

  private String getAction(int key) throws Exception {
    int i = 0;
    Card c = dealer.hand.get(0);
    String action = "S";

    // System.out.println("Current count: " + this.count);

    System.out.println(this.strat.get(key));
    String[] arr = this.strat.get(key).split(" ");
    try {
      int a = Integer.parseInt(c.getValue());
      // System.out.println("dealer's card value: " + a);
      // switch (a) {
      // case 2:
      // action = arr[1];
      // case 3:
      // action = arr[2];
      // case 4:
      // action = arr[3];
      // case 5:
      // action = arr[4];
      // case 6:
      // action = arr[5];

      // case 7:
      // action = arr[6];
      // case 8:
      // action = arr[7];
      // case 9:
      // action = arr[8];
      // }

      action = arr[a - 1];
    } catch (NumberFormatException e) {
      if (c.getValue().equals("A")) {
        action = arr[10];
      } else {
        action = arr[9];
      }
    }

    // System.out.println(arr[9]);
    System.out.println(action);
    return action;
  }

  public String decide() throws Exception {

    try {
      if (this.hand.size() == 2) {
        if (this.hand.get(0).getValue().equals(this.hand.get(1).getValue())) {
          // write what to do if 2 cards are equal
        }
        if ((this.hand.get(0).getValue().equals("A") || this.hand.get(1).getValue().equals("A")) && this.count <= 18) {
          // write what to do if one card if Ace and count is less or equal 18
        }
      }
      if (count < 9)
        return "H";
      else if (count == 9)
        return getAction(2);
      else if (count == 10)
        return getAction(3);
      else if (count == 11)
        return getAction(4);
      else if (count == 12)
        return getAction(5);
      else if (count == 13)
        return getAction(6);
      else if (count == 14)
        return getAction(7);
      else if (count == 15)
        return getAction(8);
      else if (count == 16)
        return getAction(9);
      else if (count >= 17)
        return "S";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
