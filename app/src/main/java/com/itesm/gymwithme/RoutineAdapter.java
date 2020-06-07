package com.itesm.gymwithme;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Routine> routines;

    public RoutineAdapter(Context context, ArrayList<Routine> routines) {
        this.context = context;
        this.routines = routines;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view_routine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routine item = routines.get(position);

        holder.textTitle.setText(item.getTitle());
        holder.textMuscle.setText(item.printMuscles());
        setColor(holder.imgLevel, item.getLevel());
        holder.textLevel.setText(item.getLevel());
        holder.textAuthor.setText(item.getAuthor().getUsername());
        holder.textContent.setText(item.printExercises());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putCharSequence("title", item.getTitle());
                data.putCharSequence("muscles", item.printMuscles());
                data.putCharSequence("level", item.getLevel());
                data.putCharSequence("author", item.getAuthor().getUsername());
                data.putCharSequence("workout", item.printExercises());
                Navigation.findNavController(holder.itemView).navigate(
                        R.id.action_routineListFragment_to_routineDetailFragment, data);
            }
        });


    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        TextView textMuscle;
        TextView textLevel;
        TextView textAuthor;
        TextView textContent;
        ImageView imgLevel;

        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.title_text);
            textMuscle = itemView.findViewById(R.id.muscle_text);
            textLevel = itemView.findViewById(R.id.difficulty_text);
            imgLevel = itemView.findViewById(R.id.difficulty_text_title);
            textAuthor = itemView.findViewById(R.id.author_text);
            textContent = itemView.findViewById(R.id.info_text);

            cardView = itemView.findViewById(R.id.card_routine_rv);



        }
    }

    private void setColor(ImageView imgView, String level) {
        if (level.equals("Beginner")) {
            imgView.setColorFilter(context.getResources().getColor(R.color.colorEasy));
        } else if (level.equals("Intermediate")) {
            imgView.setColorFilter(context.getResources().getColor(R.color.colorIntermediate));
        } else if (level.equals("Advanced")) {
            imgView.setColorFilter(context.getResources().getColor(R.color.colorExpert));
        }
    }
}
