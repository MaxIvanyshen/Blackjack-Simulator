import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameTest {

    @Test
    public void createGame() {
        Game game = new Game();
        assertThat(game.getScore().dealer).isEqualTo(0);
        assertThat(game.getScore().player).isEqualTo(0);
    }

    @Test
    public void summarySizeEquals0() {
        Game g = new Game();
        assertThat(g.getResultSummary().size()).isEqualTo(0);
    }

    @Test
    public void setRound() {
        Game g = new Game();
        Round r = new Round(new Dealer(new Hand()),
                new Player(new Hand()));

        r.setBet(100);
        g.setRoundSetup(r);
        g.setIterations(1);
        g.start();

        assertThat(g.getResultSummary().size()).isEqualTo(1);
        assertThat(g.getPlayerMoneySummary().size()).isEqualTo(1);
    }

    @Test
    public void twoRounds() {
        Game g = new Game();
        Round r = new Round(new Dealer(), new Player());
        r.setBet(200);
        g.setRoundSetup(r);
        g.setIterations(10);

        g.start();

        for(Result res : g.getResultSummary())
            System.out.println(res);

        assertThat(g.getResultSummary().size()).isEqualTo(10);
        assertThat(g.getPlayerMoneySummary().size()).isEqualTo(10);
    }
}
