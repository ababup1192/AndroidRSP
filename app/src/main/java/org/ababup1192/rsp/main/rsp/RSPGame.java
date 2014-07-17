package org.ababup1192.rsp.main.rsp;

import org.ababup1192.rsp.main.GameMainActivity;

import java.util.Random;

public class RSPGame {

    // Game Resources
    public static enum GAME_STATE {
        MY_TURN, RESULT, END
    }

    public static enum HAND {
        ROCK, SCISSORS, PAPER
    }

    public static enum JUDGE {
        WIN, LOSE, DRAW
    }

    private Integer rest;
    private Integer score;
    private GAME_STATE gameState;
    private HAND myHand;
    private HAND enemyHand;
    private JUDGE judgeStatus;

    private GameMainActivity gameMainActivity;

    public RSPGame(GameMainActivity gameMainActivity) {
        rest = 3;
        score = 0;
        gameState = GAME_STATE.MY_TURN;
        this.gameMainActivity = gameMainActivity;
    }

    public void setGameState(GAME_STATE gameState) {
        this.gameState = gameState;
    }

    public Integer getRest() {
        return rest;
    }

    public Integer getScore() {
        return score;
    }

    public GAME_STATE getGameState() {
        return gameState;
    }

    public HAND getEnemyHand() {
        return enemyHand;
    }

    public HAND getMyHand() {
        return myHand;
    }

    public void setMyHand(HAND hand) {
        myHand = hand;
    }

    public JUDGE getJudgeStatus() {
        return judgeStatus;
    }

    public void battle() {
        long seed = System.currentTimeMillis(); // 現在時刻のミリ秒
        Random random = new Random(seed);
        enemyHand = HAND.values()[random.nextInt(3)];
        judge(myHand.ordinal(), enemyHand.ordinal());
    }

    public void judge(int my, int enemy) {
        int judge = (my - enemy + 3) % 3;
        if (judge == 0) {
            judgeStatus = JUDGE.DRAW;
            gameMainActivity.showResult();
        } else if (judge == 2) {
            judgeStatus = JUDGE.WIN;
            score++;
            gameMainActivity.showResult();
            gameMainActivity.showScore();
        } else {
            judgeStatus = JUDGE.LOSE;
            rest--;
            gameMainActivity.showResult();
            gameMainActivity.showRestImages();
        }
        if (rest == 0) {
            gameState = GAME_STATE.END;
        } else {
            gameState = GAME_STATE.RESULT;
        }
    }


}
