package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.drunkler.drunkly.App.getContext;

/**
 * The adapter used for the mixers list view in the Tips for Drinks Menu
 *
 * @author Oscar
 */
public class MixerAdapter extends RecyclerView.Adapter<MixerAdapter.MyViewHolder>{

    private List<Drink> mixerList; // The array list to hold every mixer

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mixerCat, mixers;


        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mixerCat = view.findViewById(R.id.mixerCat2);
            mixers = view.findViewById(R.id.mixers2);
        }

        @Override
        public void onClick(View v) {
            if (mixers.getVisibility() == mixerCat.getVisibility()) {
                mixers.setVisibility(View.GONE);
            }
            else {
                mixers.setVisibility(View.VISIBLE);
            }
        }
    }

    MixerAdapter(ArrayList<Drink> mixerList) {
        this.mixerList = mixerList;
    }

    @NonNull
    @Override
    public MixerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drinks_row, parent, false);

        return new MixerAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MixerAdapter.MyViewHolder holder, int position) {
        Drink drink = mixerList.get(position);
        StringBuilder mixerStringList = new StringBuilder(getContext().getString(R.string.whatMix)+ "\n\n");
        holder.mixerCat.setText(drink.getSorM());
        for (String mixer: drink.getMixers()) {
            mixerStringList.append(mixer).append("\n");
        }
        holder.mixers.setText(mixerStringList.toString().trim());
    }

    @Override
    public int getItemCount() {
        return mixerList.size();
    }
}
