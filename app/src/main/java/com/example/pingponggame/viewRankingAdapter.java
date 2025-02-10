package com.example.pingponggame;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pingponggame.R;

import java.util.List;

public class viewRankingAdapter extends RecyclerView.Adapter<viewRankingAdapter.viewRankingViewHolder> {
    private List<score> user_list;

    public viewRankingAdapter(List<score> user_list) {
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public viewRankingAdapter.viewRankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_ranking_item, parent, false);
        return new viewRankingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewRankingAdapter.viewRankingViewHolder holder, int position) {
        score currentScore = user_list.get(position);
        holder.txt_rank.setText(String.valueOf(position + 1));
        holder.txt_name.setText(currentScore.getUserName());
        holder.txt_id.setText(currentScore.getUserID());
        holder.txt_score.setText(String.valueOf(currentScore.getScore()));
        if (Variable.user.getUserID().equals(currentScore.getUserID())){
            holder.bgr_item.setImageResource(R.drawable.icon_ranking_user);
        }
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }
    public class viewRankingViewHolder extends RecyclerView.ViewHolder{
        TextView txt_rank, txt_name, txt_score, txt_id;
        ImageView bgr_item;

        public viewRankingViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_rank = itemView.findViewById(R.id.txt_rank);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_score = itemView.findViewById(R.id.txt_score);
            txt_id = itemView.findViewById(R.id.txt_id);
            bgr_item = itemView.findViewById(R.id.back_item_ranking);
        }
    }
}
