package com.drunkler.drunkly;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import static com.drunkler.drunkly.App.getContext;
import static com.drunkler.drunkly.R.string;

/**
 * A GameCard is a special type of card, it can hold minigames
 * Used for the card 8 and the Jacks
 *
 * @author Oscar
 */
class GameCard extends Card {
    private List<MiniGame> miniGames; // List to hold every minigame
    private List<String> miniGameTitles; // List to hold every minigame's title
    private HashMap<String, String> miniGamesString; // HashMap to map the name of the minigame with it's explications

    /**
     * Add MiniGames to the Card
     *
     * Leads to: addMiniGames()
     *           setDescription()
     *           setValue(int )
     *           setMiniGamesTitles(List<String>)
     *           setMiniGames(HasMap<String, String>)
     */
    GameCard(Integer description)
    {
        // initialise instance variables
        super(description);
        miniGames = new ArrayList<>();
        miniGameTitles = new ArrayList<>();
        miniGamesString = new HashMap<>();
        addMiniGames();
        setDescription();
        setValue(8);
        setMiniGamesTitles(miniGameTitles);
        setMiniGames(miniGamesString);
    }
    /**
     * Create and add MiniGames to the card
     */
    private void addMiniGames()
    {
        MiniGame squirrel, pim, clap, twenty;

        // create the miniGames
        squirrel = new MiniGame(getContext().getString(string.squirrelGame));
        pim = new MiniGame(getContext().getString(string.pimpimGame));
        clap = new MiniGame(getContext().getString(string.clapGame));
        twenty = new MiniGame(getContext().getString(string.twentyPlusOneGame));

        // create the explanation of the games
        squirrel.putRules(getContext().getString(string.squirrelRules));
        squirrel.addExample(getContext().getString(string.squirrelExample));
        squirrel.addNote(getContext().getString(string.squirrelNote));

        pim.putRules(getContext().getString(string.pimRules));
        pim.addExample(getContext().getString(string.pimExample));
        pim.addNote(getContext().getString(string.pimNote));

        clap.putRules(getContext().getString(string.clapRules));
        clap.addExample(getContext().getString(string.clapExample));
        clap.addNote(getContext().getString(string.clapNote));

        twenty.putRules(getContext().getString(string.twentyRules));
        twenty.addNote(getContext().getString(string.twentyNote));

        // Populate the different lists and HashMaps
        miniGames.add(squirrel);
        miniGames.add(pim);
        miniGames.add(clap);
        miniGames.add(twenty);

        miniGameTitles.add(squirrel.getName());
        miniGameTitles.add(pim.getName());
        miniGameTitles.add(clap.getName());
        miniGameTitles.add(twenty.getName());

        miniGamesString.put(squirrel.getName(), squirrel.getSemiFullDescription());
        miniGamesString.put(pim.getName(), pim.getSemiFullDescription());
        miniGamesString.put(clap.getName(), clap.getSemiFullDescription());
        miniGamesString.put(twenty.getName(), twenty.getSemiFullDescription());
    }

    /**
     * Assigns the rules to each miniGame
     */
    private void setDescription()
    {
        for(int i = 0; i < miniGames.size(); i++) {
            setRulesDetails(miniGames.get(i).getFullDescription() + "\n");
        }
    }

    /**
     * Special method for the jack
     *
     * This method transform the GameCard assigned to normally the 8 to the jack
     *
     * Has access to : setDescription()
     */
    void setJack() {

        setValue(11); // The value of a Jack card is 11

        // Clear every list so far created and populated
        miniGames.clear();
        miniGameTitles.clear();
        miniGamesString.clear();

        //Create the miniGames
        MiniGame fps, buffalo, chicken, drinkorilla, order, international, drinkly;

        fps = new MiniGame(getContext().getString(string.fps));
        buffalo = new MiniGame(getContext().getString(string.buffalo));
        chicken = new MiniGame(getContext().getString(string.chicken));
        drinkorilla = new MiniGame(getContext().getString(string.drinkorilla));
        order = new MiniGame(getContext().getString(string.orderName));
        international = new MiniGame(getContext().getString(string.international));
        drinkly = new MiniGame(getContext().getString(string.drinklerName));

        fps.putRules(getContext().getString(string.fpsRules));
        buffalo.putRules(getContext().getString(string.buffaloRules));
        chicken.putRules(getContext().getString(string.chickenRules));
        drinkorilla.putRules(getContext().getString(string.drinkorillaRules));
        drinkorilla.addExample(getContext().getString(string.drinkorillaExample));
        order.putRules(getContext().getString(string.orderRule));
        order.addNote(getContext().getString(string.orderNote));
        international.putRules(getContext().getString(string.internationalRules));
        drinkly.putRules(getContext().getString(string.drinklerRules));

        // Repopulate the lists with the minigames (that are actually rules now)
        miniGames.add(fps);
        miniGames.add(buffalo);
        miniGames.add(chicken);
        miniGames.add(drinkorilla);
        miniGames.add(order);
        miniGames.add(international);
        miniGames.add(drinkly);

        miniGameTitles.add(fps.getName());
        miniGameTitles.add(buffalo.getName());
        miniGameTitles.add(chicken.getName());
        miniGameTitles.add(drinkorilla.getName());
        miniGameTitles.add(order.getName());
        miniGameTitles.add(international.getName());
        miniGameTitles.add(drinkly.getName());

        miniGamesString.put(fps.getName(), fps.getSemiFullDescription());
        miniGamesString.put(buffalo.getName(), buffalo.getSemiFullDescription());
        miniGamesString.put(chicken.getName(), chicken.getSemiFullDescription());
        miniGamesString.put(drinkorilla.getName(), drinkorilla.getSemiFullDescription());
        miniGamesString.put(order.getName(), order.getSemiFullDescription());
        miniGamesString.put(international.getName(), international.getSemiFullDescription());
        miniGamesString.put(drinkly.getName(), drinkly.getSemiFullDescription());

        setDescription(); // Set the explanations of each rule
    }
}