package org.ababup1192.rsp.main.rsp.hand;

import org.ababup1192.rsp.main.rsp.RSPGame;

public class StateSelector {

    GameState gameState;

    public StateSelector(GameState gameState) {
        this.gameState = gameState;
    }

    public void selectState(RSPGame.GAME_STATE currentState) {

        switch (currentState) {
            case MY_TURN:
                gameState.behaveMyTurn();
                break;
            case RESULT:
                gameState.behaveResult();
                break;
            case END:
                gameState.behaveEnd();
                break;
        }
    }
}
