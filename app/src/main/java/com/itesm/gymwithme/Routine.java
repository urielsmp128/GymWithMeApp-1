package com.itesm.gymwithme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routine {

    @SerializedName("_id")
    private String id;
    private List<String> muscleGroups;
    private String title;
    private String level;
    private List<String> content;
    private User author;

    public Routine(List<String> muscleGroups, String title, String level, List<String> content) {
        this.muscleGroups = muscleGroups;
        this.title = title;
        this.level = level;
        this.content = content;
    }

    public Routine(String id, List<String> muscleGroups, String title, String level, List<String> content,
                   User author) {
        this.id = id;
        this.muscleGroups = muscleGroups;
        this.title = title;
        this.level = level;
        this.content = content;
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String printMuscles() {

        String str = muscleGroups.get(0);
        for (int i = 1; i < muscleGroups.size(); i++) {
            str += ", " + muscleGroups.get(i);
        }

        return str;
    }

    public String printExercises() {
        int step = 1;
        int i = 0;
        String str = step++ + ": " + content.get(i++);
        for ( ; i < content.size(); i++) {
            str += "\n" + step++ + ": " + content.get(i);
        }

        return str;
    }
}
