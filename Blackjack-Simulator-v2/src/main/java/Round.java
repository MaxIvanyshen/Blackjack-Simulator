import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Round {

    private Dealer dealer;

    private int bet;

    private List<Player> playersList;

    private Result result;

    private Deck deck;

    public Round() {
        this.playersList = new ArrayList<>();
    }

    public Round(Dealer dealer, Player... players) {
        this.playersList = new ArrayList<>();
        setDealer(dealer);
        setPlayers(players);
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public void setPlayers(Player... players) {
        playersList.addAll(Arrays.asList(players));
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return playersList;
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

    public Result checkCounts(Hand playerHand, Hand dealerHand) {

        boolean blackjackPlayer = (playerHand.getCount() == 21 && playerHand.getCardsNumber() == 2);
        boolean blackjackDealer = (dealerHand.getCount() == 21 && dealerHand.getCardsNumber() == 2);

        if(playerHand.getCount() > 21)
            return Result.DEALER;
        else if(dealerHand.getCount() > 21)
            return Result.PLAYER;

        if(blackjackPlayer)
            return Result.PLAYER;
        else if(blackjackDealer)
            return Result.DEALER;

        return null;
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

//    public void play() {
//        boolean roundIsOn = true;
//        while(roundIsOn) {
//            dealer.getAction();
//
//            for(Player p : playersList) {
//                String action = p.getAction();
//
//                Result result = checkCounts(dealer.getHand(), p.getHand());
//                if(result != null) {
//                    setResult(result);
//                    roundIsOn = false;
//                }
//            }
//        }
//    }
}
