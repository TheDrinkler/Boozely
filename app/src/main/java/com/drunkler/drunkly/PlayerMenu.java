package com.drunkler.drunkly;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import static com.drunkler.drunkly.App.getContext;

/**
 * The player menu creates and stores players
 *
 * @author Oscar
 */
@TargetApi(Build.VERSION_CODES.N)
class PlayerMenu {
    private ArrayList<Player> players; // List to store every player

    PlayerMenu() {
        // Creating and adding the players to the menu
        players = new ArrayList<>();

        Player player1 = new Player(getContext().getString(R.string.player1));
        Player player2 = new Player(getContext().getString(R.string.player2));
        Player player3 = new Player(getContext().getString(R.string.player3));

        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    /**
     *
     * @param i The position of the player in the list
     * @return The name of the player
     */
    String getPlayer(int i) {
        return players.get(i).getName();
    }

    /**
     * Method to add a player in the menu
     */
    public void addPlayer(){
        Player player = new Player(getContext().getString(R.string.playerPlain) + " " + (players.size() + 1));
        players.add(player);
    }

    /**
     * Method to add all players from an ArrayList to the players array list
     * @param playerz The ArrayList we want to get the players from
     */
    void addAllPlayers(ArrayList<Player> playerz) {
        players.addAll(playerz);
    }

    /**
     * Method to add a new player with his name, but checking that this player doesn't already exist
     * @param name the name of the new player
     */
    void newPlayer(String name) {
            Player player = new Player(name);
            players.add(player);
    }

    /**
     * Method to check the if the name of the player is already in the game
     * @param name The name of the player
     * @return true if the player already exists, false if he doesn't
     */
    Boolean playerExists(String name){
        Boolean playerExistsAlready = false;
        for (Player inGame: players) {
            if (name.equals(inGame.getName())) {
                playerExistsAlready = true;
            }
        }
        return playerExistsAlready;
    }

    /**
     * Method to remove a specific player
     * @param i The index of the player
     */
    public void removePlayer(int i) {
        players.remove(i);
    }

    /**
     * Method to remove all players from the list
     */
    void removeAllPlayers() {
        players.clear();
    }

    /**
     * To see the list of players
     * @return the list of all the players
     */
    ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Method to get a list of the name of each player
     * @return A list of the name of each player
     */
    List<String> getPlayersNames() {
        List<String> list = new ArrayList<>();
        players.forEach(player -> list.add(player.getName()));
        return list;
    }

    /**
     * Remove from the list of all players, all those who are nameless (aka Player i)
     */
    void filter() {
        for (int i = players.size() - 1; i >= 0; i--) {
            if (players.get(i).getName().contains(getContext().getString(R.string.playerString)) || players.get(i).getName().isEmpty()) {
                players.remove(i);
            }
        }
    }

    /**
     * To check if their is any players in the list
     * @return true if the list is not empty/ false if it is
     */
    boolean hasPlayers() {
        return !players.isEmpty();
    }

    /**
     * Size of the list
     * @return the size of the list of players
     */
    public int size() {
        return players.size();
    }
}
