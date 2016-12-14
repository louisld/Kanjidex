package fr.bloome.kanjidex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by louis on 11/12/2016.
 */

public class WordDAO extends DAOBase {

    public static final String TABLE_NAME = "words";
    public static final String KANJI_NUMBER = "word_kanji_number";
    public static final String KANJIS = "word_kanjis";
    public static final String HURIGANA = "word_hurigana";
    public static final String TRADUCTION = "word_traduction";
    public static final String GRADE = "word_grade";

    public WordDAO(Context pContext){
        super(pContext);
    }

    public void ajouter(Word word){
        ContentValues value = new ContentValues();
        value.put(KANJI_NUMBER, word.getKanji_number());
        value.put(KANJIS, word.getKanjis());
        value.put(HURIGANA, word.getKanji_hurigana());
        value.put(TRADUCTION, word.getTraduction());
        value.put(GRADE, word.getGrade());
        mDb.insert(this.TABLE_NAME, null, value);
    }
    public void clear(){
        mDb.execSQL("DELETE FROM words");
    }

    public ArrayList<Word> getWordWithGrade(int kanjiNumber, int grade){
        ArrayList<Word> words = new ArrayList<>();
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE word_kanji_number=? AND word_grade=?", new String[]{String.valueOf(kanjiNumber), String.valueOf(grade)});
        while(c.moveToNext()){
            words.add(new Word(c.getInt(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5)));
        }
        c.close();
        return words;
    }

    public boolean isGrade(int kanjiNumber, int grade){
        boolean result;
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE word_kanji_number=? AND word_grade=?", new String[]{String.valueOf(kanjiNumber), String.valueOf(grade)});
        if(c.getCount() != 0)
            return true;
        else
            return false;
    }
}
