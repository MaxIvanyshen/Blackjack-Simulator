import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Round {

    private Dealer dealer;

    private int bet;

    private Player player;

    private Result result;

    private Deck deck;

    private boolean roundIsOn;

    public Round() {
    }

    public Round(Dealer dealer, Player player) {
        setDealer(dealer);
        setPlayer(player);
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Player getPlayer() {
        return player;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Result determineResult(Hand playerHand, Hand dealerHand) {

        boolean playerCountInBounds = playerHand.getCount() <= 21;
        boolean dealerCountInBounds = dealerHand.getCount() <= 21;

        if(playerCountInBounds && !dealerCountInBounds)
            return Result.PLAYER;
        else if(!playerCountInBounds && dealerCountInBounds)
            return Result.DEALER;

        if(playerHand.getCount() > dealerHand.getCount())
            return Result.PLAYER;
        else if(dealerHand.getCount() > playerHand.getCount())
            return Result.DEALER;

        return Result.PUSH;
    }

    public void checkCounts(Hand playerHand, Hand dealerHand) {

        boolean blackjackPlayer = (playerHand.getCount() == 21 && playerHand.getCardsNumber() == 2);
        boolean blackjackDealer = (dealerHand.getCount() == 21 && dealerHand.getCardsNumber() == 2);

        if(playerHand.getCount() > 21)
            setResult(Result.DEALER);
        else if(dealerHand.getCount() > 21)
            setResult(Result.PLAYER);

        if(blackjackPlayer)
            setResult(determineResult(playerHand, dealerHand));
        else if(blackjackDealer)
            setResult(determineResult(playerHand, dealerHand));

    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public void hit(Hand hand) {
        hand.add(deck.pop());
    }

    public void deal(Gambler gambler) {
        if(gambler instanceof Dealer)
            dealToDealer(gambler.getHand());
        else
            dealToPlayer(gambler.getHand());
    }

    private void dealToPlayer(Hand playerHand) {
        playerHand.add(deck.pop());
        playerHand.add(deck.pop());
    }

    private void dealToDealer(Hand dealerHand) {
        Card revealedCard = deck.pop();
        dealerHand.add(revealedCard);

        Card hiddenCard = deck.pop();
        hiddenCard.hide();
        dealerHand.add(hiddenCard);
    }

    public void play() {

        roundIsOn = true;

        while(roundIsOn) {
            checkCounts(player.getHand(), dealer.getHand());

            if(getResult() != null)
                break;

            if(!roundIsOn)
                break;
        }
    }

    private void operateDealerAction() {

        String dealerAction = dealer.getAction();

        if(dealerAction.equals("S"))  {
            setResult(determineResult(player.getHand(), dealer.getHand()));
            roundIsOn = false;
        }
        else {
            hit(dealer.getHand());
            checkCounts(player.getHand(), dealer.getHand());
        }
    }

    private void operateActions() {
        String playerAction = player.getAction(dealer.getHand());

        if(playerAction.equals("S"))
            operateDealerAction();

        else if(playerAction.equals("H")) {
            hit(player.getHand());
            checkCounts(player.getHand(), dealer.getHand());
        }

        else if(playerAction.equals("D")) {
            hit(player.getHand());
            checkCounts(player.getHand(), dealer.getHand());

            operateDealerAction();
        }
    }
}
