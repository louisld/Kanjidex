package fr.bloome.kanjidex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by louis on 10/12/2016.
 */

public class KanjiDAO extends DAOBase {
    public static final String TABLE_NAME = "kanjis";
    public static final String NUMBER = "kanji_id";
    public static final String KANJI = "kanji_kanji";
    public static final String HURIGANA = "kanji_hurigana";
    public static final String LINES = "kanji_lines";

    public static final String KANJI_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS `kanjis` (\n" +
                    "  `kanji_id` int(11) NOT NULL,\n" +
                    "  `kanji_hurigana` varchar(5)  NOT NULL,\n" +
                    "  `kanji_kanji` varchar(1)  NOT NULL,\n" +
                    "  `kanji_lines` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`kanji_id`)\n" +
                    ")";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public KanjiDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param k the kanji to add to database
     */
    public void ajouter(Kanji k) {
        ContentValues value = new ContentValues();
        value.put(this.NUMBER, k.getNumber());
        value.put(this.KANJI, k.getKanji());
        value.put(this.HURIGANA, k.getHurigana());
        value.put(this.LINES, k.getLines());
        mDb.insert(this.TABLE_NAME, null, value);
    }

    /**
     * @param number the number of the kanji
     */
    public void supprimer(long number) {
        mDb.delete(TABLE_NAME, NUMBER + " = ?", new String[] {String.valueOf(number)});
    }

    public void clear(){
        mDb.execSQL("DELETE FROM kanjis");
    }

    /**
     * @param k the modified kanji
     */
    public void modifier(Kanji k) {
        ContentValues value = new ContentValues();
        value.put(NUMBER, k.getNumber());
        value.put(KANJI, k.getKanji());
        value.put(HURIGANA, k.getHurigana());
        value.put(LINES, k.getLines());
        mDb.update(TABLE_NAME, value, NUMBER  + " = ?", new String[] {String.valueOf(k.getNumber())});
        Log.e("KanjiDAO", String.valueOf(k.getNumber()));
    }

    public Cursor selectAll() {
        ArrayList<Kanji> list = new ArrayList<>();
        try{
            Cursor c = mDb.rawQuery("SELECT * FROM kanjis ORDER BY kanji_id", new String[]{});
            return c;
        }catch(Exception e){
            Log.e("KanjiDAO", e.toString());
            return null;
        }
    }

    public Kanji select(int number){
        Log.e("Miaou", String.valueOf(number));
        Cursor c = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE kanji_id=?", new String[]{String.valueOf(number)});
        while(c.moveToNext()) {
            return new Kanji(c.getInt(1), c.getString(3), c.getString(2), c.getInt(4));
        }
        return null;
    }
}
