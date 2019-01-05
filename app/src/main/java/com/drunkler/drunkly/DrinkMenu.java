package com.drunkler.drunkly;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.drunkler.drunkly.App.getContext;
import static com.drunkler.drunkly.R.string;

/**
 * DrinkMenu creates and orders Drinks
 *
 * @author Oscar
 */
class DrinkMenu {
    private HashMap<String, ArrayList<Drink>> drinkMap; //HashMap for the each drink and it's mixers
    private ArrayList<String> alcoholCategories; // A String ArrayList holding the alcohol drink category
    private ArrayList<String> softDrinkCategories; // Same for the soft drinks

    private ArrayList<String> drinkCategories; //ArrayList to hold the category

    /**
     * Creating the DrinkMenu
     *
     * Has access to createDrinks()
     */
    DrinkMenu() {
        drinkMap = new HashMap<>();

        alcoholCategories = new ArrayList<>();

        softDrinkCategories = new ArrayList<>();

        drinkCategories = new ArrayList<>();
        createDrinks();
    }

    /**
     * Create the drinkMenu
     */
    @SuppressLint("NewApi")
    private void createDrinks() {
        //String array of every mixer fo every spirit
        String[] ginMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.mango), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.lemonade) + "\n", getContext().getString(string.ginger), getContext().getString(string.tonic)};
        String[] flavoredGinMixersA = {getContext().getString(string.multi) + "\n", getContext().getString(string.lemonade), getContext().getString(string.soda) + "\n", getContext().getString(string.tonic)};
        String[] whiteRumMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.multi), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.coconutWater), getContext().getString(string.straight)};
        String[] spicedRumMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.multi), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.ginger), getContext().getString(string.straight)};
        String[] darkRumMixersA = {getContext().getString(string.apple), getContext().getString(string.multi), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola) + "\n", getContext().getString(string.coconutWater), getContext().getString(string.ginger), getContext().getString(string.straight)};
        String[] vodkaMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry),  getContext().getString(string.multi), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.energy), getContext().getString(string.lemonade) + "\n", getContext().getString(string.tonic)};
        //String[] vodkaVanillaMixersA = vodkaMixersA;
        String[] scotchMixersA = {getContext().getString(string.cola) + "\n", getContext().getString(string.ginger), getContext().getString(string.straight), getContext().getString(string.water)};
        String[] bourbonMixersA = {getContext().getString(string.cola) + "\n", getContext().getString(string.ginger), getContext().getString(string.straight), getContext().getString(string.water)};
        String[] otherWhiskeyA = {getContext().getString(string.cola) + "\n", getContext().getString(string.straight), getContext().getString(string.water)};
        String[] liqueursMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.mango), getContext().getString(string.multi), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.straight), getContext().getString(string.tonic) };
        String[] amarettoMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.mango), getContext().getString(string.multi), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.coconutWater), getContext().getString(string.straight)};
        String[] peachSchnappsMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.mango), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.straight)};
        String[] coconutLiqueurMixersA = {getContext().getString(string.apple), getContext().getString(string.cranberry), getContext().getString(string.grapefruit), getContext().getString(string.mango), getContext().getString(string.orange), getContext().getString(string.pineapple) + "\n", getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.straight)};
        String[] cognacMixersA = {getContext().getString(string.cola), getContext().getString(string.lemonade) + "\n", getContext().getString(string.soda) + "\n", getContext().getString(string.straight), getContext().getString(string.water)};
        String[] tequilaMixersA = {getContext().getString(string.pineapple) + "\n", getContext().getString(string.salt), getContext().getString(string.straight)};
        String[] jagerMixersA = {getContext().getString(string.energy) + "\n", getContext().getString(string.straight)};

        //Convert the Arrays to ArrayLists
        ArrayList<String> ginMixer = new ArrayList<>(Arrays.asList(ginMixersA));
        ArrayList<String> flavouredGinMixers = new ArrayList<>(Arrays.asList(flavoredGinMixersA));
        ArrayList<String> whiteRumMixers = new ArrayList<>(Arrays.asList(whiteRumMixersA));
        ArrayList<String> spicedRumMixers = new ArrayList<>(Arrays.asList(spicedRumMixersA));
        ArrayList<String> darkRumMixers = new ArrayList<>(Arrays.asList(darkRumMixersA));
        ArrayList<String> vodkaMixers = new ArrayList<>(Arrays.asList(vodkaMixersA));
        ArrayList<String> vodkaVanillaMixers = new ArrayList<>(Arrays.asList(vodkaMixersA));
        ArrayList<String> scotchMixers = new ArrayList<>(Arrays.asList(scotchMixersA));
        ArrayList<String> bourbonMixers = new ArrayList<>(Arrays.asList(bourbonMixersA));
        ArrayList<String> otherWhiskeyMixers = new ArrayList<>(Arrays.asList(otherWhiskeyA));
        ArrayList<String> liqueursMixers = new ArrayList<>(Arrays.asList(liqueursMixersA));
        ArrayList<String> amarettoMixers = new ArrayList<>(Arrays.asList(amarettoMixersA));
        ArrayList<String> peachSchnappsMixers = new ArrayList<>(Arrays.asList(peachSchnappsMixersA));
        ArrayList<String> coconutLiqueurMixers = new ArrayList<>(Arrays.asList(coconutLiqueurMixersA));
        ArrayList<String> cognacMixers = new ArrayList<>(Arrays.asList(cognacMixersA));
        ArrayList<String> tequilaMixers = new ArrayList<>(Arrays.asList(tequilaMixersA));
        ArrayList<String> jagerMixers = new ArrayList<>(Arrays.asList(jagerMixersA));

        //Create the drinks
        Drink gin = new Drink(string.gin, ginMixer);
        Drink flavoredGin = new Drink(string.flavouredGin, flavouredGinMixers);
        Drink whiteRum = new Drink(string.whiteRum, whiteRumMixers);
        Drink spicedRum = new Drink(string.spicedRum, spicedRumMixers);
        Drink darkRum = new Drink(string.darkRum, darkRumMixers);
        Drink vodka = new Drink(string.vodka, vodkaMixers);
        Drink vodkaVanilla = new Drink(string.vodkaVanilla, vodkaVanillaMixers);
        Drink scotch = new Drink(string.scotch, scotchMixers);
        Drink bourbon = new Drink(string.bourbon, bourbonMixers);
        Drink otherWhiskey = new Drink(string.otherWhiskey, otherWhiskeyMixers);
        Drink liqueurs = new Drink(string.liqueurs, liqueursMixers);
        Drink amaretto = new Drink(string.Amaretto, amarettoMixers);
        Drink peachSchnapps = new Drink(string.schnapps, peachSchnappsMixers);
        Drink coconutLiqueur = new Drink(string.cocoRum, coconutLiqueurMixers);
        Drink cognac = new Drink(string.cognac, cognacMixers);
        Drink tequila = new Drink(string.tequila, tequilaMixers);
        Drink jager = new Drink(string.jager, jagerMixers);

        //Create the category of each drink
        ArrayList<Drink> ginList = new ArrayList<>();
        ginList.add(gin);
        ginList.add(flavoredGin);

        ArrayList<Drink> rumList = new ArrayList<>();
        rumList.add(whiteRum);
        rumList.add(spicedRum);
        rumList.add(darkRum);

        ArrayList<Drink> vodkaList = new ArrayList<>();
        vodkaList.add(vodka);
        vodkaList.add(vodkaVanilla);

        ArrayList<Drink> whiskeyList = new ArrayList<>();
        whiskeyList.add(scotch);
        whiskeyList.add(bourbon);
        whiskeyList.add(otherWhiskey);

        ArrayList<Drink> otherList = new ArrayList<>();
        otherList.add(liqueurs);
        otherList.add(cognac);
        otherList.add(amaretto);
        otherList.add(coconutLiqueur);
        otherList.add(peachSchnapps);
        otherList.add(tequila);
        otherList.add(jager);

        //Create the maps with the title of each category and the drinks they hold
        drinkMap.put(getContext().getString(string.gin), ginList);
        drinkMap.put(getContext().getString(string.rum), rumList);
        drinkMap.put(getContext().getString(string.vodka), vodkaList);
        drinkMap.put(getContext().getString(string.whiskey), whiskeyList);
        drinkMap.put(getContext().getString(string.other), otherList);

        alcoholCategories.add(getContext().getString(string.gin));
        alcoholCategories.add(getContext().getString(string.rum));
        alcoholCategories.add(getContext().getString(string.vodka));
        alcoholCategories.add(getContext().getString(string.whiskey));
        alcoholCategories.add(getContext().getString(string.other));


        //******
        //CREATING THE MIXERS
        // ********
        //ArrayList containing every soft drinks
        ArrayList<String> allMixers = new ArrayList<>();
        allMixers.add(getContext().getString(string.tonic));
        allMixers.add(getContext().getString(string.lemonade));
        allMixers.add(getContext().getString(string.orange));
        allMixers.add(getContext().getString(string.mango));
        allMixers.add(getContext().getString(string.pineapple));
        allMixers.add(getContext().getString(string.apple));
        allMixers.add(getContext().getString(string.cranberry));
        allMixers.add(getContext().getString(string.grapefruit));
        allMixers.add(getContext().getString(string.ginger));
        allMixers.add(getContext().getString(string.soda));
        allMixers.add(getContext().getString(string.multi));
        allMixers.add(getContext().getString(string.straight));
        allMixers.add(getContext().getString(string.cola));
        allMixers.add(getContext().getString(string.coconutWater));
        allMixers.add(getContext().getString(string.energy));
        allMixers.add(getContext().getString(string.salt));
        allMixers.add(getContext().getString(string.water));

        //Create an ArrayList for each soft drink
        ArrayList<String> tonicMixers = new ArrayList<>();
        ArrayList<String> lemonadeMixers = new ArrayList<>();
        ArrayList<String> orangeMixers = new ArrayList<>();
        ArrayList<String> mangoMixers = new ArrayList<>();
        ArrayList<String> pineappleMixers = new ArrayList<>();
        ArrayList<String> appleMixers = new ArrayList<>();
        ArrayList<String> cranberryMixers = new ArrayList<>();
        ArrayList<String> grapefruitMixers = new ArrayList<>();
        ArrayList<String> gingerMixers = new ArrayList<>();
        ArrayList<String> sodaMixers = new ArrayList<>();
        ArrayList<String> multiMixers = new ArrayList<>();
        ArrayList<String> noMixers = new ArrayList<>();
        ArrayList<String> colaMixers = new ArrayList<>();
        ArrayList<String> cocoMixers = new ArrayList<>();
        ArrayList<String> energyMixers = new ArrayList<>();
        ArrayList<String> saltMixers = new ArrayList<>();
        ArrayList<String> waterMixers = new ArrayList<>();

        //Creating an ArrayList to hold every soft drinks' array
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list.add(tonicMixers);
        list.add(lemonadeMixers);
        list.add(orangeMixers);
        list.add(mangoMixers);
        list.add(pineappleMixers);
        list.add(appleMixers);
        list.add(cranberryMixers);
        list.add(grapefruitMixers);
        list.add(gingerMixers);
        list.add(sodaMixers);
        list.add(multiMixers);
        list.add(noMixers);
        list.add(colaMixers);
        list.add(cocoMixers);
        list.add(energyMixers);
        list.add(saltMixers);
        list.add(waterMixers);

        // Add to each soft drink array, the spirits recommended with it
        for (int i = 0; i< list.size(); i++) {
            String mixer = allMixers.get(i).trim();

            for (String mixer1 : ginMixer) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.gin));
                }
            }
            for (String mixer1 : flavouredGinMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.flavouredGin));
                }
            }
            for (String mixer1 : whiteRumMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.whiteRum));
                }
            }
            for (String mixer1 : spicedRumMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.spicedRum));
                }
            }
            for (String mixer1 : darkRumMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.darkRum));
                }
            }
            for (String mixer1 : vodkaMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.vodka));
                }
            }
            for (String mixer1 : vodkaVanillaMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.vodkaVanilla));
                }
            }
            for (String mixer1 : scotchMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.scotch));
                }
            }
            for (String mixer1 : bourbonMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.bourbon));
                }
            }
            for (String mixer1 : otherWhiskeyMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.otherWhiskey));
                }
            }
            for (String mixer1 : liqueursMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.liqueurs));
                }
            }
            for (String mixer1 : cognacMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.cognac));
                }
            }
            for (String mixer1 : amarettoMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.Amaretto));
                }
            }
            for (String mixer1 : coconutLiqueurMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.cocoRum));
                }
            }
            for (String mixer1 : peachSchnappsMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.schnapps));
                }
            }
            for (String mixer1 : tequilaMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.tequila));
                }
            }
            for (String mixer1 : jagerMixers) {
                if (mixer1.trim().equals(mixer)) {
                    list.get(i).add("- " + getContext().getString(string.jager));
                }
            }
        }

        // Create each soft drink
        Drink tonic = new Drink(string.tonicS, tonicMixers);
        Drink lemonade = new Drink(string.lemonadeS, lemonadeMixers);
        Drink orange = new Drink(string.orangeS, orangeMixers);
        Drink mango = new Drink(string.mangoS, mangoMixers);
        Drink pineapple = new Drink(string.pineappleS, pineappleMixers);
        Drink apple = new Drink(string.appleS, appleMixers);
        Drink cranberry = new Drink(string.cranberryS,cranberryMixers);
        Drink grapefruit = new Drink(string.grapefruitS, grapefruitMixers);
        Drink ginger = new Drink(string.gingerS, gingerMixers);
        Drink soda = new Drink(string.sodaS, sodaMixers);
        Drink multi = new Drink(string.multiS, multiMixers);
        Drink no = new Drink(string.straightS, noMixers);
        Drink cola = new Drink(string.colaS, colaMixers);
        Drink coconut = new Drink(string.coconutWaterS, cocoMixers);
        Drink energy = new Drink(string.energyS, energyMixers);
        Drink salt = new Drink(string.saltS, saltMixers);
        Drink water = new Drink(string.waterS, waterMixers);

        // Create the category for each soft
        ArrayList<Drink> juicesList =  new ArrayList<>();
        juicesList.add(orange);
        juicesList.add(mango);
        juicesList.add(pineapple);
        juicesList.add(apple);
        juicesList.add(cranberry);
        juicesList.add(grapefruit);
        juicesList.add(multi);

        Collections.sort(juicesList, Comparator.comparing(Drink::getSorM));

        ArrayList<Drink> sodaList = new ArrayList<>();
        sodaList.add(soda);
        sodaList.add(cola);
        sodaList.add(lemonade);
        sodaList.add(energy);

        Collections.sort(sodaList, Comparator.comparing(Drink::getSorM));

        ArrayList<Drink> otherSoftList = new ArrayList<>();
        otherSoftList.add(tonic);
        otherSoftList.add(ginger);
        otherSoftList.add(no);
        otherSoftList.add(coconut);
        otherSoftList.add(salt);
        otherSoftList.add(water);

        Collections.sort(otherSoftList, Comparator.comparing(Drink::getSorM));

        //Create the hashMap for each soft category
        drinkMap.put(getContext().getString(string.juices), juicesList);
        drinkMap.put(getContext().getString(string.sodas), sodaList);
        drinkMap.put(getContext().getString(string.others), otherSoftList);

        softDrinkCategories.add(getContext().getString(string.juices));
        softDrinkCategories.add(getContext().getString(string.sodas));
        softDrinkCategories.add(getContext().getString(string.others));


        drinkCategories.clear();
        drinkCategories.addAll(alcoholCategories);
    }

    /**
     * @return The categories of each drink
     */
    ArrayList<String> getDrinkCategories() {
        return drinkCategories;
    }

    /**
     * @param drink The spirit or mixer we are using
     * @return the mixers used for the drink
     */
    ArrayList<Drink> getDrinkMixers(String drink) {
        return new ArrayList<>(drinkMap.get(drink));
    }

    /**
     * Organize the drinks by soft drinks
     */
    void setAsSoft() {
        drinkCategories.clear();
        drinkCategories.addAll(softDrinkCategories);
    }

    /**
     * Organize the drinks by alcohols
     */
    void setAsAlcohol() {
        drinkCategories.clear();
        drinkCategories.addAll(alcoholCategories);
    }
}
