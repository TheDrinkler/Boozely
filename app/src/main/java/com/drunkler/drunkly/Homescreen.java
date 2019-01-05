package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Homescreen Class dictates how the Homescreen activity behaves
 * @author Oscar
 */
public class Homescreen extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter simpleGestureFilter; // Allows to control the screen with swipe gestures

    private Button spDrinkButt; // Button calling the method to open the cocktail menu
    private Button drinkButt; // Button calling the method to open the drinks & tricks menu
    private Button boozeledGame; // Button calling the method to start the game
    private FloatingActionButton languagePref; // Button to change the language
    private ImageView jokeButt; // ImageView acting like a button to call the method to offer the user the choice of speaking with me
    private TextView currentQuote; // TextView to hold the quotes that are being displayed on the activity_homescreen
    private TextView quoteBackground; // TextView to add a black background behind the quotes

    //TODO: Specials
    //private ImageView pumpkin1; // A pumpkin image facing right
   // private ImageView halloweenBanner; // A halloween banner
    //private ImageView webCorner; // A spiderweb in the corner
    private TextView happyHallo; // Happy halloween!
    private ImageView newYearBanner; // Happy new year banner


    private AdView mAdView; // AdView for cash

    private ArrayList<String> quotes; // ArrayList to hold every quotes
    private int y; // Integer that will be used to make sure the same quote isn't displayed twice in a row
    public boolean isFinished; // Boolean to check if the user left the screen is not (prevents from keep displaying the quotes for to save battery and memory)
    private String body; // The body of the email if the user decides to send one
    private String name, ingredients, recipe, spirit, mixers, rules; // The different strings that will hold the value of different variables needed for submitting a cocktail or a drink
    private String quote; // Holds the quote


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createWaiver();
    }

    /**
     * Waiver to warn the user what is about to happen
     *
     * Either gives access to the game or closes it
     */
    @SuppressLint("ClickableViewAccessibility")
    private void createWaiver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Homescreen.this);
        builder.setTitle(R.string.waiverTitleHome)
               .setMessage(getString(R.string.waiverMessageHome) + "\n\n" + "                            \uD83E\uDD14\uD83D\uDE15")
               .setPositiveButton(R.string.waiverYesHome, (dialog, which) -> startBoozely()
               )
               .setNegativeButton(R.string.waiverNoHome, ((dialog, which) -> finish()));

        builder.setCancelable(false);
        AlertDialog waiver = builder.create();
        waiver.show();

        Button negativeButton = waiver.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

//TODO: Setting the view
   /**
    * SPECIAL HALLOWEEN
    */
   private void setPumpkins() {
   //     pumpkin1 = findViewById(R.id.pumpkin1);
   //     halloweenBanner = findViewById(R.id.halloweenbanner);
   //     webCorner = findViewById(R.id.webcorner);
        happyHallo = findViewById(R.id.happyNewYearText);
        newYearBanner = findViewById(R.id.newyearbanner);

   //     pumpkin1.bringToFront();
   //     webCorner.bringToFront();
   }

//TODO: Making it visible
    /**
    * SPECIAL HALLOWEEN
    */
    private void makePumpkinsVisible(){
    //    pumpkin1.setVisibility(View.VISIBLE);
    //    halloweenBanner.setVisibility(View.VISIBLE);
    //    webCorner.setVisibility(View.VISIBLE);
        happyHallo.setVisibility(View.VISIBLE);
        newYearBanner.setVisibility(View.VISIBLE);
    }

//TODO: Making it invisible
   /**
    *  SPECIAL HALLOWEEN
    */
   private void makePumpkinsInvisible(){
    //    pumpkin1.setVisibility(View.INVISIBLE);
    //    halloweenBanner.setVisibility(View.INVISIBLE);
    //    webCorner.setVisibility(View.INVISIBLE);
        happyHallo.setVisibility(View.INVISIBLE);
        newYearBanner.setVisibility(View.INVISIBLE);
    }

    /**
     * Start the app when the user accepts the waiver
     */
    @SuppressLint({"ClickableViewAccessibility", "RestrictedApi"})
    private void startBoozely() {

        setContentView(R.layout.activity_homescreen);

        simpleGestureFilter = new SimpleGestureFilter(Homescreen.this, this); //Initialize the swiping orders

        isFinished = false; // We are still on the Homescreen so keep displaying the quotes
        // Every string is empty atm
        body = "";
        name = "";
        ingredients = "";
        recipe = "";
        spirit = "";
        mixers = "";
        rules = "";

        //TODO: Using the setting the view method
        setPumpkins();

        // Chooses a new quote to display
        currentQuote = findViewById(R.id.dailyQuote);
        quoteBackground = findViewById(R.id.quoteBackground);
        quoteBackground.setBackgroundColor(Color.parseColor("#FF000000"));

        quotes = new ArrayList<>();
        addQuotes(); // Adds every quote to the array list

        // For the add
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest1 = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest1);

        // Initializes and creates the ad
        MobileAds.initialize(this, "ca-app-pub-8818799719074826~6407123349");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.bringToFront();


        // Create a drop-in animation
        Animation titleDropIn = AnimationUtils.loadAnimation(this, R.anim.drop_in);

        //Identify each textView and button on the screen
        TextView title = findViewById(R.id.title);
        languagePref = findViewById(R.id.languagePref);
        spDrinkButt = findViewById(R.id.specDrinksButton);
        jokeButt = findViewById(R.id.JokerButton);
        drinkButt = findViewById(R.id.drinksTipsButton);
        boozeledGame = findViewById(R.id.boozelyTheGameButton);
        title.bringToFront(); // So it covers the other objects

        // Everything is invisible until the animation starts
        currentQuote.setVisibility(View.INVISIBLE);
        quoteBackground.setVisibility(View.INVISIBLE);
        spDrinkButt.setVisibility(View.INVISIBLE);
        jokeButt.setVisibility(View.INVISIBLE);
        drinkButt.setVisibility(View.INVISIBLE);
        boozeledGame.setVisibility(View.INVISIBLE);
        languagePref.setVisibility(View.INVISIBLE);
        mAdView.setVisibility(View.INVISIBLE);

        //TODO: Using the making them invisible method
        makePumpkinsInvisible();

        // Sets and starts the animation to the title
        title.setAnimation(titleDropIn);

        titleDropIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation titleDropIn) { }

            @Override
            public void onAnimationRepeat(Animation titleDropIn) {}

            @Override
            public void onAnimationEnd(Animation titleDropIn) {

                //When the animation ends, make the rest visible and create the effects
                currentQuote.setVisibility(View.VISIBLE);
                quoteBackground.setVisibility(View.VISIBLE);
                quoteBackground.bringToFront();
                currentQuote.bringToFront();
                spDrinkButt.setVisibility(View.VISIBLE);
                jokeButt.setVisibility(View.VISIBLE);
                drinkButt.setVisibility(View.VISIBLE);
                boozeledGame.setVisibility(View.VISIBLE);
                languagePref.setVisibility(View.VISIBLE);
                mAdView.setVisibility(View.VISIBLE);

                //TODO: Using the making them visible method
                makePumpkinsVisible();

                animateButtons(); // Method to create the effects
            }
        });

        //Adding a button click effect to the image
        jokeButt.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ImageView view = (ImageView) v;
                    view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    ImageView view = (ImageView) v;
                    view.getDrawable().clearColorFilter();
                    view.invalidate();
                    break;
                }
                case MotionEvent.ACTION_CANCEL: {
                    ImageView view = (ImageView) v;
                    view.getDrawable().clearColorFilter();
                    view.invalidate();
                    break;
                }
            }
            return false;
        });

        // Creates on-click listeners for the buttons
        spDrinkButt.setOnClickListener(v -> spDrinkButtListener()); // Send to cocktail menu
        jokeButt.setOnClickListener(v -> jokeButtListener()); // prompts for submissions
        drinkButt.setOnClickListener(v -> drinkButtListener());// send to the drinks menu
        boozeledGame.setOnClickListener(v -> boozeledGameListener()); // send to the game
        languagePref.setOnClickListener(v -> showLanguages());

        // Asks the user to input a quote he would like to add when clicking on a quote
        currentQuote.setOnClickListener((View v) -> {

            String subject = getString(R.string.quoteEmailSubject);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.quoteDialogTitle);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint(R.string.quoteInputHint);
            builder.setView(input);

            builder.setPositiveButton(R.string.sendString, (dialog, which) -> {
                quote = input.getText().toString();
                if (quote.trim().equals("")) {
                    // If the user clicks send but hasn't entered anything, inform him
                    Toast toast = Toast.makeText(this, R.string.quoteToast, Toast.LENGTH_SHORT);
                    toast.show();
                    currentQuote.callOnClick();
                }
                else {
                    // If every variable has a value, create the mail
                    body = getString(R.string.quoteBody) + "\n\n" + input.getText().toString();
                    composeEmail(subject, body); // method to create a mail
                    body = "";
                }
            });
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                body = "";
                quote = "";
                dialog.cancel();
            });

            AlertDialog quoteSubmitter = builder.create();
            quoteSubmitter.show();

            Button negativeButton = quoteSubmitter.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
        });

    }

    /**
     * Method to start the cocktail Menu
     */
    private void spDrinkButtListener() {
            Animation rotateLeft = AnimationUtils.loadAnimation(spDrinkButt.getContext(), R.anim.rotate_left);
            spDrinkButt.startAnimation(rotateLeft);
            Intent intent = new Intent(this, CocktailScreen.class);
            isFinished = true; // We are leaving this activity so stop changing the quotes
            startActivity(intent);
    }

    /**
     * Method to add effect when image clicked and leads to the method that prompts the user to submit stuff
     */
    private void jokeButtListener() {
            Animation alpha = AnimationUtils.loadAnimation(jokeButt.getContext(), R.anim.disappear);
            jokeButt.startAnimation(alpha);
            jokeButtMessage(); //
    }

    /**
     * Method to prompt the user to submit stuff
     */
    private void jokeButtMessage(){
        String[] choices = {getString(R.string.feedbackSuggest), getString(R.string.quoteSuggest), getString(R.string.cocktailSuggest), getString(R.string.drinkSuggest), getString(R.string.ruleSuggest), getString(R.string.minigameSuggest)};
        AlertDialog.Builder builder = new AlertDialog.Builder(Homescreen.this);
        builder.setTitle(R.string.sendTheMessageTitle);
        builder.setItems(choices, ((dialog, which) -> submitToDrinkler(which)));
        builder.setPositiveButton(R.string.close, (dialogInterface, i) -> {
        });

        AlertDialog submit = builder.create();
        submit.show();
    }

    /**
     * Method to submit stuff
     * Depending on the choice of the user, send to according method
     * @param which The item clicked by the user
     */
    private void submitToDrinkler(int which){
        switch (which) {
            case 0: {
                submitFeedback(); // To submit feedback
                break;
            }
            case 1: {
                submitQuote(); // To submit a quote
                break;
            }
            case 2: {
                submitCocktail(); // To submit a cocktail
                break;
            }
            case 3: {
                submitDrink(); // To submit a drink
                break;
            }
            case 4: {
                submitRule(); // To submit a rule
                break;
            }
            case 5: {
                submitGame();// To submit a cocktail
                break;
            }
        }
    }

    /**
     * Method to start the Tips & Drinks menu
     */
    private void drinkButtListener() {
            Animation rotateRight = AnimationUtils.loadAnimation(drinkButt.getContext(), R.anim.rotate_right);
            drinkButt.startAnimation(rotateRight);
            Intent intent = new Intent(this, TipsDrinks.class);
            isFinished = true; // Stop displaying the quotes
            startActivity(intent);
    }

    /**
     * Method to start the game
     */
    private void boozeledGameListener() {
            boozeledGame.bringToFront();
            Animation scale = AnimationUtils.loadAnimation(boozeledGame.getContext(), R.anim.big_scale);
            boozeledGame.startAnimation(scale);
            isFinished = true; // Stop having the effects on the quotes
            Intent intent = new Intent(this, OpenScreen.class);
            startActivity(intent);
    }

    /**
     * Creates a dialog that lists every language the user can switch the app to
     *
     * Leads to choseLanguage(int)
     */
    private void showLanguages(){
        String[] languages = {getString(R.string.engLang), getString(R.string.frLang)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.languageChoice)
                .setItems(languages, ((dialog, which) -> choseLanguage(which)))
                .setPositiveButton(R.string.cancel, (dialogInterface, i) -> {});

        AlertDialog languageDialog = builder.create();
        languageDialog.show();
    }

    /**
     * Method to chose what happens when the user selects a language
     * @param which The index of the language selected by the user
     *
     * Leads to setLanguage(String)
     */
    private void choseLanguage(int which) {
        switch (which) {
            case 0:
                setLanguage("en"); // Sets the language of the app in english
                break;
            case 1:
                setLanguage("fr"); // Sets the language of the app in french
                break;
        }
    }

    /**
     * Sets the language of the screen in the string entered, refreshes the activity
     * @param lan The language entered
     */
    private void setLanguage(String lan){
        Locale myLocale = new Locale(lan);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Homescreen.class);
        startActivity(refresh);
        finish();
    }

    /**
     * Method to submit feedback or a message
     *
     * Has access to: composeEmail(String, String)
     *                submitFeedback()
     */
    private void submitFeedback() {
        // Layout that will hold where the user will enter his feedback or message
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // The EditText to hold and record the feedback the user enters
        final EditText message = new EditText(this);
        message.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        message.setHint(R.string.messageHint);
        layout.addView(message);

        // The AlertDialog to show the message and prompts the user to enter stuff
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogFeedbackTitle)
                .setView(layout)
                .setPositiveButton(R.string.sendString, (dialog, which) -> {
                    if (!message.getText().toString().trim().isEmpty()) {
                        // If the user has entered feedback or a message, compose the mail
                        composeEmail(getString(R.string.emailSubjectFeedback), message.getText().toString());
                    }
                    else {
                        // If the user hasn't entered anything, inform him
                        Toast toast = Toast.makeText(this, R.string.toastFeedBack, Toast.LENGTH_SHORT);
                        toast.show();
                        submitFeedback();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> jokeButtMessage());

        AlertDialog feedback = builder.create();
        feedback.show();

        Button negativeButton = feedback.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a quote via the imageButton
     */
    private void submitQuote() {
        // The layout tha will hold the view of the AlertDialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // The EditText that will prompt the user to enter a quote and record the quote
        final EditText message = new EditText(this);
        message.setRawInputType(InputType.TYPE_CLASS_TEXT);
        message.setHint(R.string.quoteInputHint);
        layout.addView(message);

        // The Dialog that will pop-up to prompt the user
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quoteDialogTitle)
                .setView(layout)
                .setPositiveButton(R.string.sendString, ((dialog, which) -> {
                    if (!message.getText().toString().trim().isEmpty()) {
                        // If the user has entered a quote, compose the mail
                        composeEmail(getString(R.string.quoteEmailSubject), getString(R.string.quoteBody) + "\n\n" + message.getText().toString());
                    }
                    else {
                        // If the user tries to send an empty quote, inform  him
                        Toast toast = Toast.makeText(this, R.string.quoteToast, Toast.LENGTH_SHORT);
                        toast.show();
                        submitQuote();
                    }
                }))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> jokeButtMessage()));

        AlertDialog quote = builder.create();
        quote.show();

        Button negativeButton = quote.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a cocktail recipe
     */
    private void submitCocktail(){
        AtomicBoolean hasName = new AtomicBoolean(false); //Boolean value to check if the user entered a name
        AtomicBoolean hasIngredients = new AtomicBoolean(false); //Boolean value to check if the user entered some ingredients
        AtomicBoolean hasRecipe = new AtomicBoolean(false); //Boolean value to check if the user entered the recipe

        // The layout that will hold the edittexts and then be added to the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Subject of the mail
        String subject = getString(R.string.cocktailEmailSubject);

        // EditText where the user can enter the name
        final EditText inputName = new EditText(this);
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);
        inputName.setHint(R.string.cocktailHintName);
        //check if the user has entered a previous value
        if (!name.trim().equals("")) {
            inputName.setText(name);
        }
        //Adding the EditText to the layout
        layout.addView(inputName);

        // EditText where the user can enter the ingredients
        final EditText inputIngredients = new EditText(this);
        inputIngredients.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputIngredients.setHint(R.string.cocktailHintIngredients);
        // Check if the user has entered a previous value
        if (!ingredients.trim().equals("")) {
            inputIngredients.setText(ingredients);
        }
        // Adding the EditText to the layout
        layout.addView(inputIngredients);

        //Edit text where the user can enter the recipe
        final EditText inputRecipe = new EditText(this);
        inputRecipe.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputRecipe.setHint(R.string.cocktailHintRecipe);
        //Check if the user has entered a previous value
        if (!recipe.trim().equals("")) {
            inputRecipe.setText(recipe);
        }
        //Adding the editText to the layout
        layout.addView(inputRecipe);

        //Creating the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cocktailDialogTitle);
        builder.setView(layout);
        builder.setPositiveButton(R.string.sendString, (dialog, which) -> {
            //Assign the values accordingly
            name = inputName.getText().toString().trim();
            ingredients = inputIngredients.getText().toString().trim();
            recipe = inputRecipe.getText().toString().trim();

            if (name.equals("")) {
                //Warn the user he hasn't entered a name
                hasName.set(false);
                Toast toast = Toast.makeText(this, R.string.cocktailToastNoName, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                hasName.set(true);
            }

            if (ingredients.equals("")) {
                //Warn the user he hasn't entered the ingredients...
                hasIngredients.set(false);
                if (name.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.cocktailToastIngredientsNoName, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    //...using the name of the cocktail if he entered one (just for style)
                    Toast toast = Toast.makeText(this, getString(R.string.cocktailToastIngredientsWithName) + " " + name, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                hasIngredients.set(true);
            }

            if (recipe.equals("")) {
                //Warn the user he hasn't entered a recipe...
                hasRecipe.set(false);
                if (name.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.cocktailToastRecipeNoName, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // ... using the name of the cocktail if he entered one (just for style)
                    Toast toast = Toast.makeText(this, getString(R.string.cocktailToastRecipeWithName) + " " + name, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                hasRecipe.set(true);
            }

            if (hasName.get() && hasIngredients.get() && hasRecipe.get()) {
                //If the user has entered a all of the values, send a pre-formatted mail
                body = getString(R.string.cocktailBody) + "\n" + inputName.getText().toString() +
                        "\n\n" + getString(R.string.cocktailIngredients) + "\n" + inputIngredients.getText().toString() +
                        "\n\n" + getString(R.string.cocktailRecipe) + "\n" + inputRecipe.getText().toString();
                composeEmail(subject, body);
                // clear the values
                body = "";
                name = "";
                ingredients = "";
                recipe = "";
            }
            else {
                //save the values to the variables and send back to the dialog
                name = inputName.getText().toString();
                ingredients = inputIngredients.getText().toString();
                recipe = inputRecipe.getText().toString();
                submitCocktail();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            //clear the values and send to previous dialog
            body = "";
            name = "";
            ingredients = "";
            recipe = "";
            jokeButtMessage();
        });

        AlertDialog cocktailSubmitter = builder.create();
        cocktailSubmitter.show();

        // Change the color of the cancel button
        Button negativeButton = cocktailSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a drink
     */
    private void submitDrink(){
        AtomicBoolean hasSpirit = new AtomicBoolean(false); //Boolean value to check if the user entered an alcohol
        AtomicBoolean hasMixers = new AtomicBoolean(false); //Boolean value to check if the user entered some mixers

        // The layout that will hold the edittexts and be added to the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // The subject of the mail
        String subject = getString(R.string.drinkEmailSubject);

        // EditText for the spirit
        final EditText inputSpirit = new EditText(this);
        inputSpirit.setInputType(InputType.TYPE_CLASS_TEXT);
        inputSpirit.setHint(R.string.drinkHintSpirit);
        //Check if the user has previously entered a spirit
        if (!spirit.equals("")) {
            inputSpirit.setText(spirit);
        }
        //Adding the edittext to the layout
        layout.addView(inputSpirit);

        // EditText for the mixers
        final EditText inputMixer = new EditText(this);
        inputMixer.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputMixer.setHint(R.string.drinkHintMixer);
        //Check if the user has previously entered mixers
        if (!mixers.equals("")) {
            inputMixer.setText(mixers);
        }
        // Adding the edittext to the layout
        layout.addView(inputMixer);

        //Creating the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.drinkDialogTitle);
        builder.setView(layout);
        builder.setPositiveButton(R.string.sendString, (dialog, which) -> {
            //Assigning the according values
            spirit = inputSpirit.getText().toString().trim();
            mixers = inputMixer.getText().toString().trim();

            if (spirit.equals("")) {
                hasSpirit.set(false);
                // Informing the user he hasn't entered any spirits
                Toast toast = Toast.makeText(this, R.string.drinkToastNoSpirit, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                hasSpirit.set(true);
            }
            if (mixers.equals("")) {
                hasMixers.set(false);
                // Inform the user he hasn't entered mixers
                Toast toast = Toast.makeText(this, R.string.drinkToastNoMixers, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                hasMixers.set(true);
            }

            if (hasSpirit.get() && hasMixers.get()) {
                // If every value was entered, send the pre-formatted mail and clear the values
                String body = getString(R.string.drinkBody) + "\n" + inputSpirit.getText().toString() +
                        "\n\n" + getString(R.string.drinkMixer) + "\n" + inputMixer.getText().toString();
                composeEmail(subject, body);
                spirit = "";
                mixers = "";
            }
            else {
                // Save the entered values and ask user to add missing values
                spirit = inputSpirit.getText().toString().trim();
                mixers = inputMixer.getText().toString().trim();
                submitDrink();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            //Send back to previous dialog and clear the variables
            jokeButtMessage();
            spirit = "";
            mixers = "";
        });

        AlertDialog drinkSubmitter = builder.create();
        drinkSubmitter.show();

        // Styling the dialog
        Button negativeButton = drinkSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a rule
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitRule()
     *          sendTheMessage()
     */
    private void submitRule() {
        AtomicBoolean hasName = new AtomicBoolean(false); // Boolean value to check if the user entered a name
        AtomicBoolean hasRules = new AtomicBoolean(false);// Boolean value to check if the user entered the rules

        // The layout that will hold the EditTexts and be added to the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //The subject of the mail
        String subject = getString(R.string.emailRuleSubject);

        // Edittext where the user will enter the name of the rule
        final EditText inputRule = new EditText(this);
        inputRule.setInputType(InputType.TYPE_CLASS_TEXT);
        inputRule.setHint(R.string.hintRuleName);
        //Check if the user has already entered a name
        if (!name.equals("")) {
            inputRule.setText(name);
        }
        // Add the text to the layout
        layout.addView(inputRule);

        //Edittext where the user will enter rules
        final EditText inputExplanation = new EditText(this);
        inputExplanation.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputExplanation.setHint(R.string.hintRuleExplain);
        if (!rules.equals("")) {
            inputExplanation.setText(rules);
        }
        layout.addView(inputExplanation);

        //Creating the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ruleDialogTitle);
        builder.setView(layout);
        builder.setPositiveButton(R.string.sendString, (dialog, which) -> {
            // Assigning the according values
            name = inputRule.getText().toString().trim();
            rules = inputExplanation.getText().toString().trim();

            if (name.equals("")) {
                hasName.set(false);
                //Inform that there are no names
                Toast toast = Toast.makeText(this, R.string.toastRuleNoName, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                hasName.set(true);
            }
            if (rules.equals("")) {
                hasRules.set(false);
                //inform that there are no explanations of the rule...
                if (hasName.get()) {
                    //...using the name of the rule if entered (just for style)
                    Toast.makeText(this, getString(R.string.toastRuleExplainWithName) + " " + name, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast toast = Toast.makeText(this, R.string.toastRuleExplainNoName, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                hasRules.set(true);
            }

            if (hasName.get() && hasRules.get()) {
                // If every value is valid, send the pre-formatted mail. clear the values
                String body = getString(R.string.bodyNameRule) + inputRule.getText().toString() +
                        getString(R.string.bodyExplainRule) + inputExplanation.getText().toString();
                composeEmail(subject, body);
                name = "";
                rules = "";
            }
            else {
                // Save the values already entered and reload the dialog
                name = inputRule.getText().toString().trim();
                rules = inputExplanation.getText().toString().trim();
                submitRule();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            // Send to previous dialog and clear the values
            jokeButtMessage();
            name = "";
            rules = "";
        });

        AlertDialog drinkSubmitter = builder.create();
        drinkSubmitter.show();

        // Styling the cancel button
        Button negativeButton = drinkSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to suggest a minigame
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitGame()
     *          sendTheMessage()
     */
    private void submitGame() {
        // Exactly like the submitRule method so check that one out to for comments
        AtomicBoolean hasName = new AtomicBoolean(false);
        AtomicBoolean hasRules = new AtomicBoolean(false);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        String subject = getString(R.string.emailGameSubject);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.gameDialogTitle);

        final EditText inputRule = new EditText(this);
        inputRule.setInputType(InputType.TYPE_CLASS_TEXT);
        inputRule.setHint(R.string.hintGameName);
        if (!name.equals("")) {
            inputRule.setText(name);
        }
        layout.addView(inputRule);

        final EditText inputExplanation = new EditText(this);
        inputExplanation.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputExplanation.setHint(R.string.hintGameRules);
        if (!rules.equals("")) {
            inputExplanation.setText(rules);
        }
        layout.addView(inputExplanation);

        builder.setView(layout);

        builder.setPositiveButton(R.string.sendString, (dialog, which) -> {
            name = inputRule.getText().toString().trim();
            rules = inputExplanation.getText().toString().trim();

            if (name.equals("")) {
                hasName.set(false);
                Toast toast = Toast.makeText(this, R.string.toastGameNoName, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                hasName.set(true);
            }
            if (rules.equals("")) {
                hasRules.set(false);
                if (hasName.get()) {
                    Toast.makeText(this, getString(R.string.toastGameRuleWithName) + " " + name, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast toast = Toast.makeText(this, getString(R.string.toastGameRuleNoName), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                hasRules.set(true);
            }

            if (hasName.get() && hasRules.get()) {
                String body = getString(R.string.bodyGameName) + inputRule.getText().toString() +
                        getString(R.string.bodyGameRule) + inputExplanation.getText().toString();
                composeEmail(subject, body);
                name = "";
                rules = "";
            }
            else {
                name = inputRule.getText().toString().trim();
                rules = inputExplanation.getText().toString().trim();
                submitGame();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            jokeButtMessage();
            name = "";
            rules = "";
        });

        AlertDialog drinkSubmitter = builder.create();
        drinkSubmitter.show();

        Button negativeButton = drinkSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to compose an email programmatically
     * @param subject The subject of the email
     * @param body The body of the email
     */
    private void composeEmail(String subject, String body) {
        String[] addresses = {"igotdrunkly@gmail.com"}; // The addresses to which we want to send the mail to
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
    * Sets and starts the animation for each button
    * Makes the contents visible
     **/
    private void animateButtons() {
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateQuote();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        spDrinkButt.startAnimation(bounce);
        jokeButt.startAnimation(bounce);
        drinkButt.startAnimation(bounce);
    }

    /**
     * Adding the quotes to display on the activity_homescreen
     */
    private void addQuotes() {
        quotes.add(getString(R.string.yeeha));
        quotes.add(getString(R.string.mark));
        quotes.add(getString(R.string.hello));
        quotes.add(getString(R.string.nothing));
        quotes.add(getString(R.string.zeus));
        quotes.add(getString(R.string.just));
        quotes.add(getString(R.string.balls));
        quotes.add(getString(R.string.submitQuote1));
        quotes.add(getString(R.string.puking));
        quotes.add(getString(R.string.shoe));
        quotes.add(getString(R.string.sunrise));
        quotes.add(getString(R.string.pipe));
        quotes.add(getString(R.string.sshhh));
        quotes.add(getString(R.string.fun));
        quotes.add(getString(R.string.nuggets));
        quotes.add(getString(R.string.disclaimer));
        quotes.add(getString(R.string.chill));
        quotes.add(getString(R.string.shark));
        quotes.add(getString(R.string.sugar));
        quotes.add(getString(R.string.submitQuote2));
        quotes.add(getString(R.string.pimpim));
        quotes.add(getString(R.string.thirtySix));
        quotes.add(getString(R.string.fortyTwo));
        quotes.add(getString(R.string.order));
        quotes.add(getString(R.string.swipe));
        quotes.add(getString(R.string.much));
        quotes.add(getString(R.string.what));
        quotes.add(getString(R.string.squirrel));
        quotes.add(getString(R.string.shots));
        quotes.add(getString(R.string.fun2));
        quotes.add(getString(R.string.mixture));
        quotes.add(getString(R.string.aBitGood));
        quotes.add(getString(R.string.better));
        quotes.add(getString(R.string.submitQuote3));

//TODO: specials quotes
        quotes.add(getString(R.string.happyNewYear));
        quotes.add(getString(R.string.resolutions));


        Random r = new Random();
        int i = r.nextInt(quotes.size());
        currentQuote.setText(quotes.get(i));
        y = i; // Give y the value of i, to make sure we won't be picking the same quote twice in a row
    }

    /**
     * Animate and change the quote to be displayed
     */
    private void updateQuote() {

        final Animation disappear = AnimationUtils.loadAnimation(this, R.anim.translate_left); //The animation of quote sliding to the left
        final Animation appear = AnimationUtils.loadAnimation(this, R.anim.translate_from_right_to_center); //The animation of the quote sliding from the right

        // Only animate the quotes, if the screen is visible by the user
        if (!isFinished) {
            disappear.setAnimationListener(new Animation.AnimationListener() {

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    // Choose a new random number
                    Random r = new Random();
                    int i = r.nextInt(quotes.size()); // Use that number to get it from the array list
                    if (quotes.get(i).equals(quotes.get(y))) { // Making sure that that number wasn't previously selected, if it...
                        if (i != quotes.size() - 1) { // ...If i is not the last quote increase by one
                            i++;
                        } else { // ... otherwise decrease it by one
                            i--;
                        }
                    }
                    currentQuote.setText(quotes.get(i));

                    y = i; // Assign to y the new value of i
                    currentQuote.setAnimation(appear);
                    currentQuote.startAnimation(appear);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

        // Setting the animation listener to know when to start which animation and when
        if (!isFinished) {
            appear.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                // Wait 5s when the animation is done
                public void onAnimationEnd(Animation animation) {
                    Handler delay = new Handler();
                    delay.postDelayed(() -> {
                        currentQuote.setAnimation(disappear);
                        currentQuote.startAnimation(disappear);
                    }, 5000);

                }

                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        // Wait 5s before changing the first quote
        if (!isFinished) {
            Handler delay = new Handler();
            delay.postDelayed(() -> {
                currentQuote.setAnimation(disappear);
                currentQuote.startAnimation(disappear);
            }, 5000);
        }
    }

    /**
     * When the user presses the back button
     */
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    /**
     * Method to know where the user touches the screen
     * @param me The gesture of the user
     * @return The consequence of the gesture of the user
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.simpleGestureFilter.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    /**
     * Method to know what swipe effect did the user do
     * @param direction The direction of the swipe
     */
    @Override
    public void onSwipe(int direction) {

        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                spDrinkButtListener();
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                drinkButtListener();
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                jokeButtListener();
                break;
            case SimpleGestureFilter.SWIPE_UP:
                boozeledGameListener();
                break;
        }
    }
}