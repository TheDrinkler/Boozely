package com.drunkler.drunkly;

import android.annotation.TargetApi;

import java.util.ArrayList;
import java.util.Random;

import static com.drunkler.drunkly.R.*;



/**
 * The CardDeck class creates a deck of cards shuffled
 * @author Oscar
 */
class CardDeck {
    private ArrayList<Card> cards; // A first ArrayList that holds every Card in the correct order
    private ArrayList<Card> deck; // A second ArrayList to which add cards in a random order
    protected Random r; // A random number

    /**
     * Create a new deck
     */
    CardDeck()
    {
        cards = new ArrayList<>();
        deck = new ArrayList<>();
        addCards();
        makeDeck();
    }

    /**
     * Create and add cards to the ArrayList
     */
    private void addCards()
    {
        Card clubs2, diamonds2, hearts2, spades2,
                clubs3, diamonds3, hearts3, spades3,
                clubs4, diamonds4, hearts4, spades4,
                clubs5, diamonds5, hearts5, spades5,
                clubs6, diamonds6, hearts6, spades6,
                clubs7, diamonds7, hearts7, spades7,
                clubs9, diamonds9, hearts9, spades9,
                clubs10, diamonds10, hearts10, spades10,
                queenClubs, queenDiamonds, queenHearts, queenSpades,
                kingClubs, kingDiamonds, kingHearts, kingSpades,
                aceClubs, aceDiamonds, aceHearts, aceSpades,
                blackJoker, redJoker;

        //TODO: This is a marker for halloween and christmas card
        // Card halloweenCard1, halloweenCard2, halloweenCard3, halloweenCard4;
        // Card christmasCardSanta1, christmasCardSanta2, christmasCardGrinch1, christmasCardGrinch2, christmasCardNaughtyGift, christmasCardGoodGift;

        GameCard clubs8, diamonds8, hearts8, spades8, jackClubs, jackDiamonds, jackHearts, jackSpades;


        // create the cards
        clubs2 = new Card(string.clubs2);
        diamonds2 = new Card(string.diamonds2);
        hearts2 = new Card(string.hearts2);
        spades2 = new Card(string.spades2);

        clubs3 = new Card(string.clubs3);
        diamonds3 = new Card(string.diamonds3);
        hearts3 = new Card(string.hearts3);
        spades3 = new Card(string.spades3);

        clubs4 = new Card(string.clubs4);
        diamonds4 = new Card(string.diamonds4);
        hearts4 = new Card(string.hearts4);
        spades4 = new Card(string.spades4);

        clubs5 = new Card(string.clubs5);
        diamonds5 = new Card(string.diamonds5);
        hearts5 = new Card(string.hearts5);
        spades5 = new Card(string.spades5);

        clubs6 = new Card(string.clubs6);
        diamonds6 = new Card(string.diamonds6);
        hearts6 = new Card(string.hearts6);
        spades6 = new Card(string.spades6);

        clubs7 = new Card(string.clubs7);
        diamonds7 = new Card(string.diamonds7);
        hearts7 = new Card(string.hearts7);
        spades7 = new Card(string.spades7);

        clubs8 = new GameCard(string.clubs8);
        diamonds8 = new GameCard(string.diamonds8);
        hearts8 = new GameCard(string.hearts8);
        spades8 = new GameCard(string.spades8);

        clubs9 = new Card(string.clubs9);
        diamonds9 = new Card(string.diamonds9);
        hearts9 = new Card(string.hearts9);
        spades9 = new Card(string.spades9);

        clubs10 = new Card(string.clubs10);
        diamonds10 = new Card(string.diamonds10);
        hearts10 = new Card(string.hearts10);
        spades10 = new Card(string.spades10);

        jackClubs = new GameCard(string.clubsJack);
        jackDiamonds = new GameCard(string.diamondsJack);
        jackHearts = new GameCard(string.heartsJack);
        jackSpades = new GameCard(string.spadesJack);

        queenClubs = new Card(string.clubsQueen);
        queenDiamonds = new Card(string.diamondsQueen);
        queenHearts = new Card(string.heartsQueen);
        queenSpades = new Card(string.spadesQueen);

        kingClubs = new Card(string.clubsKing);
        kingDiamonds = new Card(string.diamondsKing);
        kingHearts = new Card(string.hearts3King);
        kingSpades = new Card(string.spadesKing);

        aceClubs = new Card(string.clubsAce);
        aceDiamonds = new Card(string.diamondsAce);
        aceHearts = new Card(string.heartsAce);
        aceSpades = new Card(string.spadesAce);

        blackJoker = new Card(string.blackJoker);
        redJoker = new Card(string.redJoker);

        //TODO: halloween and christmas cards
        //SPECIAL HALLOWEEN
        //halloweenCard1 = new Card(string.halloweenCardName);
        //halloweenCard2 = new Card(string.halloweenCardName);
        //halloweenCard3 = new Card(string.halloweenCardName);
        //halloweenCard4 = new Card(string.halloweenCardName);

        //christmasCardSanta1 = new Card(string.santaCard);
        //christmasCardSanta2 = new Card(string.santaCard);
        //christmasCardGrinch1 = new Card(string.grinchCard);
        //christmasCardGrinch2 = new Card(string.grinchCard);
        //christmasCardGoodGift = new Card(string.goodGift);
        //christmasCardNaughtyGift = new Card(string.naughtyGift);

        // assign the simple rule to these cards
        clubs2.setRule(string.black2);
        diamonds2.setRule(string.red2);
        hearts2.setRule(string.red2);
        spades2.setRule(string.black2);

        clubs3.setRule(string.racistGet3);
        diamonds3.setRule(string.red3);
        hearts3.setRule(string.red3);
        spades3.setRule(string.sexistGet3);

        clubs4.setRule(string.black4);
        diamonds4.setRule(string.whoresGet4);
        hearts4 .setRule(string.whoresGive4);
        spades4.setRule(string.black4);

        clubs5.setRule(string.black5);
        diamonds5.setRule(string.guysGet5);
        hearts5.setRule(string.guysGive5);
        spades5.setRule(string.black5);

        clubs6.setRule(string.black61);
        diamonds6.setRule(string.red61);
        hearts6.setRule(string.red62);
        spades6.setRule(string.black62);

        clubs7.setRule(string.black7);
        diamonds7.setRule(string.red7);
        hearts7.setRule(string.red7);
        spades7.setRule(string.black7);

        clubs8.setRule(string.black8);
        diamonds8.setRule(string.red8);
        hearts8.setRule(string.black8);
        spades8.setRule(string.red8);

        clubs9.setRule(string.black9);
        diamonds9.setRule(string.red9);
        hearts9.setRule(string.red9);
        spades9.setRule(string.black9);

        clubs10.setRule(string.c10SimpleRule);
        diamonds10.setRule(string.red10);
        hearts10.setRule(string.red10);
        spades10.setRule(string.s10SimpleRule);

        jackClubs.setRule(string.jackSimpleRule);
        jackDiamonds.setRule(string.jackSimpleRule);
        jackHearts.setRule(string.jackSimpleRule);
        jackSpades.setRule(string.jackSimpleRule);

        queenClubs.setRule(string.queenOfBitches);
        queenDiamonds.setRule(string.queenOfBitches);
        queenHearts.setRule(string.queenOfBitches);
        queenSpades.setRule(string.queenOfBitches);

        kingClubs.setRule(string.kingOfThumbs);
        kingDiamonds.setRule(string.kingOfThumbs);
        kingHearts.setRule(string.kingOfThumbs);
        kingSpades.setRule(string.kingOfThumbs);

        aceClubs.setRule(string.aceSimpleRule);
        aceDiamonds.setRule(string.aceSimpleRule);
        aceHearts.setRule(string.aceSimpleRule);
        aceSpades.setRule(string.aceSimpleRule);

        blackJoker.setRule(string.bJSimpleRule);
        redJoker.setRule(string.rJSimpleRule);

//TODO: setting rules for special cards
        //HALLOWEEN
        //halloweenCard1.setRule(string.halloweenCardRule);
        //halloweenCard2.setRule(string.halloweenCardRule);
        //halloweenCard3.setRule(string.halloweenCardRule);
        //halloweenCard4.setRule(string.halloweenCardRule);

        //christmasCardSanta1.setRule(string.santaCardSimpleRules);
        //christmasCardSanta2.setRule(string.santaCardSimpleRules);
        //christmasCardGrinch1.setRule(string.grinchCardSimpleRules);
        //christmasCardGrinch2.setRule(string.grinchCardSimpleRules);
        //christmasCardGoodGift.setRule(string.goodGiftSimpleRules);
        //christmasCardNaughtyGift.setRule(string.naughtyGiftSimpleRules);


        // assign a detailed explication to these rules
        clubs2.setRulesDetails(string.black2Detailed);
        diamonds2.setRulesDetails(string.red2Detailed);
        hearts2.setRulesDetails(string.red2Detailed);
        spades2.setRulesDetails(string.black2Detailed);

        clubs3.setRulesDetails(string.black3Detailed);
        diamonds3.setRulesDetails(string.red3Detailed);
        hearts3.setRulesDetails(string.red3Detailed);
        spades3.setRulesDetails(string.black31Detailed);

        clubs4.setRulesDetails(string.black4Detailed);
        diamonds4.setRulesDetails(string.whoresTake4Detailed);
        hearts4 .setRulesDetails(string.whoresGive4Detailed);
        spades4.setRulesDetails(string.black4Detailed);

        clubs5.setRulesDetails(string.black5Detailed);
        diamonds5.setRulesDetails(string.guyTake5Detailed);
        hearts5.setRulesDetails(string.guyGive5Detailed);
        spades5.setRulesDetails(string.black5Detailed);

        clubs6.setRulesDetails(string.black6Detailed);
        diamonds6.setRulesDetails(string.red6Detailed);
        hearts6.setRulesDetails(string.red6Detailed);
        spades6.setRulesDetails(string.black61Detailed);

        clubs7.setRulesDetails(string.black7Detailed);
        diamonds7.setRulesDetails(string.red7Detailed);
        hearts7.setRulesDetails(diamonds7.getRulesInDetails());
        spades7.setRulesDetails(clubs7.getRulesInDetails());

        clubs8.setRulesDetails("\n");
        diamonds8.setRulesDetails("\n");
        hearts8.setRulesDetails("\n");
        spades8.setRulesDetails("\n");

        clubs9.setRulesDetails(string.black9Detailed);
        diamonds9.setRulesDetails(string.red9Detailed);
        hearts9.setRulesDetails(diamonds9.getRulesInDetails());
        spades9.setRulesDetails(clubs9.getRulesInDetails());

        clubs10.setRulesDetails(string.black10Detailed);
        diamonds10.setRulesDetails(string.red10Detailed);
        hearts10.setRulesDetails(diamonds10.getRulesInDetails());
        spades10.setRulesDetails(clubs10.getRulesInDetails());

        jackClubs.setRulesDetails("\n");
        jackDiamonds.setRulesDetails(jackClubs.getRulesInDetails());
        jackHearts.setRulesDetails(jackClubs.getRulesInDetails());
        jackSpades.setRulesDetails(jackClubs.getRulesInDetails());

        jackClubs.setJack();
        jackHearts.setJack();
        jackDiamonds.setJack();
        jackSpades.setJack();

        queenClubs.setRulesDetails(string.queenDetailed);
        queenDiamonds.setRulesDetails(queenClubs.getRulesInDetails());
        queenHearts.setRulesDetails(queenClubs.getRulesInDetails());
        queenSpades.setRulesDetails(queenClubs.getRulesInDetails());

        kingClubs.setRulesDetails(string.kingDetailed);
        kingDiamonds.setRulesDetails(kingClubs.getRulesInDetails());
        kingHearts.setRulesDetails(kingClubs.getRulesInDetails());
        kingSpades.setRulesDetails(kingClubs.getRulesInDetails());

        aceClubs.setRulesDetails(string.aceDetailed);
        aceDiamonds.setRulesDetails(aceClubs.getRulesInDetails());
        aceHearts.setRulesDetails(aceClubs.getRulesInDetails());
        aceSpades.setRulesDetails(aceClubs.getRulesInDetails());

        blackJoker.setRulesDetails(string.bJDetailed);
        redJoker.setRulesDetails(string.rJDetailed);

//TODO: setting the detailed explanation of the rules for the special cards
        // HALLOWEEN
        //halloweenCard1.setRulesDetails(string.halloweenCardExplain);
        //halloweenCard2.setRulesDetails(string.halloweenCardExplain);
        //halloweenCard3.setRulesDetails(string.halloweenCardExplain);
        //halloweenCard4.setRulesDetails(string.halloweenCardExplain);

        //christmasCardSanta1.setRulesDetails(string.santaCardExplain);
        //christmasCardSanta2.setRulesDetails(christmasCardSanta1.getRulesInDetails());
        //christmasCardGrinch1.setRulesDetails(string.grinchCardExplain);
        //christmasCardGrinch2.setRulesDetails(christmasCardGrinch1.getRulesInDetails());
        //christmasCardGoodGift.setRulesDetails(string.goodGiftExplain);
        //christmasCardNaughtyGift.setRulesDetails(string.naughtyGiftExplain);

        //Assign the value of each card

        clubs2.setValue(2);
        clubs3.setValue(3);
        clubs4.setValue(4);
        clubs5.setValue(5);
        clubs6.setValue(6);
        clubs7.setValue(7);
        clubs9.setValue(9);
        clubs10.setValue(10);
        queenClubs.setValue(12);
        kingClubs.setValue(13);
        aceClubs.setValue(1);

        diamonds2.setValue(2.5);
        diamonds3.setValue(3.5);
        diamonds4.setValue(4.5);
        diamonds5.setValue(5.5);
        diamonds6.setValue(6.5);
        diamonds7.setValue(7);
        diamonds9.setValue(9);
        diamonds10.setValue(10);
        queenDiamonds.setValue(12);
        kingDiamonds.setValue(13);
        aceDiamonds.setValue(1);

        hearts2.setValue(2.5);
        hearts3.setValue(3.5);
        hearts4.setValue(4.5);
        hearts5.setValue(5.5);
        hearts6.setValue(6.5);
        hearts7.setValue(7);
        hearts9.setValue(9);
        hearts10.setValue(10);
        queenHearts.setValue(12);
        kingHearts.setValue(13);
        aceHearts.setValue(1);

        spades2.setValue(2);
        spades3.setValue(3);
        spades4.setValue(4);
        spades5.setValue(5);
        spades6.setValue(6);
        spades7.setValue(7);
        spades9.setValue(9);
        spades10.setValue(10);
        queenSpades.setValue(12);
        kingSpades.setValue(13);
        aceSpades.setValue(1);

        blackJoker.setValue(15);
        redJoker.setValue(14);

//TODO: setting the value for the special cards
        // HALLOWEEN
        //halloweenCard1.setValue(31);
        //halloweenCard2.setValue(31);
        //halloweenCard3.setValue(31);
        //halloweenCard4.setValue(31);

        //christmasCardSanta1.setValue(26);
        //christmasCardSanta2.setValue(26);
        //christmasCardGrinch1.setValue(23);
        //christmasCardGrinch2.setValue(23);
        //christmasCardGoodGift.setValue(24);
        //christmasCardNaughtyGift.setValue(25);

        //Assign the image of each card

        clubs2.assignImage(drawable.c2_of_clubs);
        diamonds2.assignImage(drawable.c2_of_diamonds);
        hearts2.assignImage(drawable.c2_of_hearts);
        spades2.assignImage(drawable.c2_of_spades);

        clubs3.assignImage(drawable.c3_of_clubs);
        diamonds3.assignImage(drawable.c3_of_diamonds);
        hearts3.assignImage(drawable.c3_of_hearts);
        spades3.assignImage(drawable.c3_of_spades);

        clubs4.assignImage(drawable.c4_of_clubs);
        diamonds4.assignImage(drawable.c4_of_diamonds);
        hearts4.assignImage(drawable.c4_of_hearts);
        spades4.assignImage(drawable.c4_of_spades);

        clubs5.assignImage(drawable.c5_of_clubs);
        diamonds5.assignImage(drawable.c5_of_diamonds);
        hearts5.assignImage(drawable.c5_of_hearts);
        spades5.assignImage(drawable.c5_of_spades);

        clubs6.assignImage(drawable.c6_of_clubs);
        diamonds6.assignImage(drawable.c6_of_diamonds);
        hearts6.assignImage(drawable.c6_of_hearts);
        spades6.assignImage(drawable.c6_of_spades);

        clubs7.assignImage(drawable.c7_of_clubs);
        diamonds7.assignImage(drawable.c7_of_diamonds);
        hearts7.assignImage(drawable.c7_of_hearts);
        spades7.assignImage(drawable.c7_of_spades);

        clubs8.assignImage(drawable.c8_of_clubs);
        diamonds8.assignImage(drawable.c8_of_diamonds);
        hearts8.assignImage(drawable.c8_of_hearts);
        spades8.assignImage(drawable.c8_of_spades);

        clubs9.assignImage(drawable.c9_of_clubs);
        diamonds9.assignImage(drawable.c9_of_diamonds);
        hearts9.assignImage(drawable.c9_of_hearts);
        spades9.assignImage(drawable.c9_of_spades);

        clubs10.assignImage(drawable.c10_of_clubs);
        diamonds10.assignImage(drawable.c10_of_diamonds);
        hearts10.assignImage(drawable.c10_of_hearts);
        spades10.assignImage(drawable.c10_of_spades);

        jackClubs.assignImage(drawable.jack_of_clubs2);
        jackDiamonds.assignImage(drawable.jack_of_diamonds2);
        jackHearts.assignImage(drawable.jack_of_hearts2);
        jackSpades.assignImage(drawable.jack_of_spades2);

        queenClubs.assignImage(drawable.queen_of_clubs2);
        queenDiamonds.assignImage(drawable.queen_of_diamonds2);
        queenHearts.assignImage(drawable.queen_of_hearts2);
        queenSpades.assignImage(drawable.queen_of_spades2);

        kingClubs.assignImage(drawable.king_of_clubs2);
        kingDiamonds.assignImage(drawable.king_of_diamonds2);
        kingHearts.assignImage(drawable.king_of_hearts2);
        kingSpades.assignImage(drawable.king_of_spades2);

        aceClubs.assignImage(drawable.ace_of_clubs);
        aceDiamonds.assignImage(drawable.ace_of_diamonds);
        aceHearts.assignImage(drawable.ace_of_hearts);
        aceSpades.assignImage(drawable.ace_of_spades);

        blackJoker.assignImage(drawable.black_joker);
        redJoker.assignImage(drawable.red_joker);

//TODO: assigning the image of the christmas cards
        //HALLOWEEN
        //halloweenCard1.assignImage(drawable.orangehalloweencard);
        //halloweenCard2.assignImage(drawable.orangehalloweencard);
        //halloweenCard3.assignImage(drawable.orangehalloweencard);
        //halloweenCard4.assignImage(drawable.orangehalloweencard);

        //christmasCardSanta1.assignImage(drawable.santacard);
        //christmasCardSanta2.assignImage(drawable.santacard);
        //christmasCardGrinch1.assignImage(drawable.grinchcard);
        //christmasCardGrinch2.assignImage(drawable.grinchcard);
        //christmasCardGoodGift.assignImage(drawable.giftcard);
        //christmasCardNaughtyGift.assignImage(drawable.badgiftcard);

// add every card to the deck

       add(aceClubs);
       add(aceDiamonds);
       add(aceHearts);
       add(aceSpades);

       add(blackJoker);
       add(redJoker);

       add(clubs10);
       add(diamonds10);
       add(hearts10);
       add(hearts10);

       add(clubs2);
       add(diamonds2);
       add(hearts2);
       add(spades2);

       add(clubs3);
       add(diamonds3);
       add(hearts3);
       add(spades3);

       add(clubs4);
       add(diamonds4);
       add(hearts4);
       add(spades4);

       add(clubs5);
       add(diamonds5);
       add(hearts5);
       add(spades5);

       add(clubs6);
       add(diamonds6);
       add(hearts6);
       add(spades6);

       add(clubs7);
       add(diamonds7);
       add(hearts7);
       add(spades7);

       add(clubs8);
       add(diamonds8);
       add(hearts8);
       add(spades8);

       add(clubs9);
       add(diamonds9);
       add(hearts9);
       add(spades9);

       add(jackClubs);
       add(jackDiamonds);
       add(jackHearts);
       add(jackSpades);

       add(queenClubs);
       add(queenDiamonds);
       add(queenHearts);
       add(queenSpades);

       add(kingClubs);
       add(kingDiamonds);
       add(kingHearts);
       add(kingSpades);

//TODO: adding the special cards to the deck
       //SPECIAL HALLOWEEN
        //add(halloweenCard1);
        //add(halloweenCard3);
        //add(halloweenCard2);
        //add(halloweenCard4);

        //add(christmasCardSanta1);
        //add(christmasCardSanta2);
        //add(christmasCardGrinch1);
        //add(christmasCardGrinch2);
        //add(christmasCardGoodGift);
        //add(christmasCardNaughtyGift);
    }

    /**
     * Method to add cards to the cardDeck ArrayList
     * @param card The Card to add
     */
    private void add(Card card) {
        cards.add(card);
    }

    /**
     * @return The size of the deck
     */
    int getDeckSize()
    {
        return deck.size();
    }


    /**
     * Picks a random card from the deck and removes it from the ArrayList
     * @return A random Card
     */
    Card choseRandomCard(int i)
    {
        return deck.get(i);
    }

    /**
     * Method to remove a card from the deck
     * @param index The index of the Card in the ArrayList
     */
    @TargetApi(24)
    private void removeCard(int index) {
        cards.remove(index);
    }

    /**
     * Method to randomly take a card for the cards ArrayList and adds to the deck ArrayList
     */
    private void makeDeck() {
        r = new Random();
        for (int i = cards.size(); i > 0; i--) {
            int index = r.nextInt(cards.size());
            deck.add(cards.get(index));
            removeCard(index);
        }
    }
}