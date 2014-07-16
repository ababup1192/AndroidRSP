package org.ababup1192.rsp;

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

import java.util.Random;


public class GameMain extends ActionBarActivity {

    // Activity Resource
    private Context context;
    private LinearLayout restLayout;
    private LinearLayout battleLayout;
    private LinearLayout bottomHandsLayout;
    private TextView scoreText;
    private ImageView rockImage;
    private ImageView scissorsImage;
    private ImageView paperImage;

    // Game Resources
    private enum GAME_STATE {
        MY_TURN, RESULT, END
    }

    private enum HAND {
        ROCK, SCISSORS, PAPER
    }

    private enum JUDGE {
        WIN, LOSE, DRAW
    }

    private int rest = 3;
    private Integer score = 0;
    private String defaultScoreText;
    private GAME_STATE gameState = GAME_STATE.MY_TURN;
    private HAND myHand;
    private HAND enemyHand;
    private JUDGE judgeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

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

        // ゲーム状態の管理

        // スコアボード初期化
        setScore();

        // ゲーム状態の初期化
        gameState = GAME_STATE.MY_TURN;

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
                if (gameState == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    gameState = GAME_STATE.MY_TURN;
                } else if (gameState == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                }
                break;
        }
        return true;
    }

    private void battle() {
        long seed = System.currentTimeMillis(); // 現在時刻のミリ秒
        Random random = new Random(seed);
        enemyHand = HAND.values()[random.nextInt(3)];
        judge(myHand.ordinal(), enemyHand.ordinal());
    }

    private void judge(int my, int enemy) {
        int judge = (my - enemy + 3) % 3;
        if (judge == 0) {
            judgeStatus = JUDGE.DRAW;
            showHand();
        } else if (judge == 2) {
            judgeStatus = JUDGE.WIN;
            showHand();
            score++;
            setScore();
        } else {
            judgeStatus = JUDGE.LOSE;
            showHand();
            rest--;
            setRestImages();
        }
        if (rest == 0) {
            gameState = GAME_STATE.END;
        } else {
            gameState = GAME_STATE.RESULT;
        }
    }

    private void setScore() {
        scoreText.setText(defaultScoreText + " " + score);
    }

    private void setRestImages() {
        restLayout.removeAllViews();
        for (int i = 0; i < rest; i++) {
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
                if (gameState == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    gameState = GAME_STATE.MY_TURN;
                } else if (gameState == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    myHand = HAND.ROCK;
                    battle();
                }
            }
        });

        scissorsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    gameState = GAME_STATE.MY_TURN;
                } else if (gameState == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    myHand = HAND.SCISSORS;
                    battle();
                }
            }
        });

        paperImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState == GAME_STATE.RESULT) {
                    battleLayout.removeAllViews();
                    setRSPImages();
                    setClickRSPImageEvent();
                    gameState = GAME_STATE.MY_TURN;
                } else if (gameState == GAME_STATE.END) {
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                } else {
                    bottomHandsLayout.removeView(v);
                    myHand = HAND.PAPER;
                    battle();
                }
            }
        });
    }

    private TextView getJudgeText() {
        TextView judgeText = new TextView(this);
        if (judgeStatus == JUDGE.WIN) {
            judgeText.setText(getString(R.string.game_main_win_text));
            judgeText.setTextColor(Color.parseColor("#CC3300"));
        } else if (judgeStatus == JUDGE.LOSE) {
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

    private void showHand() {
        battleLayout.removeAllViews();
        battleLayout.addView(getHandView(enemyHand));
        battleLayout.addView(getJudgeText());
        battleLayout.addView(getHandView(myHand));
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
