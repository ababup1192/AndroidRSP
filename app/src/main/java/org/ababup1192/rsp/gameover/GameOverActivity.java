package org.ababup1192.rsp.gameover;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ababup1192.rsp.R;
import org.ababup1192.rsp.main.GameMainActivity;
import org.ababup1192.rsp.title.TitleActivity;

public class GameOverActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final Context context = this;
        SharedPreferences sharedPreferences = getSharedPreferences("high_score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int highScore = sharedPreferences.getInt("high_score", 0);

        Intent intent = getIntent();
        Integer score = intent.getIntExtra("score", 0);

        LinearLayout scoreLayout = (LinearLayout) findViewById(R.id.layout_score);
        TextView continueText = (TextView) findViewById(R.id.text_continue);
        TextView endText = (TextView) findViewById(R.id.text_end);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView scoreText = new TextView(this);
        scoreText.setText(score.toString());
        scoreText.setTextSize(40);
        scoreText.setLayoutParams(layoutParams);

        TextView highScoreText = new TextView(this);
        highScoreText.setText(getString(R.string.game_over_high_score));
        highScoreText.setTextSize(40);
        highScoreText.setLayoutParams(layoutParams);

        scoreLayout.addView(scoreText);
        if (score > highScore) {
            editor.putInt("high_score", score);
            editor.apply();
            scoreLayout.addView(highScoreText);
        }

        continueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameMainActivity.class);
                startActivity(intent);
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TitleActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_over, menu);
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
