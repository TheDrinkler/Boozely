package com.drunkler.drunkly;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * The PlayerNameScreen class dictates how the screen where the user enters the different players behaves
 *
 * @author Oscar
 */
public class PlayersNameScreen extends AppCompatActivity {

    private ArrayList<Player> players; // A list to hold the players
    public PlayerMenu playerMenu; // A player menu used to populate the screen when started
    public static PlayerMenu playerMenu1; // A static player menu to store all the players entered on this screen and access it on the next

    RecyclerView listOfPlayers; // The recycler view where the players can see, add and enter other players
    FloatingActionButton addPlayer; // The button used to add a player
    private ListOfPlayersAdapter listOfPlayersAdapter; // The adapter for the recycler view
    private Button button; // The button to start the game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_name_screen);


        // For the add
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView5);
        PublisherAdRequest adRequest1 = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest1);

        // Creates and initializes the add
        MobileAds.initialize(this, "ca-app-pub-8818799719074826~6407123349");

        AdView mAdView = findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        listOfPlayers = findViewById(R.id.playerNames);
        addPlayer = findViewById(R.id.addPlayer);

        // Start the game with the new players entered, filtering the menu as well
        button = findViewById(R.id.Booze);
        button.setOnClickListener(view -> {
            playerMenu1 = new PlayerMenu();
            playerMenu1.removeAllPlayers();
            playerMenu1.addAllPlayers(playerMenu.getPlayers());
            playerMenu1.filter();
            goToCardScreen();
        });

        playerMenu = new PlayerMenu();
        players = new ArrayList<>();

        // Set the default view of the list
        setListOfPlayersView(players);

        // Adds a row to the list and a player to the menu, scrolls to the new player entered
        addPlayer.setOnClickListener((View view) -> {
            playerMenu.addPlayer();
            players.clear();
            seeList(players);
            listOfPlayers.smoothScrollToPosition(players.size() - 1);
        });
        createToast();
    }

    /**
     * Creates the list of all the players (the view)
     * @param playerz the list of the players
     */
    void setListOfPlayersView(ArrayList<Player> playerz) {
        listOfPlayersAdapter = new ListOfPlayersAdapter(playerz);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());


        listOfPlayers.setLayoutManager(layoutManager);
        listOfPlayers.setItemAnimator(new DefaultItemAnimator());
        listOfPlayers.setAdapter(listOfPlayersAdapter);

        seeList(playerz);
    }

    /**
     * Adds every player to the list and displays them
     * @param playerz The list of the players
     */
    private void seeList(ArrayList<Player> playerz) {
        playerz.addAll(playerMenu.getPlayers());
        listOfPlayersAdapter.notifyItemInserted(playerz.size() - 1);
    }

    /**
     * Method to start the game
     */
    private void goToCardScreen() {
        Set<String> set = new HashSet<>(playerMenu1.getPlayersNames());

        // Check that they're isn't twice the same name
        if (set.size() < playerMenu1.getPlayers().size()) {
            Toast.makeText(this, R.string.toastDiffPlayer, Toast.LENGTH_SHORT).show();
        }
        else {
            // Start the game
            final Animation translate = loadAnimation(this, R.anim.translate);
            button.setVisibility(View.VISIBLE);
            button.setAnimation(translate);
            button.startAnimation(translate);
            Intent intent = new Intent(this, CardScreen.class);
            startActivity(intent);
        }
    }

    /**
     * The method to inform the user that entering players is optional
     */
    private void createToast() {
        Toast toast = Toast.makeText(this, R.string.toastOptionalPlayer, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * When the user presses the back button
     */
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
