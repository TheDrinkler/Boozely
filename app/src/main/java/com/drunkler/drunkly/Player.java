package com.drunkler.drunkly;

/**
 * The player is are those who will be playing the game. It only has a name
 *
 * @author Oscar
 */
class Player {
    private String name;

    Player(String name){
        this.name = name;
    }

    /**
     * To retrieve the name of the player
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * To change the name of the player
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
