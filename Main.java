
public class Main {

  public static int winCount = 0;
  public static int loseCount = 0;

  private static int roundCount = 0;
  private static int maxCount = 0;

  private static Player p = new Player();
  private static Dealer d = new Dealer(p);

  public static Winner lastWinner = Winner.DEALER; 

  public static void main(String[] args) {
    p.setDealer(d);

    maxCount = 10;
    for(int i = 0; i < 50; i++) {
      try {
        if(p.money <= 0) {
          System.out.println("LOST ALL THE MONEY");
          break;
        }
        if(lastWinner == Winner.PLAYER) {
          p.bet += 100;
        }
        else if(lastWinner == Winner.DEALER) {
          p.bet = 100;
        }
        d.game();
      } catch (Exception e) {
        continue;
      }
    }
  }

  public static void roundEnded() {
    roundCount++;
    if (roundCount < maxCount) {
      d.game();
    } else {
      // d.roundIsOn = false;
      printSummary();
    }
  }

  public static void printSummary() {
    System.out.println("Player won: " + winCount);
    System.out.println("Dealer won: " + loseCount);
  }
}
