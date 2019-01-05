package com.drunkler.drunkly;

/**
 * A MiniGame is a quick and simple game to play within the game, no or a few items are required to play them
 *
 * @author Oscar
 */
class MiniGame {
    private String name; // The name of the minigame
    private String rules; // The rules of the minigame
    private String example; // An example for the minigame
    private String note; // A sub note for the minigame

    /**
     * Constructor for objects of class miniGame
     */
    MiniGame(String name) {
        // initialise instance variables
        this.name = name;
        rules = "";
        example = "";
        note = "";
    }

    /**
     * @return The name of the miniGame
     */
    protected String getName() {
        return name;
    }

    /**
     * Set the rules of the miniGame
     *
     * @param r The rules of the game
     */
    void putRules(String r) {
        rules = r;
    }

    /**
     * Add examples for a better comprehension
     *
     * @param e The example
     */
    void addExample(String e) {
        example = e;
    }

    /**
     * Add a note to the rule
     *
     * @param n The note to add
     */
    void addNote(String n) {
        note = n;
    }

    /**
     * @return A full description of the game
     */
    String getFullDescription() {
        return name + "\n" + rules + example + note;
    }

    /**
     * @return the rules, examples and notes of the miniGame
     */
    String getSemiFullDescription() {
        return rules + example + note;
    }
}
