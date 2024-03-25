package com.seminarski.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.seminarski.quizapp.QuizBridge.*;

import java.util.ArrayList;

public class QuizDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " + QuestionsTable.COLUMN_ANSWER1 + " TEXT, " + QuestionsTable.COLUMN_ANSWER2 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER3 + " TEXT, " + QuestionsTable.COLUMN_ANSWER4 + " TEXT, " + QuestionsTable.COLUMN_CORRECTANSWER + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillTableQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillTableQuestions() {
        Question q1 = new Question("What's the capital city of Germany?", "Frankfurt", "Berlin", "Hamburg", "Koln", 2);
        addQuestion(q1);
        Question q2 = new Question("What's the name of the famous orange cat?", "Hector", "Remy", "Dennis", "Garfield", 4);
        addQuestion(q2);
        Question q3 = new Question("Which Croatian mountain has the highest peak?", "Dinara", "Sjeverni Velebit", "Biokovo", "Južni Velebit", 1);
        addQuestion(q3);
        Question q4 = new Question("What's the most common English name in the world?", "John", "James", "Mary", "Robert", 2);
        addQuestion(q4);
        Question q5 = new Question("When did Maradona score his famous goal called 'The Hand of God'?", "1978", "1982", "1986", "1990", 3);
        addQuestion(q5);
        Question q6 = new Question("What's the number of friends in famous sitcom F.R.I.E.N.D.S?", "4", "5", "6", "7", 3);
        addQuestion(q6);
        Question q7 = new Question("When did World War I end?", "1914", "1916", "1918", "1920", 3);
        addQuestion(q7);
        Question q8 = new Question("What's the capital city of Colombia?", "Bogota", "Quito", "Lima", "Buenos Aires", 1);
        addQuestion(q8);
        Question q9 = new Question("When did Croatia declare its dependency?", "1989", "1990", "1991", "1992", 3);
        addQuestion(q9);
        Question q10 = new Question("Who was the first person in space?", "Neil Armstrong", "Buzz Aldrin", "Alexei Leonov", "Yuri Gagarin", 4);
        addQuestion(q10);
        Question q11 = new Question("How many time zones are there in Russia?", "9", "11", "13", "15", 2);
        addQuestion(q11);
        Question q12 = new Question("Which of the following empires had no written language?", "Incan", "Aztec", "Egyptian", "Roman", 1);
        addQuestion(q12);
        Question q13 = new Question("Until 1923, what was the Turkish city of Istanbul called?", "Istanbul", "Alexandria", "Ankara", "Constantinople", 4);
        addQuestion(q13);
        Question q14 = new Question("What's the longest river in the world?", "Amazon", "Yangtze", "Nile", "Congo", 3);
        addQuestion(q14);
        Question q15 = new Question("Which English football team is known as ‘Hammers’?", "Arsenal", "Brighton", "Milwall", "West Ham", 4);
        addQuestion(q15);
        Question q16 = new Question("What city do The Beatles come from?", "Peckham", "London", "Manchester", "Liverpool", 4);
        addQuestion(q16);
        Question q17 = new Question("How many keys does a classic piano have?", "88", "89", "90", "91", 1);
        addQuestion(q17);
        Question q18 = new Question("Who invented the World Wide Web?", "Dennis Ritchie", "Tim Berners-Lee", "Guido van Rossum", "Vint Cerf", 2);
        addQuestion(q18);
        Question q19 = new Question("When was the first issue of Vogue published?", "1892", "1960", "1979", "2000", 1);
        addQuestion(q19);
        Question q20 = new Question("How many stripes are there on the US flag?", "11", "13", "15", "17", 2);
        addQuestion(q20);
        Question q21 = new Question("Who entered a contest to find his own look-alike and came 3rd?", "Elvis Presley", "Marilyn Monroe", "Madonna", "Charlie Chaplin", 4);
        addQuestion(q21);
        Question q22 = new Question("What is the world's most populated country?", "India", "Russia", "China", "USA", 3);
        addQuestion(q22);
        Question q23 = new Question("What is the world's largest continent?", "Africa", "Asia", "Europe", "North America", 2);
        addQuestion(q23);
        Question q24 = new Question("Which country formerly ruled Iceland?", "Norway", "Finland", "Denmark", "Germany", 3);
        addQuestion(q24);
        Question q25 = new Question("What is the smallest country in the world?", "Vatican City", "Monaco", "Luxembourg", "Andorra", 1);
        addQuestion(q25);
        Question q26 = new Question("How long was the Hundred Years' War?", "99", "100", "108", "116", 4);
        addQuestion(q26);
        Question q27 = new Question("What does the word 'Trojan' refer to in the computer world?", "Virus", "Malware", "Spyware", "Worm", 2);
        addQuestion(q27);
        Question q28 = new Question("Who created C programming language?", "Ken Thompson", "Dennis Ritchie", "Robin Milner", "Frieder Nake", 2);
        addQuestion(q28);
        Question q29 = new Question("What does 1 TB (terabyte) equal to?", "1000 GB", "1008 GB", "1016 GB", "1024 GB", 4);
        addQuestion(q29);
        Question q30 = new Question("What's the number of bits used in IPv6 address?", "32 bit", "64 bit", "128 bit", "256 bit", 3);
        addQuestion(q30);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_ANSWER1, question.getAnswer1());
        cv.put(QuestionsTable.COLUMN_ANSWER2, question.getAnswer2());
        cv.put(QuestionsTable.COLUMN_ANSWER3, question.getAnswer3());
        cv.put(QuestionsTable.COLUMN_ANSWER4, question.getAnswer4());
        cv.put(QuestionsTable.COLUMN_CORRECTANSWER, question.getCorrectAnswer());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do{
                Question question = new Question();
                question.setQuestion((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION))));
                question.setAnswer1((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER1))));
                question.setAnswer2((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER2))));
                question.setAnswer3((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER3))));
                question.setAnswer4((c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER4))));
                question.setCorrectAnswer((c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CORRECTANSWER))));

                questionList.add(question);

            }while(c.moveToNext());

        }
        c.close();
        return questionList;
    }
}
