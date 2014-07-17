package org.ababup1192.rsp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ababup1192.rsp.R;
import org.ababup1192.rsp.gameover.GameOverActivity;
import org.ababup1192.rsp.main.rsp.RSPGame;
import org.ababup1192.rsp.main.hand.GameState;
import org.ababup1192.rsp.main.hand.PaperImageClickState;
import org.ababup1192.rsp.main.hand.RockImageClickState;
import org.ababup1192.rsp.main.hand.ScissorsImageClickState;
import org.ababup1192.rsp.main.hand.StateSelector;
import org.ababup1192.rsp.main.util.UIHelper;
import org.ababup1192.rsp.util.LayoutHelper;


public class GameMainActivity extends ActionBarActivity implements Observer {

    private String defaultScoreText;

    private RSPGame rspGame;
    private GameMainActivity gameMainActivity;
    private Context context;
    private LinearLayout restLayout;
    private LinearLayout battleLayout;
    private LinearLayout bottomHandsLayout;
    private TextView scoreText;
    private ImageView rockImage;
    private ImageView scissorsImage;
    private ImageView paperImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        // ゲーム初期化
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 画面をタップして離した時にイベント取得
            case MotionEvent.ACTION_UP:
                switch (rspGame.getGameState()) {
                    case RESULT:
                        battleLayout.removeAllViews();
                        showRSPImages();
                        rspGame.setGameState(RSPGame.GAME_STATE.MY_TURN);
                        break;
                    case END:
                        Intent intent = new Intent(context, GameOverActivity.class);
                        intent.putExtra("score", rspGame.getScore());
                        startActivity(intent);
                        break;
                }
                break;
        }
        return true;
    }

    private void init() {
        // Contextの取得
        context = this;
        gameMainActivity = this;

        // レイアウトリソースの束縛
        restLayout = (LinearLayout) findViewById(R.id.layout_rest_images);
        battleLayout = (LinearLayout) findViewById(R.id.layout_battle);
        bottomHandsLayout = (LinearLayout) findViewById(R.id.layout_bottom_hands);
        defaultScoreText = getString(R.string.score);
        scoreText = (TextView) findViewById(R.id.text_score);
        rockImage = (ImageView) findViewById(R.id.image_rock);
        scissorsImage = (ImageView) findViewById(R.id.image_scissors);
        paperImage = (ImageView) findViewById(R.id.image_paper);

        gameInit();
    }

    /**
     * ゲームの初期化, UIの初期化など
     */
    private void gameInit() {
        // ゲームの開始
        rspGame = new RSPGame(this);

        showScore();
        showRestImages();
        setClickRSPImageEvent();
    }


    // レイアウトの外部公開
    public LinearLayout getBattleLayout() {
        return battleLayout;
    }

    public LinearLayout getBottomHandsLayout() {
        return bottomHandsLayout;
    }

    /**
     * スコアの表示
     */
    public void showScore() {
        if (rspGame != null) {
            scoreText.setText(defaultScoreText + " " + rspGame.getScore());
        }
    }

    /**
     * 残機の表示
     */
    public void showRestImages() {
        if (rspGame != null) {
            restLayout.removeAllViews();
            for (int i = 0; i < rspGame.getRest(); i++) {
                ImageView smileImage = new ImageView(this);
                smileImage.setImageResource(R.drawable.smile);
                int whSize = (int) getResources().getDimension(R.dimen.smile_image_wh);
                LayoutHelper.setLinearLayoutParams(smileImage, whSize, whSize);
                restLayout.addView(smileImage);
            }
        }
    }


    /**
     * じゃんけん結果の表示
     */
    public void showResult() {
        battleLayout.removeAllViews();
        battleLayout.addView(UIHelper.getHandView(rspGame.getEnemyHand(), rspGame, this));
        battleLayout.addView(UIHelper.getJudgeText(rspGame, this));
        battleLayout.addView(UIHelper.getHandView(rspGame.getMyHand(), rspGame, this));
    }


    /**
     * 画面下部のグーチョキパーボタンの生成・表示
     */
    public void showRSPImages() {
        bottomHandsLayout.removeAllViews();
        rockImage = new ImageView(this);
        scissorsImage = new ImageView(this);
        paperImage = new ImageView(this);

        rockImage.setImageResource(R.drawable.rock);
        scissorsImage.setImageResource(R.drawable.scissors);
        paperImage.setImageResource(R.drawable.paper);
        int whSize = (int) getResources().getDimension(R.dimen.hand_image_wh);
        LayoutHelper.setLinearLayoutParams(rockImage, whSize);
        LayoutHelper.setLinearLayoutParams(scissorsImage, whSize, (int) getResources().getDimension(R.dimen.rsp_images_margin), 0, 0, 0);
        LayoutHelper.setLinearLayoutParams(paperImage, whSize, (int) getResources().getDimension(R.dimen.rsp_images_margin), 0, 0, 0);

        bottomHandsLayout.addView(rockImage);
        bottomHandsLayout.addView(scissorsImage);
        bottomHandsLayout.addView(paperImage);

        setClickRSPImageEvent();
    }

    /**
     * 画面下部のグーチョキパーボタンのイベント設置
     */
    public void setClickRSPImageEvent() {
        if (rockImage != null && scissorsImage != null && paperImage != null) {

            rockImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameState state = new RockImageClickState(gameMainActivity, rspGame, v);
                    new StateSelector(state).selectState(rspGame.getGameState());
                }
            });

            scissorsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameState state = new ScissorsImageClickState(gameMainActivity, rspGame, v);
                    new StateSelector(state).selectState(rspGame.getGameState());
                }
            });

            paperImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameState state = new PaperImageClickState(gameMainActivity, rspGame, v);
                    new StateSelector(state).selectState(rspGame.getGameState());
                }
            });
        }
    }

    @Override
    public void update() {
        showResult();
        showScore();
        showRestImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
