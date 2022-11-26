package models;

import simulator.Deck;
import simulator.Player;
import simulator.Result;

public class RoundResponseModel {

    public Result result;
    public Deck deck;
    public Player player;

    public RoundResponseModel(Result result, Deck deck, Player player) {
        this.result = result;
        this.deck = deck;
        this.player = player;
    }
}
