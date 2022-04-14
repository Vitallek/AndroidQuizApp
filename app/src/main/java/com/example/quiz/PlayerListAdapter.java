package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.MyViewHolder> {

    private List<PlayerDBmodel> playerListFromDb;
    private Context context;

    public PlayerListAdapter(List<PlayerDBmodel> playerListFromDb, Context context) {
        this.playerListFromDb = playerListFromDb;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_for_list,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemID.setText(String.valueOf(playerListFromDb.get(position).getId()));
        holder.itemName.setText(playerListFromDb.get(position).getName());
        holder.itemScore.setText(String.valueOf(playerListFromDb.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return playerListFromDb.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemID;
        TextView itemName;
        TextView itemScore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.itemID);
            itemName = itemView.findViewById(R.id.itemName);
            itemScore = itemView.findViewById(R.id.itemScore);
        }
    }
}
