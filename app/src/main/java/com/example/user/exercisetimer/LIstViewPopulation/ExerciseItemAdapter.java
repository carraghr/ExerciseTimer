package com.example.user.exercisetimer.LIstViewPopulation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.exercisetimer.Exercises.Exercise;
import com.example.user.exercisetimer.R;

import java.util.ArrayList;

public class ExerciseItemAdapter extends ArrayAdapter<Exercise> {

    private final Context context;
    private final ArrayList<Exercise> itemsArrayList;

    public ExerciseItemAdapter(Context context, ArrayList<Exercise> itemsArrayList) {

        super(context, R.layout.exercise_item, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.exercise_item, parent, false);
        // 3. Get the two text view from the rowView
        TextView exerciseName = (TextView) rowView.findViewById(R.id.exercise_name);
        TextView holdTime = (TextView) rowView.findViewById(R.id.exercise_hold);
        TextView restTime = (TextView) rowView.findViewById(R.id.exercise_rest);
        TextView times = (TextView) rowView.findViewById(R.id.times);
        // 4. Set the text for textView
        exerciseName.setText(itemsArrayList.get(position).getName());
        holdTime.setText(itemsArrayList.get(position).getHoldTIme());
        restTime.setText(itemsArrayList.get(position).getRestTime());
        times.setText(itemsArrayList.get(position).getTimes());
        // 5. retrn rowView
        return rowView;
    }
}
