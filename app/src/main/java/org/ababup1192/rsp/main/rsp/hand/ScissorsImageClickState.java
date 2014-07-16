package org.ababup1192.rsp.main.rsp.hand;

import android.view.View;

import org.ababup1192.rsp.main.GameMainActivity;
import org.ababup1192.rsp.main.rsp.RSPGame;

public class ScissorsImageClickState extends ImageClickState {
    public ScissorsImageClickState(GameMainActivity gameMainActivity, RSPGame rspGame, View targetView) {
        super(gameMainActivity, rspGame, RSPGame.HAND.SCISSORS, targetView);
    }
}
