package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.drunkler.drunkly.PlayersNameScreen.playerMenu1;

/**
 * The CardScreen Class dictates how the CardScreen Activity should behave
 * @author Oscar
 */
public class CardScreen extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter simpleGestureFilter;        // The class to allow swiping commands

    private CardDeck cardDeck;                              // A CardDeck
    private Card card;                                      // A Card
    private FloatingActionButton button, roleButton;        // The buttons for the explanations of the card (button) and to show which role is held by which player (roleButton)
    // TODO: marker for where to change for halloween
    private ImageView cardView, cardView2;                  // Two ImageView views to hold the image of the Card card. cardView2 will be used to add the transitioning effects
    // TODO: christmas button making
    //private FloatingActionButton christButton;              // A special christmas button
    private TextView cardRules;                             // The TextView to display the rules of the Card card
    private TextView player;                                // The TextView to display the name of the player assigned to the Card card (if players were entered)
    private TextView yayText;                               // Displays a happy emoji that will come into focus if the user decides to replay
    private TextView nayText;                               // Displays a sad emoji that will come into focus if the user decides to close the game
    private TextView nOfCardsLeft;                          // Displays the number of cards left in the deck
    private ConstraintLayout constraintLayout;              // The layout that holds both the TextView for the rules and the name of the player of the Card card
    private ConstraintLayout layout;                        // The layout that will appear when the deck is finished suggesting either to restart the game or quit
    private LinearLayout linearLayout;                      // The layout that is shown while the deck is being used
    private ConstraintLayout nayay;                         // The layout holding the TextView showing nayText and yayText
    private Snackbar snackbar12, snackbar6, snackbarJoke;   // Customized snackbars that appear when specific cards appear: 12 for the Queen, 6 for for the 6 and Joke for the jokers
    private InterstitialAd mInterstitialAd;                 // The Interstitial ad that appears when the deck is finished
    private AdView myAdView;                                // The ad banner at the bottom of the screen
    private PublisherAdView mPublisherAdView2;

    private int i;                                          // The index that will be used to fetch a card from the deck
    private int playerNumber;                               // The index that will used to get a player from the list of players
    private int cardIndex;                                  // The int to find the last card that was displayed before going back
    private int size;                                       // The number of cards left in the deck
    private String jack;                                    // The string value that holds the name of each player who got a Jack
    private boolean startOfDeck;                            // Boolean value to check if the user is at the beginning of the deck or not
    private boolean stillHasCards;                          // Boolean value to check if the deck still has cards
    private List<String> roles;                             // The string value that holds the name of the players that have or had roles (King, queen or jack)

    //TODO: christmas roles list
    //private List<String> christRoles;

    private String body, name, ingredients, recipe, spirit, mixers, rules; // String values to store what the user entered when

    public CardScreen() {
    }

    /**
     * Sends to setupDeck as main
     * Creates the method for each button
     * @param savedInstanceState The state of the activity
     */
    @SuppressLint("RestrictedApi")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_screen);

        // For the add
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView6);
        PublisherAdRequest adRequest1 = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest1);

        mPublisherAdView2 = findViewById(R.id.publisherAdView2);
        mPublisherAdView2.loadAd(adRequest1);

        PublisherInterstitialAd mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-8818799719074826/8916220588");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        // Initializes and creates the ads
        MobileAds.initialize(this, "ca-app-pub-8818799719074826~6407123349");

        myAdView = findViewById(R.id.adView2);
        AdView myAdView2 = findViewById(R.id.adView6);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);
        myAdView2.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8818799719074826/8916220588");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        simpleGestureFilter = new SimpleGestureFilter(CardScreen.this, this);

        // Giving the values of each variables
        cardView = findViewById(R.id.imageView);
        cardView2 = findViewById(R.id.imageView2);
        cardRules = findViewById(R.id.cardName);
        player = findViewById(R.id.currentPlayer);
        yayText = findViewById(R.id.yay);
        nayText = findViewById(R.id.nay);
        nOfCardsLeft = findViewById(R.id.cardsLeftTextBox);
        constraintLayout = findViewById(R.id.textBox);
        layout = findViewById(R.id.noCardsLayout);
        linearLayout = findViewById(R.id.linearLayout);
        nayay = findViewById(R.id.nayay);
        roleButton = findViewById(R.id.roleButton);
        FloatingActionButton cockFloat = findViewById(R.id.cocktailFloat); // Button to show the cocktail menu
        FloatingActionButton drinkFloat = findViewById(R.id.drinkFloat); // Button to show the tips for drink menu

        //TODO: christmas buttons
        //christButton = findViewById(R.id.specialChristmasButton);
        //christButton.setVisibility(View.GONE);
        //christButton.setOnClickListener(v -> createChristmasRolesView());
        //christRoles = new ArrayList<>();
        //christRoles.add(getString(R.string.goodGiftedNone));
        //christRoles.add(getString(R.string.badGiftedNone));
        //christRoles.add(getString(R.string.santaClauseNone));
        //christRoles.add(getString(R.string.grinchNone));

        layout.setVisibility(View.GONE);
        yayText.bringToFront();
        nayText.bringToFront();

        i = -1;
        playerNumber = -1;
        cardIndex = 0;
        stillHasCards = true;
        roles = new ArrayList<>();
        roles.add(getString(R.string.ruleMakersNone));
        roles.add(getString(R.string.kingOfThumbsNone));
        roles.add(getString(R.string.queenOfBitchesNone));
        jack = getString(R.string.ruleMakersRoles);

        name = "";
        ingredients = "";
        recipe = "";
        spirit = "";
        mixers = "";
        rules = "";

        // When the '?' button is clicked, show the detailed rules of the card
        button = findViewById(R.id.cardExplain);
        button.setOnClickListener(view -> {
            if (card != null) {
                showDetailedRules();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardScreen.this);
                builder.setTitle(R.string.nullCardExplanationsTitle)
                        .setMessage(R.string.nullCardExplanations)
                        .setPositiveButton("R.I.P", (dialog, which) -> {});
                AlertDialog explanations = builder.create();
                explanations.show();
            }
        });

        // Button pressed to add or remove players from the game
        FloatingActionButton addButton = findViewById(R.id.addAPlayer);
        addButton.setOnClickListener(view -> askDeleteOrAdd());

        button.bringToFront();

        // Button pressed to close the game
        Button quitButton = findViewById(R.id.noCardQuit);
        quitButton.setOnClickListener(view -> closeGame());

        // Button pressed to replay
        Button replayButton = findViewById(R.id.noCardReplay);
        replayButton.setOnClickListener(view -> replay());

        // Button pressed to show the roles
        roleButton.setVisibility(View.GONE);
        roleButton.setOnClickListener(v -> createRolesView());

        // Buttons pressed to show the cocktail or drink menu
        cockFloat.setOnClickListener(v -> openCocktails());
        drinkFloat.setOnClickListener(v -> openDrinks());

        // Button pressed to send a message to me
        FloatingActionButton sendMessage = findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(v -> sendTheMessage());

        snackbar12 = Snackbar.make(linearLayout, R.string.snackbarHoe, Snackbar.LENGTH_SHORT);
        snackbar6 = Snackbar.make(linearLayout, R.string.snackbarAgent, Snackbar.LENGTH_SHORT);
        snackbarJoke = Snackbar.make(linearLayout, R.string.snackBarWelcome, Snackbar.LENGTH_SHORT);

        //Set-up the game and start the game
        setupDeck();
    }



    /*
    PASSIVE METHODS
     */

    /**
     * Method creating a dialog that lists different messages the user can send to me
     *
     * Sends to submitToDrinkler
     */
    private void sendTheMessage(){
        // String array for every choice the user can have
        String[] choices = {getString(R.string.ruleSuggest), getString(R.string.minigameSuggest), getString(R.string.feedbackSuggest), getString(R.string.quoteSuggest), getString(R.string.cocktailSuggest), getString(R.string.drinkSuggest)};

        // The dialog that appears
        AlertDialog.Builder builder = new AlertDialog.Builder(CardScreen.this);
        builder.setTitle(R.string.sendTheMessageTitle);
        builder.setItems(choices, ((dialog, which) -> submitToDrinkler(which)));
        builder.setPositiveButton(R.string.close, (dialogInterface, i) -> {
        });

        AlertDialog submit = builder.create();
        submit.show();
    }

    /**
     * Sends to to another method depending on the user's choice
     * @param which The index clicked by the user when he made his choice, sends to one of the following
     *              submitRule()
     *              submitGame()
     *              submitFeedback()
     *              submitQuote()
     *              submitCocktail()
     *              submitDrink()
     */
    private void submitToDrinkler(int which){
        switch (which) {
            case 0: {
                submitRule();
                break;
            }
            case 1: {
                submitGame();
                break;
            }
            case 2: {
                submitFeedback();
                break;
            }
            case 3: {
                submitQuote();
                break;
            }
            case 4: {
                submitCocktail();
                break;
            }
            case 5: {
                submitDrink();
                break;
            }
        }
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
            sendTheMessage();
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
     * Comments are exactly like the submitRule()
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitGame()
     *          sendTheMessage()
     */
    private void submitGame() {
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
            sendTheMessage();
            name = "";
            rules = "";
        });

        AlertDialog drinkSubmitter = builder.create();
        drinkSubmitter.show();

        Button negativeButton = drinkSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit feedback or a message
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitFeedback()
     *          sendTheMessage()
     */
    private void submitFeedback() {
        // Layout that will hold the EditText
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Where the user will enter his message of feedback
        final EditText message = new EditText(this);
        message.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        message.setHint(R.string.messageHint);
        layout.addView(message);

        // The dialog itself
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogFeedbackTitle)
                .setView(layout)
                .setPositiveButton(getString(R.string.sendString), (dialog, which) -> {
                    // If the user has entered a message, send the mail
                    if (!message.getText().toString().trim().isEmpty()) {
                        composeEmail(getString(R.string.emailSubjectFeedback), message.getText().toString());
                    }
                    // Inform the user he hasn't entered any message or feedback, reloads the dialog
                    else {
                        Toast toast = Toast.makeText(this, R.string.toastFeedBack, Toast.LENGTH_SHORT);
                        toast.show();
                        submitFeedback();
                    }
                })
                // Sends the user back to the previous dialog offering the choices
                .setNegativeButton(R.string.cancel, (dialog, which) -> sendTheMessage());

        AlertDialog feedback = builder.create();
        feedback.show();

        // Customize the color of the negative button
        Button negativeButton = feedback.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a quote
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitQuote()
     *          sendTheMessage()
     */
    private void submitQuote() {
        // The layout that will hold EditText
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Where the user will enter his quote
        final EditText message = new EditText(this);
        message.setRawInputType(InputType.TYPE_CLASS_TEXT);
        message.setHint(R.string.quoteInputHint);
        layout.addView(message);

        // The dialog itself
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quoteDialogTitle)
                .setView(layout)
                .setPositiveButton(R.string.sendString, ((dialog, which) -> {
                    // If the user has entered something, send the mail
                    if (!message.getText().toString().trim().isEmpty()) {
                        composeEmail(getString(R.string.quoteEmailSubject), message.getText().toString());
                    }
                    // Inform the user he hasn't entered shit, reload the dialog
                    else {
                        Toast toast = Toast.makeText(this, R.string.quoteToast, Toast.LENGTH_SHORT);
                        toast.show();
                        submitQuote();
                    }
                }))
                // Sends the user back to dialog offering the choices
                .setNegativeButton(R.string.cancel, ((dialog, which) -> sendTheMessage()));

        AlertDialog quote = builder.create();
        quote.show();

        // Customize the color of the negative button
        Button negativeButton = quote.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a cocktail recipe
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitCocktail()
     *          sendTheMessage() when canceled
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
            sendTheMessage();
        });

        AlertDialog cocktailSubmitter = builder.create();
        cocktailSubmitter.show();

        // Change the color of the cancel button
        Button negativeButton = cocktailSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to submit a drink
     *
     * Sends to one of the following:
     *          composeEmail(subject, body)
     *          submitDrink()
     *          sendTheMessage() when canceled
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
            sendTheMessage();
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
     * Method to compose an email programmatically to me
     * @param subject The subject of the email
     * @param body The body of the email
     *
     *Sends to one the default emailing app
     */
    private void composeEmail(String subject, String body) {
        // The addresses to which the mail will be sent
        String[] addresses = {"igotdrunkly@gmail.com"};

        // Loads the default emailing app, composes the addresses, subject and body
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
     * Ask user to add or remove a player
     *
     * Sends to makeChoice(which)
     */
    private void askDeleteOrAdd() {
        // The choices the player has
        String[] choices = {getString(R.string.addPlayer), getString(R.string.removePlayer), getString(R.string.seePlayers)};

        // The dialog itself
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.addOrRemove)
                .setItems(choices, ((dialog, which) -> makeChoice(which)))
                .setPositiveButton(R.string.close, (dialog, which) -> {})
                .create().show();
    }

    /**
     * Method to deal with the users choice of removing or adding a player
     *
     * Sends to one of the following:
     *              addANewPlayer()
     *              askDeleteOrAdd()
     */
    private void makeChoice(int which) {
        switch (which) {
            case 0:
                addANewPlayer();
                break;
            case 1:
                // If the user has entered some players, show the list of the players
                if (playerMenu1.hasPlayers()) {
                    List<String> menu1Players = playerMenu1.getPlayersNames();
                    String[] playersStringList = new String[menu1Players.size()];
                    playersStringList = menu1Players.toArray(playersStringList);

                    // The dialog itself
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.dare)
                            .setItems(playersStringList, (dialog, which1) -> {
                                Toast toast = Toast.makeText(this, playerMenu1.getPlayer(which1) + " " + getString(R.string.leftGame), Toast.LENGTH_SHORT);
                                toast.show();
                                removePlayer(which1);
                                dialog.cancel();
                            })
                            .setPositiveButton("Ok", (dialog, i) -> {})
                            .create().show();
                }
                // Inform the user that no players were entered and therefore can be removed from the game
                else {
                    Toast.makeText(this, R.string.cantRemove, Toast.LENGTH_SHORT).show();
                    askDeleteOrAdd();
                }
                break;
            case 2:
                // If the user has entered some players, show the list of the players
                if (playerMenu1.hasPlayers()) {
                    List<String> menu1Players = playerMenu1.getPlayersNames();
                    String[] playersStringList = new String[menu1Players.size()];
                    playersStringList = menu1Players.toArray(playersStringList);

                    // The dialog itself
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.dare)
                            .setItems(playersStringList, (dialog, which1) -> {})
                            .setPositiveButton("Ok", (dialog, i) -> {})
                            .create().show();
                }
                // Inform the user that no players were entered
                else {
                    Toast.makeText(this, R.string.cantSee, Toast.LENGTH_SHORT).show();
                    askDeleteOrAdd();
                }
        }
    }

    /**
     * Method to add a new player
     */
    private void addANewPlayer() {
        // Layout that will hold the EditText
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Where the user will enter the name of the user
        final EditText playerName = new EditText(this);
        playerName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        playerName.setHint(R.string.playerNameHint);
        layout.addView(playerName);

        // The dialog itself
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.addPlayer)
                .setView(layout)
                .setPositiveButton(R.string.addButton, ((dialog, which) -> {
                    // Set the name the user entered as a new player
                    if (!playerName.getText().toString().isEmpty()) {
                        String player = playerName.getText().toString().trim();

                        if (!playerMenu1.playerExists(player)) {
                            playerMenu1.newPlayer(player);
                            Toast toast = Toast.makeText(this, playerName.getText().toString() + " " + getString(R.string.playerJoined), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            Toast.makeText(this, playerName.getText().toString() + " " + getString(R.string.toastPlayerExists), Toast.LENGTH_SHORT).show();
                            addANewPlayer();
                        }
                    }
                    // Inform the user he hasn't entered the name of the player, reload the dialog
                    else {
                        Toast toast = Toast.makeText(this, R.string.cantJoin, Toast.LENGTH_LONG);
                        toast.show();
                        addANewPlayer();
                    }
                }))
                .setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.cancel()));

        AlertDialog  alertDialog = builder.create();
        alertDialog.show();

        // Customize the color of the negative button
        Button button = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        button.setTextColor(Color.parseColor("#FFBDBDBD"));
    }

    /**
     * Method to remove a player
     * @param i the index of the player selected
     */
    private void removePlayer(int i){
        playerMenu1.removePlayer(i);
    }

    /*
    ACTIVE METHODS
     */

    /**
     * Creates and initializes the game
     *
     * Sends to one of the following:
     *              startGame()
     *              getPreviousCard()
     *              showDetailedRules()
     */
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    private void setupDeck() {

        // Hides the emoji
        yayText.setVisibility(View.GONE);
        nayText.setVisibility(View.GONE);

        // The index is -1 because we are at the -1 card index
        i = -1;
        // The number of cards is set back to 54;
        size = 54;
        nOfCardsLeft.setText("" + size);
        //Create a new card deck
        cardDeck = new CardDeck();

        player.setVisibility(View.INVISIBLE);
        //TODO: change image for card here
        cardView.setImageResource(R.drawable.jokericon);
        cardView2.setImageResource(R.drawable.jokericon);
        cardView.setBackground(getDrawable(R.drawable.button_background));
        cardView2.setBackground(getDrawable(R.drawable.button_background));
        //Create an action listener to the card image
        //Whenever the card is clicked, something happens
        cardView.setOnClickListener(view -> {
            //Change cards
            startGame();
        });

        // When the card image is pressed, brings the user back to the previous card, if there are any
        cardView.setOnLongClickListener(v -> getPreviousCard());


        // When the text under the card is clicked, show the rules in details, unless that card doesn't exist
        cardRules.setOnClickListener(v -> {
            if (card != null) {
               showDetailedRules();
            }
            // Explain the mechanics of the game
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardScreen.this);
                builder.setTitle(R.string.nullCardExplanationsTitle)
                        .setMessage(R.string.nullCardExplanations)
                        .setPositiveButton("R.I.P", (dialog, which) -> {

                        });
                AlertDialog explanations = builder.create();
                explanations.show();
            }
        });
    }

    /**
     * Once a user presses a card or swipes left, the game starts and shows the next card in the deck
     *
     * Has access to:
     *              dismissSnacks()
     *              animateLeft()
     *              updateIndex()
     *              nextPlayer()
     *              setRoles()
     *              createLife()
     *              noMoreCardsMessage()
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "RestrictedApi"})
    private void startGame() {
        dismissSnacks();

        // Creates an animation rendering the texts temporarily bigger
        Animation scale = AnimationUtils.loadAnimation(cardRules.getContext(), R.anim.anim_scale);
        constraintLayout.setAnimation(scale);

        startOfDeck = false; // We are no longer at the start of the deck

        // removing the background of the image, to have just the image of card displayed
        cardView.setBackgroundResource(0);
        cardView2.setBackgroundResource(0);

        // Sets the image of the card if it's not a null object
        if (card != null) {
            cardView2.setImageResource(card.getCardImage());
        }
        // Show the the image of the back of the card
        else {
            //TODO: change image for card here
            cardView2.setImageResource(R.drawable.jokericon);
            cardView2.setBackground(getDrawable(R.drawable.button_background));
        }

        //Check if we are not having an index bigger than the size of the deck
        if (i < cardDeck.getDeckSize() - 1) {

            // Animate the card, then hide it
            cardView2.setVisibility(View.VISIBLE);
            animateLeft();
            cardView2.setVisibility(View.INVISIBLE);

            i++;
            card = cardDeck.choseRandomCard(i); // Choose a random card

            // If the game has players, show the name, hide the ads, show the button showing the roles, updateIndex(), nextPlayer() and setRoles()
            if (playerMenu1.hasPlayers()) {
                myAdView.setVisibility(View.GONE);
                mPublisherAdView2.setVisibility(View.GONE);
                player.setVisibility(View.VISIBLE);
                roleButton.setVisibility(View.VISIBLE);
                //TODO: marker for christmas
                //christButton.setVisibility(View.VISIBLE);
                updateIndex();
                nextPlayer();
                setRoles();
                //TODO: christmas roles
                //setChristmasRoles();
            }
            // Otherwise, hide the button, hide the text holding the name value, show the ad
            else {
                player.setVisibility(View.GONE);
                myAdView.setVisibility(View.VISIBLE);
                myAdView.setClickable(true);
                mPublisherAdView2.setVisibility(View.VISIBLE);
                mPublisherAdView2.setClickable(true);
                roleButton.setVisibility(View.GONE);
                //TODO: marker for christmas
                //christButton.setVisibility(View.GONE);
            }

            //Update the image and the text displayed
            cardRules.setText(card.getRules().toUpperCase());
            cardView.setImageResource(card.getCardImage());
            constraintLayout.animate();
            button.show();
            //A comical snackbar will pop-up from time to time depending on the card
            createLife();

            // The number of cards left in game diminishes
            size--;
            // Update the text displayed
            nOfCardsLeft.setText("" + size);
        }
        else {
            // What happens when the deck is empty
            noMoreCardsMessage();
        }
    }

    /**
     * Method to get the previous card
     *
     * Has access to:
     *              dismissSnacks()
     *              createLife()
     *              previousPlayer()
     *              noPreviousCards()
     */
    @SuppressLint("SetTextI18n")
    private boolean getPreviousCard() {
        //Animate the returning card
        final Animation translateRight2 = AnimationUtils.loadAnimation(cardView2.getContext(), R.anim.translate_right);

        dismissSnacks();

        // If the index is not the start of the deck, decrease it, change the values that appear
        if (i > 0) {
            i--;
            card = cardDeck.choseRandomCard(i);
            cardView2.setImageResource(card.getCardImage());
            cardView2.setAnimation(translateRight2);

            cardIndex--;

            size++;
            nOfCardsLeft.setText("" + size);

            translateRight2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation translateRight21) {
                }
                @Override
                public void onAnimationRepeat(Animation translateRight21) {
                }

                @Override
                public void onAnimationEnd(Animation translateRight21) {
                    cardRules.setText(card.getRules().toUpperCase());
                    cardView.setImageResource(card.getCardImage());
                    cardRules.animate();
                    button.show();
                }
            });

            // Get the previous player, if any
            if (playerMenu1.hasPlayers()) {
                previousPlayer();
            }

            cardView2.startAnimation(translateRight2);
            startOfDeck = false;
            return true;
        } else if (i == 0){
            noPreviousCards();
            nOfCardsLeft.setText("53");
            startOfDeck = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * When the deck has no more cards, suggest either to replay the game, or to quit, but load the ad
     */
    private void noMoreCardsMessage(){
        stillHasCards = false;
        linearLayout.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        Animation scale = AnimationUtils.loadAnimation(cardRules.getContext(), R.anim.anim_scale);
        layout.startAnimation(scale);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    /**
     * Method to warn the user that there are no previous cards in the deck
     */
    private void noPreviousCards() {
        Toast noCardsBefore = Toast.makeText(this, R.string.noPreviousCard, Toast.LENGTH_SHORT);
        noCardsBefore.show();
    }

    /**
     * Method to replay the game
     *
     * Has access to setupDeck()
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void replay() {
        // Show and animate the happy emoji
        Animation yay = AnimationUtils.loadAnimation(this, R.anim.nay_yay);
        layout.setVisibility(View.GONE);
        nayay.setVisibility(View.VISIBLE);
        yayText.bringToFront();
        yayText.setVisibility(View.VISIBLE);
        yayText.startAnimation(yay);

        stillHasCards = true; // Start back at the start of the deck
        linearLayout.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        card = null; // Clear the current card
        setupDeck(); // Creates a new deck
        //Clear the roles
        roles.set(0, getString(R.string.ruleMakersNone));
        roles.set(1, getString(R.string.kingOfThumbsNone));
        roles.set(2, getString(R.string.queenOfBitchesNone));
        //TODO: marker for christmas roles
        //christRoles.set(2, getString(R.string.santaClauseNone));
        //christRoles.set(3, getString(R.string.grinchNone));
        //christRoles.set(0, getString(R.string.goodGifted));
        //christRoles.set(1, getString(R.string.badGifted));
        cardRules.setText(R.string.cardName);
        yayText.startAnimation(yay);

    }

    /**
     * Method to quit the game
     */
    private void closeGame() {
        // Show and animate the sad emoji
        Animation nay = AnimationUtils.loadAnimation(this, R.anim.nay_yay);
        layout.setVisibility(View.GONE);
        nayay.setVisibility(View.VISIBLE);
        nayText.bringToFront();
        nayText.setVisibility(View.VISIBLE);
        nayText.startAnimation(nay);

        // Clear the roles
        roles.set(0, getString(R.string.ruleMakersNone));
        roles.set(1, getString(R.string.kingOfThumbsNone));
        roles.set(2, getString(R.string.queenOfBitchesNone));
        //TODO: marker for christmas roles
        //christRoles.set(2, getString(R.string.santaClauseNone));
        //christRoles.set(3, getString(R.string.grinchNone));
        //christRoles.set(0, getString(R.string.goodGifted));
        //christRoles.set(1, getString(R.string.badGifted));

        // Do not retain the name of the players added in game
        if (playerMenu1.hasPlayers()) {
            finish();
            Toast toast = Toast.makeText(getApplicationContext(), R.string.addedWasDismissed, Toast.LENGTH_LONG);
            toast.show();
        } else {
            finish();
        }
    }

    /**
     * Method to show the detailed explanations of the rules assigned to the current card
     *
     * Has access to makeGameCardRules()
     */
    private void showDetailedRules() {

        //If the card weight is 8, it means it's a game card...
        if (card.getValue() == 8 || card.getValue() == 11) {
            makeGameCardRules();
        }
        ///... otherwise it's a regular Card
        else {
            //Create a pop-up with the rules of the card
            AlertDialog.Builder builder = new AlertDialog.Builder(CardScreen.this);
            builder.setTitle(card.getDescription());
            builder.setMessage(card.getRulesInDetails());
            builder.create();

            //Create an "OK" button to close that pop-up
            builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }

    }

    /**
     * Method to show the rules/minigames of a GameCard
     *
     * Has access to makeMiniGameScreen(index chosen)
     */
    private void makeGameCardRules() {

        //Create a List that copies the list holding the titles of the miniGames
        List miniGamesList = card.getMiniGamesTitles();
        String [] miniGamesTitles = new String[miniGamesList.size()];
        miniGamesTitles = (String[]) miniGamesList.toArray(miniGamesTitles);

        //Create a pop-up with the list of miniGames
        AlertDialog.Builder eightDialog = new AlertDialog.Builder(CardScreen.this);
        eightDialog.setTitle(card.getDescription());
        eightDialog.setItems(miniGamesTitles, (dialogInterface, i) -> makeMiniGameScreen(i));
        eightDialog.create();
        eightDialog.setPositiveButton("OK", (dialogInterface, i) -> {
        });
        eightDialog.show();
    }

    /**
     * Method to show the explanations of each rule or minigame of a GameCard
     *
     * @param i the index chosen
     */
    private void makeMiniGameScreen(int i){
        //Create a list holding the name of each miniGame
        List miniGamesList2 = card.getMiniGamesTitles();

        //Create a pop-up with the explanation of the rules of the miniGame
        AlertDialog.Builder miniGameRules = new AlertDialog.Builder(CardScreen.this);
        //Create a HashMap that holds the name and the explanation of each miniGame
        HashMap miniGameExplanation = card.getMiniGames();
        miniGameRules.setTitle((String) miniGamesList2.get(i));
        miniGameRules.setMessage((String) miniGameExplanation.get(miniGamesList2.get(i)));
        miniGameRules.create();

        //Create an "OK" button to close the pop-up
        miniGameRules.setPositiveButton("OK", (dialogInterface, i1) -> makeGameCardRules());

        miniGameRules.show();
    }

    /**
     * Method to display who's turn it is
     */
    @SuppressLint("SetTextI18n")
    private void nextPlayer() {
        playerNumber++;

        if (playerNumber > playerMenu1.size() - 1) {
            playerNumber = 0;
        }

        if (cardIndex == 0) {
            card.setPlayer(playerMenu1.getPlayer(playerNumber));
        }

        if (!card.getPlayer().isEmpty()) {
            player.setText(card.getPlayer() + "!");
        }
    }

    /**
     * Method to display who's turn was before
     */
    private void previousPlayer() {
        if (playerNumber > 0) {
            playerNumber--;
        }
        else if (playerNumber == 0){
            playerNumber = playerMenu1.size() - 1 ;
        }
        player.setText(card.getPlayer());
    }

    /**
     * Method to create the dialog to show the roles
     */
    private void createRolesView() {
        // String array of the roles
        String[] rolesArray = new String[roles.size()];
        rolesArray = roles.toArray(rolesArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rolesTitle)
                .setItems(rolesArray, ((dialog, which) -> {}))
                .setPositiveButton("Ok", ((dialog, which) -> showJob(which)));

        AlertDialog roleDialog = builder.create();
        roleDialog.show();
    }

    //TODO: marker for christmas roles
    /**
     * Method to create the dialog to show the christmas roles
     */
    /*private void createChristmasRolesView() {
        String[] christRolesArray = new String[christRoles.size()];
        christRolesArray = christRoles.toArray(christRolesArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rolesTitle)
                .setItems(christRolesArray, ((dialog, which) -> {}))
                .setPositiveButton("Ok", ((dialog, which) -> {}));

        AlertDialog roleDialog = builder.create();
        roleDialog.show();
    }
     */

    /**
     * Kinda useless method
     * @param which the item clicked
     */
    private void showJob(int which){
        switch (which) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2:
                break;
        }
    }

    /**
     * Update the roles of each player
     */
    private void setRoles() {
        // check that we haven't gone back
        if (cardIndex == 0) {
            if (card.getValue() == 13) {
                        roles.set(1, getString(R.string.kingOfThumbsRole) + " " + card.getPlayer());
            }
            if (card.getValue() == 12) {
                        roles.set(2, getString(R.string.queenOfBitchesRole) + " " + card.getPlayer());
            }
            if (card.getValue() == 11 && !card.getPlayerWasAdded()) {
                jack += "\n- " + card.getPlayer();
                roles.set(0, jack);
                card.setPlayerWasAdded();
            }
        }
    }

    //TODO: marker for christmas roles
    /**private void setChristmasRoles() {
        // Exactly the above method
        if (cardIndex == 0) {
            if (card.getValue() == 26) {
                christRoles.set(2, getString(R.string.santaClause) + " " + card.getPlayer());
            }
            if (card.getValue() == 23) {
                christRoles.set(3, getString(R.string.grinch) + " " + card.getPlayer());
            }
            if (card.getValue() == 24) {
                christRoles.set(0, getString(R.string.goodGifted) + " " + card.getPlayer());
            }
            if (card.getValue() == 25) {
                christRoles.set(1, getString(R.string.badGifted) + " " + card.getPlayer());
            }
        }
    }

    /**
     * Method to open the cocktail menu
     */
    private void openCocktails(){
        Intent intent = new Intent(this, CocktailScreen.class);
        startActivity(intent);
    }

    /**
     * Method to open the drink menu
     */
    private void openDrinks() {
        Intent intent = new Intent(this, TipsDrinks.class);
        startActivity(intent);
    }

    /**
     * Method to get the last card chosen
     */
    private void updateIndex() {
        cardIndex++;
        if (cardIndex >= 0) {
            cardIndex = 0;
        }
    }

    /**
     * Method to show the animation of a left translation
     */
    private void animateLeft() {
        Animation translateLeft2 = AnimationUtils.loadAnimation(cardView2.getContext(), R.anim.translate_left);
        cardView2.setAnimation(translateLeft2);
        cardView2.animate();
    }

    /**
     * Create fun pop-ups depending on the card
     */
    private void createLife(){
        if (card.getValue() == 12) {
            snackbar12.show();

        }
        else if (card.getValue() == 6) {
            snackbar6.show();
        }
        else if (card.getValue() == 15 || card.getValue() == 14) {
            snackbarJoke.show();
        }
    }

    /**
     * Method to dismiss any Snackbar if shown when swiping
     */
    private void dismissSnacks() {
        if (snackbar6.isShown()) {
            snackbar6.dismiss();
        }
        if (snackbar12.isShown()) {
            snackbar12.dismiss();
        }
        if (snackbarJoke.isShown()) {
            snackbarJoke.dismiss();
        }
    }

    /**
     * Method for when the user presses back
     */
    @Override
    public void onBackPressed() {
        if (stillHasCards) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.leavingTitle)
                    .setMessage(R.string.leavingDialogMessage)
                    .setPositiveButton(R.string.yesDuh, ((dialog, which) -> closeGame()))
                    .setNegativeButton(R.string.hellNo, ((dialog, which) -> {}));

            AlertDialog leave = builder.create();
            leave.show();

            Button button = leave.getButton(DialogInterface.BUTTON_NEGATIVE);
            button.setTextColor(Color.parseColor("#FFBDBDBD"));
        }
        else {
            layout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to allow the user to navigate the screen when swiping
     * @param me the gesture
     * @return the gesture made
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.simpleGestureFilter.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    /**
     * Method to define what each gesture does
     * @param direction the direction of the gesture
     */
    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                if (card == null) {
                    finish();
                }
                else {
                    if (!stillHasCards) {
                        dismissSnacks();
                        layout.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        stillHasCards = true;
                    } else {
                        if (startOfDeck) {
                            dismissSnacks();
                            noPreviousCards();
                        } else {
                            getPreviousCard();
                        }
                    }
                }
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                startGame();
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                askDeleteOrAdd();
                break;
            case SimpleGestureFilter.SWIPE_UP:
                cardRules.performClick();
                break;
        }
    }
}