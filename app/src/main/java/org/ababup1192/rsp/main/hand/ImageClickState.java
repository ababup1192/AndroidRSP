package org.ababup1192.rsp.main.hand;


import android.content.Intent;
import android.view.View;

import org.ababup1192.rsp.gameover.GameOverActivity;
import org.ababup1192.rsp.main.GameMainActivity;
import org.ababup1192.rsp.main.rsp.RSPGame;

import static org.ababup1192.rsp.main.rsp.RSPGame.HAND;

public abstract class ImageClickState implements GameState {

    private GameMainActivity gameMainActivity;
    private RSPGame rspGame;
    private HAND hand;
    private View targetView;

    public ImageClickState(GameMainActivity gameMainActivity, RSPGame rspGame, HAND hand, View targetView) {
        this.gameMainActivity = gameMainActivity;
        this.rspGame = rspGame;
        this.hand = hand;
        this.targetView = targetView;
    }

    @Override
    public void behaveMyTurn() {
        gameMainActivity.getBottomHandsLayout().removeView(targetView);
        rspGame.setMyHand(hand);
        rspGame.battle();
    }

    @Override
    public void behaveResult() {
        gameMainActivity.getBattleLayout().removeAllViews();
        gameMainActivity.showRSPImages();
        gameMainActivity.setClickRSPImageEvent();
        rspGame.setGameState(RSPGame.GAME_STATE.MY_TURN);
    }

    @Override
    public void behaveEnd() {
        Intent intent = new Intent(gameMainActivity.getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score", rspGame.getScore());
        gameMainActivity.startActivity(intent);
    }
}
