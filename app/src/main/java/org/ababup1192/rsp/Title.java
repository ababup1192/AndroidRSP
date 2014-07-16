package org.ababup1192.rsp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Title extends ActionBarActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        // Contextの取得
        context = this;
        SharedPreferences sharedPreferences = getSharedPreferences("high_score", Context.MODE_PRIVATE);
        TextView highScoreText = (TextView) findViewById(R.id.text_highScore);

        highScoreText.setText(getString(R.string.high_score) + " " + sharedPreferences.getInt("high_score", 0));

        // レイアウトリソースの束縛
        Button startButton = (Button) findViewById(R.id.button_start);

        // タイトル → ゲームメイン画面
        startButton.setOnClickListener(new View.OnClickListener() {
            // クリックイベント
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameMain.class);
                startActivity(intent);
            }
        });
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
