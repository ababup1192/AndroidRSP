package org.ababup1192.rsp.main.rsp.util;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ababup1192.rsp.R;
import org.ababup1192.rsp.main.rsp.RSPGame;
import org.ababup1192.rsp.util.LayoutHelper;

public class UIHelper {

    public static TextView getJudgeText(RSPGame rspGame, Context context) {
        TextView judgeText = new TextView(context);
        switch (rspGame.getJudgeStatus()) {
            case WIN:
                judgeText.setText(context.getString(R.string.game_main_win_text));
                judgeText.setTextColor(Color.parseColor("#CC3300"));
                break;
            case LOSE:
                judgeText.setText(context.getString(R.string.game_main_lose_text));
                judgeText.setTextColor(Color.parseColor("#3333FF"));
                break;
            case DRAW:
                judgeText.setText(context.getString(R.string.game_main_draw_text));
                judgeText.setTextColor(Color.parseColor("#BBBBBB"));
                break;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        judgeText.setLayoutParams(layoutParams);
        LayoutHelper.setLinearLyoutParamsWrap(judgeText);
        return judgeText;
    }

    public static ImageView getHandView(RSPGame.HAND hand, RSPGame rspGame, Context context) {
        ImageView handImage = new ImageView(context);
        switch (hand) {
            case ROCK:
                handImage.setImageResource(R.drawable.rock);
                break;
            case SCISSORS:
                handImage.setImageResource(R.drawable.scissors);
                break;
            case PAPER:
                handImage.setImageResource(R.drawable.paper);
                break;
        }
        int whSize = (int) context.getResources().getDimension(R.dimen.hand_image_wh);
        LayoutHelper.setLinearLayoutParams(handImage, whSize);
        return handImage;
    }
}
