package com.drunkler.drunkly;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.drunkler.drunkly.R.string;

/**
 * The CocktailMenu holds lists for cocktails, creates cocktails and defines them
 *
 * @author Oscar
 */
class CocktailMenu {
    private ArrayList<Cocktail> cockMenu; //Holds every cocktails
    private ArrayList<Cocktail> specCock; // Holds the special unofficial cocktails
    //private ArrayList<Cocktail> newCocks; // Holds the new cocktails added to the menu


    private Cocktail vinscar, manos, magascar, ginberry, tuity, island, martini, beach, punch, rain;
    private Cocktail woo, lagoon, godfather, mojito, colada, margarita, daiquiri, mai;
    private Cocktail caiprinha, caipiroska, cosmos, moscow, bloody, zombie;

    //TODO: Creating special cocktails
    // SPECIAL HALLOWEEN
    //private Cocktail spooky, dark, mask, vBlood, boneyard, ghoule, graveyard;

    CocktailMenu() {
        cockMenu = new ArrayList<>();
        specCock = new ArrayList<>();
        //newCocks = new ArrayList<>();
        createCocks();
        addCocks();
    }

    /**
     * Creates the cocktails
     *
     * Has access to
     *          setAlcoholLevels()
     *          setRecipes()
     */
    private void createCocks() {

        vinscar = new Cocktail("Vinscar ⭐", string.vinscarS);
        manos = new Cocktail("Manos ⭐ ", string.manosS);
        magascar = new Cocktail("Magascar ⭐", string.magascarS);
        ginberry = new Cocktail("Ginberry Fizz", string.ginberryS);
        tuity = new Cocktail("Tuity Fruity", string.tuityS);
        island = new Cocktail("Long Island Iced Tea", string.islandS);
        martini = new Cocktail("Porn Star Martini", string.martiniS);
        beach = new Cocktail("Sex On The Beach", string.beachS);
        punch = new Cocktail("Rum Punch", string.punchS);
        rain = new Cocktail("Purple Rain", string.rainS);
        woo = new Cocktail("Woo Woo", string.wooS);
        lagoon = new Cocktail("Blue Lagoon", string.lagoonS);
        godfather = new Cocktail("GodFather", string.godfatherS);
        mojito = new Cocktail("Mojito", string.mojitoS);
        colada = new Cocktail("Piña Colada", string.coladaS);
        margarita = new Cocktail("Margarita", string.margaritaS);
        daiquiri = new Cocktail("Daiquiri", string.daiquiriS);
        mai = new Cocktail("Mai Tai", string.maiS);
        caiprinha = new Cocktail("Caïpirinha", string.caiprinhaS);
        caipiroska = new Cocktail("Caïpiroska", string.caipiroskaS);
        cosmos = new Cocktail("Cosmopolitan", string.cosmosS);
        moscow = new Cocktail("Moscow Mule", string.moscowS);
        bloody = new Cocktail("Bloody Marry", string.bloodyS);
        zombie= new Cocktail("Zombie", string.zombieS);

//TODO: Creating special cocktails
        // SPECIAL HALLOWEEN
        //spooky = new Cocktail("\uD83C\uDF83 Spooky Sunrise \uD83C\uDF83", string.spookyIngredients);
        //dark = new Cocktail("\uD83C\uDF83 Dark & Stormy \uD83C\uDF83", string.darkIngredients);
        //mask = new Cocktail("\uD83C\uDF83 Masquerade \uD83C\uDF83", string.maskIngredients);
        //vBlood = new Cocktail("\uD83C\uDF83 V-Blood \uD83C\uDF83", string.vBloodIngredients);
        //boneyard = new Cocktail("\uD83C\uDF83 The Boneyard \uD83C\uDF83", string.boneyardIngredients);
        //ghoule = new Cocktail("\uD83C\uDF83 Ghoulish Gin-ger Ale \uD83C\uDF83", string.ghouleIngredients);
        //graveyard = new Cocktail("\uD83C\uDF83 Graveyard Gruel \uD83C\uDF83", string.graveyardIngredients);

        setAlcoholLevels();
        setRecipes();

    }

    /**
     * Set the equivalent volume of 40% alcohol in the cocktail and the number of alcohols needed for the recipe
     */
    private void setAlcoholLevels() {
        vinscar.setAlcohol(1000.00);
        manos.setAlcohol(975.00);
        magascar.setAlcohol(950.00);
        ginberry.setAlcohol(135.00);
        tuity.setAlcohol(95.00);
        island.setAlcohol(238.13);
        martini.setAlcohol(200.00);
        beach.setAlcohol(138.75);
        punch.setAlcohol(146.25);
        rain.setAlcohol(143.75);
        woo.setAlcohol(138.75);
        lagoon.setAlcohol(143.75);
        godfather.setAlcohol(170.00);
        mojito.setAlcohol(187.50);
        colada.setAlcohol(146.25);
        margarita.setAlcohol(195.00);
        daiquiri.setAlcohol(187.50);
        mai.setAlcohol(290.63);
        caiprinha.setAlcohol(100.0);
        caipiroska.setAlcohol(187.5);
        cosmos.setAlcohol(190.625);
        moscow.setAlcohol(187.5);
        bloody.setAlcohol(187.5);
        zombie.setAlcohol(330.25);

//TODO: Setting the alcohol volume for special cocktails
        // SPECIAL HALLOWEEN
        //spooky.setAlcohol(300.0);
        //dark.setAlcohol(600.0);
        //mask.setAlcohol(523.43);
        //vBlood.setAlcohol(187.5);
        //boneyard.setAlcohol(700.0);
        //ghoule.setAlcohol(300.0);
        //graveyard.setAlcohol(519.375);

        vinscar.setnSpirits(10);
        manos.setnSpirits(1);
        magascar.setnSpirits(1);
        ginberry.setnSpirits(2);
        tuity.setnSpirits(2);
        island.setnSpirits(5);
        martini.setnSpirits(1);
        beach.setnSpirits(2);
        punch.setnSpirits(2);
        rain.setnSpirits(2);
        woo.setnSpirits(2);
        lagoon.setnSpirits(2);
        godfather.setnSpirits(2);
        mojito.setnSpirits(1);
        colada.setnSpirits(2);
        margarita.setnSpirits(2);
        daiquiri.setnSpirits(1);
        mai.setnSpirits(3);
        caiprinha.setnSpirits(1);
        caipiroska.setnSpirits(1);
        cosmos.setnSpirits(2);
        moscow.setnSpirits(1);
        bloody.setnSpirits(1);
        zombie.setnSpirits(6);

//TODO: Ssetting the number of spirits for special cocktails
        // SPECIAL HALLOWEEN
        //spooky.setnSpirits(1);
        //dark.setnSpirits(1);
        //mask.setnSpirits(2);
        //vBlood.setnSpirits(1);
        //boneyard.setnSpirits(5);
        //ghoule.setnSpirits(1);
        //graveyard.setnSpirits(2);
    }

    /**
     * Set the recipes for each alcohol
     */
    private void setRecipes() {
        vinscar.setRecipe(string.vinscarR);
        manos.setRecipe(string.manosR);
        magascar.setRecipe(string.magascarR);
        ginberry.setRecipe(string.ginberryR);
        tuity.setRecipe(string.tuityR);
        island.setRecipe(string.islandR);
        martini.setRecipe(string.martiniR);
        beach.setRecipe(string.beachR);
        punch.setRecipe(string.punchR);
        rain.setRecipe(string.rainR);
        woo.setRecipe(string.wooR);
        lagoon.setRecipe(string.lagoonR);
        godfather.setRecipe(string.godfatherR);
        mojito.setRecipe(string.mojitoR);
        colada.setRecipe(string.coladaR);
        margarita.setRecipe(string.margaritaR);
        daiquiri.setRecipe(string.daiquiriR);
        mai.setRecipe(string.maiR);
        caiprinha.setRecipe(string.caiprinhaR);
        caipiroska.setRecipe(string.caipiroskaR);
        cosmos.setRecipe(string.cosmosR);
        moscow.setRecipe(string.moscowR);
        bloody.setRecipe(string.bloodyR);
        zombie.setRecipe(string.zombieR);

//TODO: Making the recipe of the special cocktails
        // SPECIAL HALLOWEEN
        //spooky.setRecipe(string.spookyRecipe);
        //dark.setRecipe(string.darkRecipe);
        //mask.setRecipe(string.maskRecipe);
        //vBlood.setRecipe(string.vBloodRecipe);
        //boneyard.setRecipe(string.boneyardRecipe);
        //ghoule.setRecipe(string.ghouleRecipe);
        //graveyard.setRecipe(string.graveyardRecipe);
    }

    /**
     * Add every cocktail to lists in the default order
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void addCocks() {
        specCock.add(vinscar);
        specCock.add(manos);
        specCock.add(magascar);

//TODO: Adding the special cocktails
        //SPECIAL HALLOWEEN
        //specCock.add(spooky);
        //specCock.add(dark);
        //specCock.add(mask);
        //specCock.add(vBlood);
        //specCock.add(boneyard);
        //specCock.add(ghoule);
        //specCock.add(graveyard);

        cockMenu.add(ginberry);
        cockMenu.add(tuity);
        cockMenu.add(island);
        cockMenu.add(martini);
        cockMenu.add(beach);
        cockMenu.add(punch);
        cockMenu.add(rain);
        cockMenu.add(woo);
        cockMenu.add(lagoon);
        cockMenu.add(godfather);
        cockMenu.add(mojito);
        cockMenu.add(colada);
        cockMenu.add(margarita);
        cockMenu.add(daiquiri);
        cockMenu.add(mai);
        cockMenu.add(caiprinha);
        cockMenu.add(caipiroska);
        cockMenu.add(cosmos);
        cockMenu.add(moscow);
        cockMenu.add(bloody);
        cockMenu.add(zombie);


        // Sorts the list alphabetically
        Collections.sort(cockMenu, Comparator.comparing(Cocktail::getName));
        //Collections.sort(newCocks, Comparator.comparing(Cocktail::getName));
        Collections.sort(specCock, Comparator.comparing(Cocktail::getName));

        //specCock.addAll(newCocks);
        specCock.addAll(cockMenu);
    }

    /**
     * Sorts the cocktails in a default way
     *
     * Has access to createCocks(), addCocks()
     */
    void defaultSort() {
        specCock.clear();
        //newCocks.clear();
        cockMenu.clear();
        createCocks();
        addCocks();
    }

    /**
     * Sort the cocktails alphabetically
     */
    @TargetApi(Build.VERSION_CODES.N)
    void nameSort() {
        Collections.sort(specCock, Comparator.comparing(Cocktail::getName));
    }

    /**
     * Sorts the cocktails by alcohol volume, least to most
     *
     * Has access to nameSort()
     */
    @TargetApi(Build.VERSION_CODES.N)
    void leastAlcoholSort() {
        nameSort();
        Collections.sort(specCock, Comparator.comparingDouble(Cocktail::getAlcohol));
    }

    /**
     * Sorts the cocktails by number of spirits needed, least to most
     *
     * Has access to nameSort()
     */
    @TargetApi(Build.VERSION_CODES.N)
    void numberOfSpirits() {
        nameSort();
        Collections.sort(specCock, Comparator.comparing(Cocktail::getnSpirits));
    }

    /**
     * @return The list of every cocktails
      */
    ArrayList<Cocktail> getCocks() {
        return specCock;
    }
}