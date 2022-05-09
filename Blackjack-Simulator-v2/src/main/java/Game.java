import java.util.ArrayList;
import java.util.List;

public class Game {

    private Score score;

    private List<Result> resultSummary;

    private List<Long> playerMoneySummary;

    private long iterations;

    private Round roundSetup;

    public Game() {
        this.resultSummary = new ArrayList<>();
        this.playerMoneySummary = new ArrayList<>();
        this.score = new Score();
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<Result> getResultSummary() {
        return resultSummary;
    }

    public void setResultSummary(List<Result> summary) {
        this.resultSummary = summary;
    }

    public long getIterations() {
        return iterations;
    }

    public void setIterations(long iterations) {
        this.iterations = iterations;
    }

    public Round getRoundSetup() {
        return roundSetup;
    }

    public void setRoundSetup(Round roundSetup) {
        this.roundSetup = roundSetup;
    }

    public List<Long> getPlayerMoneySummary() {
        return playerMoneySummary;
    }

    public void setPlayerMoneySummary(List<Long> playerMoneySummary) {
        this.playerMoneySummary = playerMoneySummary;
    }

    public void start() {

        Deck d = new Deck();
        d.createDeck(6);
        d.shuffle();

        for(int i = 0; i < iterations; i++) {
            Round r = new Round(new Dealer(), new Player());
            r.setDeck(d);
            r.deal(r.getDealer());
            r.deal(r.getPlayer());

            r.play();
            resultSummary.add(r.getResult());
        }
    }
}
