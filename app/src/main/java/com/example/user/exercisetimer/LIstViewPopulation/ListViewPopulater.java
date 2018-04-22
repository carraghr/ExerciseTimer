package com.example.user.exercisetimer.LIstViewPopulation;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.exercisetimer.Exercises.ExerciseList;
import com.example.user.exercisetimer.R;

public class ListViewPopulater {
    public static void populateExerciseList(Activity context, String[] exercisesList){
        //Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,                    //Context for the activity
                R.layout.exercise_item,     //Layout to use (create)
                exercisesList               //Items to be displayed
        );
        //Configure the list view
        ListView list = (ListView) context.findViewById(R.id.exercise_selection);//this id is from activity_main
        list.setAdapter(adapter);
    }
}
