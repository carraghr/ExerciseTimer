package com.example.user.exercisetimer.FileHandler;

import android.content.Context;
import android.widget.Toast;

import com.example.user.exercisetimer.Exercises.Exercise;
import com.example.user.exercisetimer.Exercises.ExerciseList;


import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHandler{

    private Context context;
    private File file;

    private final static String FileName = "timing.dat";

    public FileHandler(Context context){
        this.context = context;
        file = new File(this.context.getFilesDir().getAbsolutePath(), FileName);
        if(!file.exists()){
            try{
                file.getParentFile().mkdirs();
                file.createNewFile();
                RandomAccessFile temp = new RandomAccessFile(file,"rw");
                temp.close();
            }catch(IOException e){
                Toast.makeText(context, e.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addTimer(String name, int times, long holdTime, long restTIme){
        String line = "";
        try{
            //create output stream
            RandomAccessFile re = new RandomAccessFile(file,"rw");
            re.seek(re.length());
            line += String.format("%-30s :", name);
            line += String.format("%-2s :", ""+times);
            line += String.format("%-30s :",Long.toString(holdTime));
            line += String.format("%-30s",Long.toString(restTIme));

            re.writeUTF(line);

            re.close();
        }catch(Exception e){}
    }

    public ExerciseList getListOfExercises(){

        ExerciseList exerciseList = new ExerciseList();

        try{
            String line;
            RandomAccessFile temp = new RandomAccessFile(file, "rw");
            while(true){
                line = temp.readUTF().trim();
                String [] elements = line.split(":");
                Exercise tempExercise = new Exercise(elements[0].trim(),Integer.parseInt(elements[1].trim()),Long.parseLong(elements[2].trim()),Long.parseLong(elements[3].trim()));
                exerciseList.putExercise(tempExercise);
            }
        }catch(EOFException e){
        }catch(Exception e){}

        return exerciseList;
    }

    public void changeExercise(Exercise exercise){
        try{
            String line;
            RandomAccessFile temp = new RandomAccessFile(file, "rw");
            while(true){
                long pointer = temp.getFilePointer();
                line = temp.readUTF().trim();
                String [] elements = line.split(":");
                if(elements[0].trim().equals(exercise.getName())){
                    String newline = String.format("%-30s :", (exercise.getName()+"changed"));
                    newline += String.format("%-2s :", ""+exercise.getTimes());
                    newline += String.format("%-30s :",Long.toString(exercise.getHoldTImeInMili()));
                    newline += String.format("%-30s",Long.toString(exercise.getRestTImeInMili()));
                    temp.seek(pointer);
                    temp.writeUTF(newline);
                    temp.close();
                    return;
                }
            }
        }catch(EOFException e){
        }catch(Exception e){}
    }

    public void removeExercise(Exercise exercise){
        try{
            RandomAccessFile temp = new RandomAccessFile(file, "rw");

            StringBuilder s = new StringBuilder();
            try{
                String line;
                while(true){
                    line = temp.readUTF().trim();
                    String [] elements = line.split(":");
                    if(!(elements[0].trim()).equals(exercise.getName())){
                        s.append(line+"\n\r");
                    }
                }
            }catch(EOFException e){
                temp.setLength(0);
                temp.seek(0);
                String[] lines = s.toString().split("\n\r");
                for(String line: lines){
                    temp.writeUTF(line);
                }
                temp.close();
            }
        }catch(EOFException e){}
        catch(Exception e){}
    }
}