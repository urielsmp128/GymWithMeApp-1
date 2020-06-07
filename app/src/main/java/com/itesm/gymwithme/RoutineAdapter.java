package com.itesm.gymwithme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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
        holder.imgLevel.setColorFilter(context.getResources().getColor(R.color.colorPrimary));
        holder.textLevel.setText(item.getLevel());
        holder.textAuthor.setText(item.getAuthor().getName());
        holder.textContent.setText(item.printExercises());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.title_text);
            textMuscle = itemView.findViewById(R.id.muscle_text);
            textLevel = itemView.findViewById(R.id.difficulty_text);
            imgLevel = itemView.findViewById(R.id.difficulty_img);
            textAuthor = itemView.findViewById(R.id.author_text);
            textContent = itemView.findViewById(R.id.info_text);



        }
    }
}
