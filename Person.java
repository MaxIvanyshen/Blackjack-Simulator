import java.util.List;
import java.util.ArrayList;

public interface Person {
  public List<Card> hand = new ArrayList<>();

  public void printHand();

  public void countPoints();
}
