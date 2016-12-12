package fr.bloome.kanjidex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView pKanji;
    private TextView pNumber;
    private TextView pHurigana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pKanji = (TextView) findViewById(R.id.kanjiTextView);
        pHurigana = (TextView) findViewById(R.id.huriganaTextView);
        pNumber = (TextView) findViewById(R.id.numberTextView);

        Intent i = getIntent();
        int number = i.getIntExtra(HomeActivity.NUMBER, 1);
        Log.e("OUAF", String.valueOf(number));
        KanjiDAO kDAO = new KanjiDAO(getApplicationContext());
        kDAO.open();
        Kanji kanji = kDAO.select(number);
        kDAO.close();
        pKanji.setText(kanji.getKanji());
        if(kanji.getNumber() < 10){
            pNumber.setText("N°\n0\n0\n" + kanji.getNumber());
        }else if(kanji.getNumber() < 100){
            pNumber.setText("N°\n0\n" + kanji.getNumber() / 10 + "\n" + kanji.getNumber() % 10);
        }else{
            pNumber.setText("N°\n" + kanji.getNumber() / 100 + "\n" + kanji.getNumber() / 10 % 10 + "\n" + kanji.getNumber() % 10);
        }
        String huriganaTmp = new String();
        for(char c : kanji.getHurigana().toCharArray()){
            huriganaTmp += c + "\n";
        }
        pHurigana.setText(huriganaTmp);
        /*WordDAO wDAO = new WordDAO(getApplicationContext());
        wDAO.open();
        ArrayList<Word> words = wDAO.getWordWithGrade(kanji.getNumber(), 1);
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.gradeOne);
        for(Word w : words){
            LinearLayout lTmp = (LinearLayout) findViewById(R.id.words_layout);
            TextView kanjis = (TextView) findViewById(R.id.words_kanjis);
            kanjis.setText(w.getKanjis());
            lLayout.addView(lTmp);
        }*/
    }

}
