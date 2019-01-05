package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.widget.AdapterView.*;

/**
 * The Class responsible for how the CocktailScreen aka the CocktailMenu behaves
 *
 * @author Oscar
 */
public class CocktailScreen extends AppCompatActivity {

    private CocktailAdapter myAdapter; // The adapter for the RecyclerView

    private TextView cocktailTitle; // The titles of the page
    private TextView cocktailPrompt; // The prompt inviting to submit a cocktail

    private CocktailMenu cockMenu; // A cocktailMenu
    private ArrayList <Cocktail> cocktailList = new ArrayList<>(); // A list holding the cocktails from the cocktail menu
    private String body; // The body for a mail
    private String name; // The name of the cocktail
    private String ingredients; // The ingredients for the cocktail
    private String recipe; // The recipe of the cocktail


    /**
     * Initializes the activity
     *
     * Has access to :
     *              setRecyclerView()
     *              prepareCocktails()
     * @param savedInstanceState The state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_screen);

        // For the add
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView3);
        PublisherAdRequest adRequest1 = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest1);

        // Creates and initializes the add
        MobileAds.initialize(this, "ca-app-pub-8818799719074826~6407123349");

        AdView mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        cockMenu = new CocktailMenu();
        body = "";
        name = "";
        ingredients = "";
        recipe = "";

        setRecyclerView();

        prepareCocktails();

        // List to hold the entries in the spinner
        String[] list = {"  " + getString(R.string.defSort), "  " + getString(R.string.naSort), "  " + getString(R.string.alLevSort), "  " + getString(R.string.nSpiritSort)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_default, list);

        // Spinner responsible to order the cocktails in a certain way
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0:
                        cocktailList.clear();
                        cockMenu.defaultSort();
                        setRecyclerView();
                        prepareCocktails();
                        break;
                    case 1:
                        cocktailList.clear();
                        cockMenu.nameSort();
                        setRecyclerView();
                        prepareCocktails();
                        break;
                    case 2:
                        cocktailList.clear();
                        cockMenu.leastAlcoholSort();
                        setRecyclerView();
                        prepareCocktails();
                        break;
                    case 3:
                        cocktailList.clear();
                        cockMenu.numberOfSpirits();
                        setRecyclerView();
                        prepareCocktails();
                        break;
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent){}
        });

        TextView sorted = findViewById(R.id.sortBy);
        sorted.setOnClickListener(v -> spinner.performClick());


        cocktailTitle = findViewById(R.id.cocktailsLover);
        cocktailPrompt = findViewById(R.id.promptSubmit);

        cocktailTitle.setOnClickListener(v -> setTitleAndPromptListeners());
        cocktailPrompt.setOnClickListener(v -> setTitleAndPromptListeners());

    }

    /**
     * Start the animation and prompts the user to submit a cocktail
     *
     * Has access to submitCocktail()
     */
    private void setTitleAndPromptListeners() {
        Animation disappear = AnimationUtils.loadAnimation(this, R.anim.disappear);
            submitCocktail();
            cocktailTitle.startAnimation(disappear);
            cocktailPrompt.startAnimation(disappear);
    }

    /**
     * Sets the recycler view
      */
    private void setRecyclerView() {
        RecyclerView cocktailMenu = findViewById(R.id.recycler_view);

        myAdapter = new CocktailAdapter(cocktailList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        cocktailMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        cocktailMenu.setLayoutManager(layoutManager);
        cocktailMenu.setItemAnimator(new DefaultItemAnimator());
        cocktailMenu.setAdapter(myAdapter);
    }

    /**
    * Add every cocktails information to a new list to then add it to the RecyclerView
    */
    private void prepareCocktails() {
        cocktailList.addAll(cockMenu.getCocks());
        myAdapter.notifyItemInserted(cocktailList.size() - 1);
    }

    /**
    * Prompts user for a cocktail recipe
     *
     * Has access to composeEmail() and submitCocktail()
    **/
    @SuppressLint({"ClickableViewAccessibility"})
    private void submitCocktail() {

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
            dialog.cancel();
        });

        AlertDialog cocktailSubmitter = builder.create();
        cocktailSubmitter.show();

        // Change the color of the cancel button
        Button negativeButton = cocktailSubmitter.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFBDBDBD"));

    }

    /**
     * Method to write an email programmatically
     * @param subject The subject of the mail
     * @param body The body of the mail
     */
    private void composeEmail(String subject, String body){
        String[] addresses = {"igotdrunkly@gmail.com"};
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
     * When the user hits the back button
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
