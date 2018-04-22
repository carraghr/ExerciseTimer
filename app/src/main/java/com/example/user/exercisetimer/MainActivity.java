package com.example.user.exercisetimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.user.exercisetimer.Exercises.Exercise;
import com.example.user.exercisetimer.Exercises.ExerciseList;
import com.example.user.exercisetimer.FileHandler.FileHandler;
import com.example.user.exercisetimer.LIstViewPopulation.ExerciseItemAdapter;

public class MainActivity extends AppCompatActivity {

    FileHandler file;
    ExerciseList exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        file = new FileHandler(MainActivity.this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.additional_timer_popup, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        Toolbar.LayoutParams.WRAP_CONTENT,
                        Toolbar.LayoutParams.WRAP_CONTENT);

                Button btnSubmit = (Button)popupView.findViewById(R.id.submit);

                final EditText name = (EditText)popupView.findViewById(R.id.editText3);
                final EditText times = (EditText)popupView.findViewById(R.id.numOfTimes);

                final EditText holdHour = (EditText)popupView.findViewById(R.id.holdHour);
                holdHour.addTextChangedListener(new TextTimerWatcher(holdHour));

                final EditText holdMinute = (EditText)popupView.findViewById(R.id.holdMinute);
                holdMinute.addTextChangedListener(new TextTimerWatcher(holdMinute));

                final EditText holdSecond = (EditText)popupView.findViewById(R.id.holdSecond);
                holdSecond.addTextChangedListener(new TextTimerWatcher(holdSecond));

                final EditText restHour = (EditText)popupView.findViewById(R.id.restHour);
                restHour.addTextChangedListener(new TextTimerWatcher(restHour));

                final EditText restMinute = (EditText)popupView.findViewById(R.id.restMinute);
                restMinute.addTextChangedListener(new TextTimerWatcher(restMinute));

                final EditText restSecond = (EditText)popupView.findViewById(R.id.restSecond);
                restSecond.addTextChangedListener(new TextTimerWatcher(restSecond));

                btnSubmit.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String exercise = name.getText().toString();
                        if(!exercise.equals("") || ! exercises.contains(exercise)){
                            if(!times.getText().toString().equals("")){
                                int repeats = Integer.parseInt(times.getText().toString());

                                if(holdHour.getText().toString().equals("")){
                                    holdHour.setText("0");
                                }
                                if(holdMinute.getText().toString().equals("")){
                                    holdMinute.setText("0");
                                }
                                if(holdSecond.getText().toString().equals("")){
                                    holdSecond.setText("0");
                                }

                                if(restHour.getText().toString().equals("")){
                                    restHour.setText("0");
                                }
                                if(restMinute.getText().toString().equals("")){
                                    restMinute.setText("0");
                                }
                                if(restSecond.getText().toString().equals("")){
                                    restSecond.setText("0");
                                }

                                long hold = TimeConverter.toMilliSeconds(Integer.parseInt(holdHour.getText().toString()),
                                        Integer.parseInt(holdMinute.getText().toString()),
                                        Integer.parseInt(holdSecond.getText().toString()));

                                long rest = TimeConverter.toMilliSeconds(Integer.parseInt(restHour.getText().toString()),
                                        Integer.parseInt(restMinute.getText().toString()),
                                        Integer.parseInt(restSecond.getText().toString()));

                                file.addTimer(exercise,repeats,hold,rest);

                                popupWindow.dismiss();


                                exercises.putExercise(new Exercise(exercise,repeats,hold,rest));
                                populateList();
                            }

                        }
                    }});
                popupWindow.setFocusable(true);
                popupWindow.update();
                popupWindow.showAtLocation(fab, Gravity.CENTER, 0, 0);
            }});

        exercises = file.getListOfExercises();
        populateList();
        registerClickCallback();
        ListView myListView = (ListView) findViewById(R.id.exercise_selection);
        registerForContextMenu(myListView);
    }

    private void populateList(){
        ExerciseItemAdapter adapter = new ExerciseItemAdapter(this,exercises.getList());
        ListView list = (ListView) this.findViewById(R.id.exercise_selection);//this id is from activity_main
        list.setAdapter(adapter);
    }

    private void registerClickCallback(){
        ListView list = (ListView) findViewById(R.id.exercise_selection);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LinearLayout row = (LinearLayout)((LinearLayout) view).getChildAt(0);
                TextView column = (TextView)row.getChildAt(0);
                String text = column.getText().toString();

                Exercise selectedExercise = exercises.getExercise(text);

                Bundle bundle;
                Intent intent;
                bundle = new Bundle();
                bundle.putSerializable("selectedExercise",selectedExercise);

                intent = new Intent(MainActivity.this,TimerActivity.class);
                intent.putExtras(bundle);

                MainActivity.this.startActivity(intent);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.delete){
            Exercise s = exercises.getExercise(info.position);
            file.removeExercise(s);
            exercises.remove(s.getName());
            populateList();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}