package org.ababup1192.rsp.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ababup1192.rsp.R;
import org.ababup1192.rsp.gameover.GameOverActivity;

import static org.ababup1192.rsp.main.RSPGame.GAME_STATE;
import static org.ababup1192.rsp.main.RSPGame.HAND;
import static org.ababup1192.rsp.main.RSPGame.JUDGE;


public class GameMainActivity extends ActionBarActivity {

    private String defaultScoreText;

    private RSPGame rspGame;

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

    private void init() {
        // Contextの取得
        context = this;

        // レイアウトリソースの束縛
        restLayout = (LinearLayout) findViewById(R.id.layout_rest_images);
        battleLayout = (LinearLayout) findViewById(R.id.layout_battle);
        bottomHandsLayout = (LinearLayout) findViewById(R.id.layout_bottom_hands);
        defaultScoreText = getString(R.string.score);
        scoreText = (TextView) findViewById(R.id.text_score);
        rockImage = (ImageView) findViewById(R.id.image_rock);
        scissorsImage = (ImageView) findViewById(R.id.image_scissors);
        paperImage = (ImageView) findViewById(R.id.image_paper);

        // ゲームの開始
        rspGame = new RSPGame(this);

        // スコアボード初期化
        setScore();

        // 残機の初期化
        setRestImages();

        // じゃんけんボタンの初期化
        setClickRSPImageEvent();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 画面をタップして離した時にイベント取得
            case MotionEvent.ACTION_UP:
                if (rspGame.getGameState() == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    rspGame.setGameState(GAME_STATE.MY_TURN);
                } else if (rspGame.getGameState() == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("score", rspGame.getScore());
                    startActivity(intent);
                }
                break;
        }
        return true;
    }

    public void setScore() {
        scoreText.setText(defaultScoreText + " " + rspGame.getScore());
    }

    public void setRestImages() {
        restLayout.removeAllViews();
        for (int i = 0; i < rspGame.getRest(); i++) {
            ImageView smileImage = new ImageView(this);
            smileImage.setImageResource(R.drawable.smile);
            int whSize = (int) getResources().getDimension(R.dimen.smile_image_wh);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(whSize, whSize);
            smileImage.setLayoutParams(layoutParams);
            restLayout.addView(smileImage);
        }
    }

    private void setRSPImages() {
        bottomHandsLayout.removeAllViews();
        rockImage = new ImageView(this);
        scissorsImage = new ImageView(this);
        paperImage = new ImageView(this);

        rockImage.setImageResource(R.drawable.rock);
        scissorsImage.setImageResource(R.drawable.scissors);
        paperImage.setImageResource(R.drawable.paper);
        int whSize = (int) getResources().getDimension(R.dimen.hand_image_wh);
        LinearLayout.LayoutParams rockLayoutParams = new LinearLayout.LayoutParams(whSize, whSize);
        LinearLayout.LayoutParams spLayoutParams = new LinearLayout.LayoutParams(whSize, whSize);
        spLayoutParams.setMargins((int) getResources().getDimension(R.dimen.rsp_images_margin), 0, 0, 0);
        rockImage.setLayoutParams(rockLayoutParams);
        scissorsImage.setLayoutParams(spLayoutParams);
        paperImage.setLayoutParams(spLayoutParams);

        bottomHandsLayout.addView(rockImage);
        bottomHandsLayout.addView(scissorsImage);
        bottomHandsLayout.addView(paperImage);

        setClickRSPImageEvent();
    }

    private void setClickRSPImageEvent() {
        rockImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rspGame.getGameState() == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    rspGame.setGameState(GAME_STATE.MY_TURN);
                } else if (rspGame.getGameState() == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("score", rspGame.getScore());
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    rspGame.setMyHand(HAND.ROCK);
                    rspGame.battle();
                }
            }
        });

        scissorsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rspGame.getGameState() == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    rspGame.setGameState(GAME_STATE.MY_TURN);
                } else if (rspGame.getGameState() == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("score", rspGame.getScore());
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    rspGame.setMyHand(HAND.SCISSORS);
                    rspGame.battle();
                }
            }
        });

        paperImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rspGame.getGameState() == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    rspGame.setGameState(GAME_STATE.MY_TURN);
                } else if (rspGame.getGameState() == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("score", rspGame.getScore());
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    rspGame.setMyHand(HAND.PAPER);
                    rspGame.battle();
                }
            }
        });
    }

    private TextView getJudgeText() {
        TextView judgeText = new TextView(this);
        if (rspGame.getJudgeStatus() == JUDGE.WIN) {
            judgeText.setText(getString(R.string.game_main_win_text));
            judgeText.setTextColor(Color.parseColor("#CC3300"));
        } else if (rspGame.getJudgeStatus() == JUDGE.LOSE) {
            judgeText.setText(getString(R.string.game_main_lose_text));
            judgeText.setTextColor(Color.parseColor("#3333FF"));
        } else {
            judgeText.setText(getString(R.string.game_main_draw_text));
            judgeText.setTextColor(Color.parseColor("#BBBBBB"));
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        judgeText.setLayoutParams(layoutParams);
        return judgeText;
    }

    private ImageView getHandView(HAND hand) {
        ImageView handImage = new ImageView(this);
        if (hand == HAND.ROCK) {
            handImage.setImageResource(R.drawable.rock);
        } else if (hand == HAND.SCISSORS) {
            handImage.setImageResource(R.drawable.scissors);
        } else {
            handImage.setImageResource(R.drawable.paper);
        }
        int whSize = (int) getResources().getDimension(R.dimen.hand_image_wh);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(whSize, whSize);
        handImage.setLayoutParams(layoutParams);
        return handImage;
    }

    public void showHand() {
        battleLayout.removeAllViews();
        battleLayout.addView(getHandView(rspGame.getEnemyHand()));
        battleLayout.addView(getJudgeText());
        battleLayout.addView(getHandView(rspGame.getMyHand()));
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
