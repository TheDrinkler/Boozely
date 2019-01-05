package com.drunkler.drunkly;

import java.util.ArrayList;

import static com.drunkler.drunkly.App.getContext;

/**
 * Drink class holds the spirits and the mixers needed to mix a drink
 *
 * @author Oscar
 */
class Drink {
    private String sorM; // The spirit or mixer chosen
    private ArrayList<String> mixers; // What mixes well with it

    /**
     * Creates a drink
     * @param sorMID The spirit or mixer chosen
     * @param mixers What mixes well with it
     */
    Drink (Integer sorMID, ArrayList<String> mixers){
        sorM = getContext().getString(sorMID);
        this.mixers = mixers;
    }

    /**
     *
     * @return The spirit or mixer chosen
     */
    String getSorM() {
        return sorM;
    }

    /**
     *
     * @return What mixes well with the sorM
     */
    ArrayList<String> getMixers() {
        return mixers;
    }
}
