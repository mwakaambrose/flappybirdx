package ltd.trustfinity.ambrose.scores;

import java.util.TimerTask;

public class TimeBasedScore extends TimerTask {
    private int score = 0;

    @Override
    public void run() {
        this.score++;
        if (this.score > 9){
            this.score = 9;
        }
    }

    public int getScore() {
        return this.score;
    }
}
