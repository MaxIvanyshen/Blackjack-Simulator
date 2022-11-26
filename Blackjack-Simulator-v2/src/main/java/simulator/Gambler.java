package simulator;

public interface Gambler {

    public Hand getHand();

    public String getAction(Hand... opponentHand);

    public void clearHand();

}
