package fr.bloome.kanjidex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by louis on 10/12/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String KANJI_KEY = "kanji_id";
    public static final String KANJI_KANJI = "kanji_kanji";
    public static final String KANJI_HURIGANA = "kanji_hurigana";
    public static final String KANJI_LINES = "kanji_lines";

    public static final String KANJI_TABLE_NAME = "kanjis";
    public static final String KANJI_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS `kanjis` (\n" +
                    " `_id` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  `kanji_id` int(11) NOT NULL,\n" +
                    "  `kanji_hurigana` varchar(5) NOT NULL,\n" +
                    "  `kanji_kanji` varchar(1)  NOT NULL,\n" +
                    "  `kanji_lines` int(11) NOT NULL\n" +
                    ")";
    public static final String KANJI_TABLE_DROP = "DROP TABLE IF EXISTS " + KANJI_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KANJI_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KANJI_TABLE_DROP);
        onCreate(db);
    }
}
