package simulator;

public class Round {

    private Dealer dealer;

    private int bet;

    private Player player;

    private Result result;

    private Deck deck;

    private boolean roundIsOn;

    public boolean splitted;

    public boolean blackjackPlayer;
    public boolean blackjackDealer;

    public boolean doubled;

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

        result = Result.PUSH;

        boolean playerCountInBounds = playerHand.getCount() <= 21;
        boolean dealerCountInBounds = dealerHand.getCount() <= 21;

        if(playerCountInBounds && !dealerCountInBounds)
            result = Result.PLAYER;
        else if(!playerCountInBounds && dealerCountInBounds)
            result = Result.DEALER;

        if(playerHand.getCount() > dealerHand.getCount())
            result = Result.PLAYER;
        else if(dealerHand.getCount() > playerHand.getCount())
            result = Result.DEALER;

        return result;
    }

    public void checkCounts(Hand playerHand, Hand dealerHand) {

        blackjackPlayer = (playerHand.getCount() == 21 && playerHand.getCardsNumber() == 2);
        blackjackDealer = (dealerHand.getCount() == 21 && dealerHand.getCardsNumber() == 2);

        if(playerHand.getCount() > 21)
            setResult(Result.DEALER);
        else if(dealerHand.getCount() > 21)
            setResult(Result.PLAYER);

        if(blackjackPlayer) {
            setResult(Result.PLAYER);
            roundIsOn = false;
        }
        else if(blackjackDealer) {
            setResult(Result.DEALER);
            roundIsOn = false;
        }

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

            operateActions();

            if(!roundIsOn)
                break;
        }

        addOrSubtractMoney();
    }

    private void addOrSubtractMoney() {
        int operatingBet = bet;

        if(result == Result.PUSH)
            return;

        if(splitted)
            return;
        else if(doubled)
            operatingBet *= 2;

        if(blackjackPlayer) {
            operatingBet *= 1.5;
        }


        if(result == Result.PLAYER)
            player.setMoney(player.getMoney() + operatingBet);
        else if(result == Result.DEALER)
            player.setMoney(player.getMoney() - operatingBet);
    }

    private void operateDealerAction() {

        String dealerAction = dealer.getAction();

        if(splitted) {
            roundIsOn = false;
            return;
        }

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

        if(playerAction == null)
            playerAction = "S";

        if(playerAction.equals("S"))
            operateDealerAction();

        else if(playerAction.equals("H")) {
            hit(player.getHand());
            checkCounts(player.getHand(), dealer.getHand());
        }

        else if(playerAction.equals("SP")) {
            splitted = true;

            Round[] rounds = new Round[2];
            rounds[0] = new Round(this.dealer, new Player(new Hand(this.player.getHand().get(1), this.deck.pop())));
            rounds[1] = new Round(this.dealer, new Player(new Hand(this.player.getHand().get(2), this.deck.pop())));

            for(Round r : rounds) {

                r.setDeck(this.deck);

                r.setBet(bet);

                r.play();

                if(r.getResult() == Result.PLAYER)
                    this.player.setMoney(this.player.getMoney() + bet);
                else if(r.getResult() == Result.DEALER)
                    this.player.setMoney(this.player.getMoney() - bet);
            }

            operateDealerAction();
        }

        else if(playerAction.equals("D")) {
            doubled = true;

            hit(player.getHand());
            checkCounts(player.getHand(), dealer.getHand());

            operateDealerAction();
        }
    }
}
