package com.drunkler.drunkly;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * The open screen presents the game.
 *
 * Shows a waiver that the user has to accept to play, and simple explains and hypes the game
 *
 * @author Oscar
 */
public class OpenScreen extends AppCompatActivity  implements SimpleGestureFilter.SimpleGestureListener {
    private SimpleGestureFilter simpleGestureFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createWaiver();

    }

    /**
     * Create a waiver that the user has to accept to continue
     *
     * Has access to: startTheGame()
     */
    private void createWaiver() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OpenScreen.this);
        builder.setTitle(R.string.waiverTitleOpenScreen);
        builder.setMessage(R.string.waiverMessageOpenScreen);
        builder.setPositiveButton(R.string.sighYeahOpenScreen, (dialogInterface, i) -> {
            // start the game if the user accepts
            startTheGame();
        })
                .setNegativeButton(R.string.umNoOpenScreen, (dialogInterface, i) -> {
                    // sends the user back to the home screen if he refuses
                    finish();
                });

        builder.setCancelable(false); // To prevent the user from ignoring the waiver, he has to either accept ot refuse

        AlertDialog warning = builder.create();
        warning.show();

        Button negativeButton = warning.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * The actually introduction screen of the game
     */
    @TargetApi(26)
    private void startTheGame() {
        setContentView(R.layout.activity_open_screen);

        simpleGestureFilter = new SimpleGestureFilter(OpenScreen.this, this);

        final Animation translate = loadAnimation(this, R.anim.translate);

        //creates an animation for the play button when clicked
        final Button button = findViewById(R.id.playButton);
        button.setOnClickListener(view -> {
            button.setVisibility(View.VISIBLE);
            button.setAnimation(translate);
            button.startAnimation(translate);

            goToPlayerScreen();
        });

        // Create an animation when the screen is loaded
        final Animation scale = loadAnimation(this, R.anim.anim_scale);

        scale.setDuration(300);

        TextView title = findViewById(R.id.title);
        title.setAnimation(scale);

        button.setVisibility(View.VISIBLE);
        button.setAnimation(scale);
    }

    /**
     * Launches the screen where the user can enter the players
     */
    private void goToPlayerScreen() {
        finish();
        Intent intent = new Intent(this, PlayersNameScreen.class);
        startActivity(intent);
    }

    /**
     * When the user presses back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.simpleGestureFilter.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                finish();
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                goToPlayerScreen();
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                break;
            case SimpleGestureFilter.SWIPE_UP:
                goToPlayerScreen();
                break;
        }
    }
}