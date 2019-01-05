package com.drunkler.drunkly;


import java.util.HashMap;
import java.util.List;

import static com.drunkler.drunkly.App.getContext;

/**
 * The Card class holds every details a card card game can have
 * @author Oscar
 */
class Card {
    private String description; // A description of the card: the family and the value of the card eg 5 of spades
    private String rules; // A quick explanation of the rules assigned to the card
    private String rulesDetails; // A detailed explanation of the rules assigned to the card
    private String player; // The name of the player assigned to the card
    private Integer cardImage; // The image directory of the card
    private Boolean playerWasAdded; // A boolean value to check that we don't set twice a player to a single card
    // For GameCard
    private double value; // The value of the card 1 to 15
    private List miniGamesTitles; // A list that holds the name of every minigame
    private HashMap<String, String> miniGames; // A HashMap that holds The title a the explanation of the minigame

    /**
     * Constructor for objects of class Card
     */
    Card(Integer descriptionInt) {
        description = getContext().getString(descriptionInt);
        rules = "";
        rulesDetails = "";
        player = "";
        value = 0;
        playerWasAdded = false;
    }

    /**
     * @return The description of the Card
     */
    protected String getDescription() {
        return description;
    }

    /**
     * Set the rules assigned to that card
     * @param r The rules
     */
    void setRule(Integer r) {
        rules += getContext().getString(r);
    }

    /**
     * Set the rules in details
     * @param rd The integer id in the string xml file for the rules in details
     */
    void setRulesDetails(Integer rd) {
        rulesDetails += getContext().getString(rd);
    }

    /**
     * Same method as above but with string
     * @param rd the string value of the rules in details
     */
    void setRulesDetails(String rd){
        rulesDetails += rd;
    }

    /**
     * Set the weight of the card
     *          0 if it's a normal card
     *          more if it's a game card
     *
     *@param v The weight of the Card
     */
    void setValue(double v) {
        value = v;
    }

   /**
     * Assign the image of the card
     *
     * @param id The file directory to the image to open
     */
   void assignImage(Integer id) {
        cardImage = id;
   }

    /**
     * Get the rules assigned to that card
     * @return The rules of that card
     */
    String getRules() {
        return rules;
    }

    /**
     * @return The rules explanation of that card
     */
    String getRulesInDetails() {
        return rulesDetails;
    }

    /**
     * @return the weight of the card
     */
    protected double getValue() {
        return value;
    }

    /**
     * Create a List retaining the titles of all the miniGames
     *
     * @param array The List we're copying the values from
     */
    void setMiniGamesTitles(List array) {
        miniGamesTitles = array;
    }

    /**
     * Create a hashMap retaining all the miniGames
     *
     * @param hash The HashMap we're copying the values from
     */
    void setMiniGames(HashMap<String, String> hash) {
        miniGames = hash;
    }

    /**
     * @return the Array retaining all the miniGames titles
     */
    List getMiniGamesTitles() {
        return miniGamesTitles;
    }

    /**
     * @return The hashMap of the miniGames
     */
    HashMap getMiniGames() {
        return miniGames;
    }

   /**
     * @return The image associated with the card
     */
   protected Integer getCardImage() {
       return cardImage;
   }

    /**
     *
     * @return The name of the player assigned to the card
     */
   String getPlayer() {
        return player;
    }

    /**
     * Sets the name of the player assigned to the card
     * @param player The name of the player
     */
   void setPlayer(String player) {
       this.player = player;
   }

    /**
     *
     * @return true if we have already assigned the player to the card, false otherwise
     */
   Boolean getPlayerWasAdded() {
        return playerWasAdded;
   }

    /**
     * Makes it true when adding a player
     */
   void setPlayerWasAdded() {
        this.playerWasAdded = true;
   }
}