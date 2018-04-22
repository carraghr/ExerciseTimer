package com.example.user.exercisetimer.Exercises;

import java.util.ArrayList;

public class ExerciseList {

    private ArrayList<Exercise> exercises;

    private int numberOfExercises = 0;

    public ExerciseList(){
        exercises = new ArrayList<>();
    }

    public Exercise getExercise(int index){
        return exercises.get(index);
    }

    public String[] getExerciseNames(){
        String[] names = new String[exercises.size()];
        for(int i=0; i < exercises.size(); i++){
            names[i] = exercises.get(i).getName();
        }
        return names;
    }

    public Exercise getExercise(String name){
        for(int i=0;i<exercises.size();i++ ){
            if(exercises.get(i).getName().equals(name)){
                return exercises.get(i);
            }
        }
        return null;
    }

    public ArrayList getList(){
        return exercises;
    }

    public void putExercise(Exercise temp){
        exercises.add(temp);
    }

    public boolean contains(String name){
        for(int i=0;i<exercises.size();i++ ){
            if(exercises.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean remove(String name){
        for(int i=0;i<exercises.size();i++ ){
            if(exercises.get(i).getName().equals(name)){
                exercises.remove(i);
                return true;
            }
        }
        return false;
    }
}
