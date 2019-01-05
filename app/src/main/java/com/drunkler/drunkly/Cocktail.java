package com.drunkler.drunkly;

import static com.drunkler.drunkly.App.getContext;

/**
 * The Cocktail Class holds every information on a cocktail
 *
 * @author Oscar
 */
class Cocktail {
    private String name, ingredients, recipe; // The strings holding the name, ingredients and recipe of the cocktail
    private double alcohol; // The equivalent volume of 40% alcohol for 1L
    private int nSpirits; // The number of spirits needed to make the cocktail

    /**
     * Creating a cocktail
     * @param name The wanted name for the cocktail
     * @param ingredientsId The needed ingredients
     */
    Cocktail(String name, Integer ingredientsId) {
        this.name = name;
        ingredients = getContext().getString(ingredientsId);
    }

    /**
     * @return the name of the cocktail
     */
    public String getName() {
        return name;
    }

    /**
     * @return the ingredients of the cocktail
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Volume of 40% alcohol for 1000mL in the cocktail
     * @return the alcohol concentration
     */
    public double getAlcohol() {
        return alcohol;
    }

    /**
     * Volume in mL of 40% alcohol for 1000mL of this cocktail
     * @param alcohol the Volume (in mL) of 40% alcohol contained in 1000mL of this cocktail
     */
    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * The recipe of the cocktail
     * @param recipeId The recipe of this cocktail
     */
    public void setRecipe(Integer recipeId) {
        recipe = getContext().getString(recipeId);
    }

    /**
     * @return the Recipe of this cocktail
     */
    public String getRecipe() {
        return recipe;
    }

    /**
     *
     * @return The number of spirits used for this cocktail
     */
    int getnSpirits() {
        return nSpirits;
    }

    /**
     *
     * @param nSpirits the number of spirits used to make this cocktail
     */
    void setnSpirits(int nSpirits) {
        this.nSpirits = nSpirits;
    }
}
