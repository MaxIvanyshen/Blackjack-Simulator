import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;

public class Player implements Person {
  public int count = 0;

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
    Card c = dealer.hand.get(0);
    String action = "S";

    System.out.println(this.strat.get(key));
    String[] arr = this.strat.get(key).split(" ");
    try {
      int a = Integer.parseInt(c.getValue());
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
          try {
            int a = Integer.parseInt(this.hand.get(0).getValue());
            return getAction(a + 17);
          } catch (NumberFormatException e) {
            return getAction(27);
          }
        }
        if ((this.hand.get(0).getValue().equals("A") || this.hand.get(1).getValue().equals("A")) && count <= 18) {
          int index = 0;
          if (this.hand.get(0).getValue().equals("A"))
            index = 1;
          if (count >= 13 && count <= 20) {
            return getAction(Integer.parseInt(this.hand.get(index).getValue()) + 9);
          }
        }
      }

      if (count < 9)
        return "H";
      else if (count >= 9 && count < 17)
        return getAction(count - 7);
      else
        return "S";

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
