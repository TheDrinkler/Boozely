package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * This class dictates how the Tips for Drinks screens behaves
 *
 * @author Oscar
 */
public class TipsDrinks extends AppCompatActivity implements CategoryAdapter.CategoryInterface{

    private CategoryAdapter categoryAdapter; // Adapter used for the category RecyclerView
    public MixerAdapter mixerAdapter; //Adapter used for the categoryDetails RecyclerView

    private TextView drinksTitle; // The title of the screen
    private TextView drinkPrompt; // The message under the title
    private RecyclerView categories; // The RecyclerView holding the name of each alcohol category
    public RecyclerView catDetails; // The RecyclerView holding each type of that alcohol category

    private  DrinkMenu drinkMenu; // The DrinkMenu is another Class holding every Alcohol Category, the type of each alcohol and the mixers good with it
    private ArrayList<String> drinkCat = new ArrayList<>(); // A String ArrayList holding the name of each alcohol category (Gin, Vodka, Rum, Whiskey, Other)
    private ArrayList<Drink> drinkMixers = new ArrayList<>(); // A Drink(String, ArrayList<String>) ArrayList holding the mixers of every Drink

    private String spirit; // The spirit used by the user when he wants to recommend a drink
    private String mixers; // The mixer used ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_drinks);

        // For the add
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView4);
        PublisherAdRequest adRequest1 = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest1);

        // Creates and initializes the add
        MobileAds.initialize(this, "ca-app-pub-8818799719074826~6407123349");

        AdView mAdView = findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        drinksTitle = findViewById(R.id.title);
        drinkPrompt = findViewById(R.id.drinkSuggestion);
        TextView spinnerPrompt = findViewById(R.id.SpinnerPrompt);
        categories = findViewById(R.id.category);
        catDetails = findViewById(R.id.categoryDetails);

        spirit = "";
        mixers = "";

        drinksTitle.setOnClickListener(v -> setTitleAndPromptListeners());
        drinkPrompt.setOnClickListener(v -> setTitleAndPromptListeners());

        drinkMenu = new DrinkMenu();

        setCategoryView(); // Creates the category RecyclerView

        setDrinkMixers(); // Creates the categoryDetails RecyclerView

        String[] list = new String[] {" " + getString(R.string.alcoholSort) + " ", " " + getString(R.string.softSort)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_item_dropdown, list);

        // So that user can rearrange the list he pleases
        Spinner spinner = findViewById(R.id.choiceSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0:
                        drinkMenu.setAsAlcohol();
                        prepareCategories();
                        prepareMixers(drinkMenu.getDrinkMixers(getString(R.string.gin)));
                        break;
                    case 1:
                        drinkMenu.setAsSoft();
                        setCategoryView();
                        prepareMixers(drinkMenu.getDrinkMixers(getString(R.string.juices)));
                        break;
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent){}
        });

        spinnerPrompt.setOnClickListener(v -> spinner.performClick());

    }

    /**
     * Sends to according methods when either the title or the text beneath is clicked
     */
    private void setTitleAndPromptListeners() {
        Animation disappear = AnimationUtils.loadAnimation(this, R.anim.disappear);

            askForSpiritsNMixers();
            drinksTitle.startAnimation(disappear);
            drinkPrompt.startAnimation(disappear);
    }

    /**
     * The view for the categories
     */
    private void setCategoryView(){

        categoryAdapter = new CategoryAdapter(drinkCat, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TipsDrinks.this, LinearLayoutManager.HORIZONTAL, false);

        categories.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        categories.setLayoutManager(layoutManager);
        categories.setItemAnimator(new DefaultItemAnimator());
        categories.setAdapter(categoryAdapter);

        prepareCategories();
    }

    /**
     * Adds the categories
     */
    private void prepareCategories(){
        categoryAdapter.notifyItemRangeRemoved(0, drinkCat.size());
        drinkCat.clear();
        drinkCat.addAll(drinkMenu.getDrinkCategories());
        for (String drink : drinkCat) {
            System.out.println(drink);
        }
        categoryAdapter.notifyItemChanged(drinkCat.size() - 1);
    }

    /**
     * See which item the user has clicked on to see the suggestions
     * @param position The position of the item clicked
     */
    @Override
    public void onItemSelected(int position) {
        prepareMixers(drinkMenu.getDrinkMixers(categoryAdapter.getCurrentCategory()));
    }

    /**
     * Method to create the mixers view
     */
    private void setDrinkMixers() {
        mixerAdapter = new MixerAdapter(drinkMixers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        catDetails.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        catDetails.setLayoutManager(layoutManager);
        catDetails.setItemAnimator(new DefaultItemAnimator());
        catDetails.setAdapter(mixerAdapter);

        prepareMixers(drinkMenu.getDrinkMixers(categoryAdapter.getCurrentCategory()));
    }

    /**
     * Adds the mixers to the view
     * @param drink The list of the drinks
     */
    private void prepareMixers(ArrayList<Drink> drink) {
        mixerAdapter.notifyItemRangeRemoved(0, drinkMixers.size());
        drinkMixers.clear();
        drinkMixers.addAll(drink);
        mixerAdapter.notifyItemChanged(drinkMixers.size() - 1);
    }

    /**
     * Prompts user for favourite spirit and mixer
      */
    @SuppressLint("ClickableViewAccessibility")
    private void askForSpiritsNMixers() {
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
                askForSpiritsNMixers();
            }
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            //Send back to previous dialog and clear the variables
            dialog.cancel();
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
     * Method to compose the email
     * @param subject The subject of the mail
     * @param body The body of the mail
     */
    private void composeEmail(String subject, String body) {
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
     * When the user presses back
     */
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}