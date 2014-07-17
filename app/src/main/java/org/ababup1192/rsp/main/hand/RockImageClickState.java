package org.ababup1192.rsp.main.hand;

import android.view.View;

import org.ababup1192.rsp.main.GameMainActivity;
import org.ababup1192.rsp.main.rsp.RSPGame;

public class RockImageClickState extends ImageClickState {
    public RockImageClickState(GameMainActivity gameMainActivity, RSPGame rspGame, View targetView) {
        super(gameMainActivity, rspGame, RSPGame.HAND.ROCK, targetView);
    }
}
