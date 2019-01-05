package com.drunkler.drunkly;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import static com.drunkler.drunkly.App.getContext;

/**
 * The Adapter user for the list of player
 *
 * @author Oscar
 *
 * @reference Stackoverflow.com
 */
public class ListOfPlayersAdapter extends RecyclerView.Adapter<ListOfPlayersAdapter.MyViewHolder>{

    private ArrayList<Player> players; // The array to old every player entered

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_name_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.playerName.setHint(players.get(position).getName());
    }
        @Override
    public int getItemCount() {
        return players.size();
    }

    ListOfPlayersAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private EditText playerName;

        MyViewHolder(View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerInput);
            playerName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!playerName.getText().toString().contains(getContext().getString(R.string.playerString))) {
                        players.get(getAdapterPosition()).setName(playerName.getText().toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

    }
}