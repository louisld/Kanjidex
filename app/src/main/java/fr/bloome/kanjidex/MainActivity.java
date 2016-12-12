package fr.bloome.kanjidex;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

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
        WordDAO wDAO = new WordDAO(getApplicationContext());
        wDAO.open();
        ArrayList<Word> words = wDAO.getWordWithGrade(kanji.getNumber(), 1);
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.gradeOne);
        for(Word w : words){
            LinearLayout lGlob = new LinearLayout(this);
            LinearLayout lTmp = new LinearLayout(this);
            lGlob.setOrientation(LinearLayout.HORIZONTAL);
            lTmp.setOrientation(LinearLayout.VERTICAL);
            lGlob.setMinimumHeight(51);

            TextView huriganas = new TextView(this);
            TextView kanjis = new TextView(this);
            TextView traduction = new TextView(this);

            kanjis.setText(w.getKanjis());
            kanjis.setTextSize(30.0f);
            kanjis.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            huriganas.setText(w.getKanji_hurigana());
            huriganas.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            traduction.setText(w.getTraduction());
            traduction.setTextSize(24.0f);
            traduction.setGravity(Gravity.CENTER_HORIZONTAL);
            traduction.setGravity(Gravity.CENTER_VERTICAL);

            lTmp.addView(huriganas);
            lTmp.addView(kanjis);
            lTmp.setGravity(Gravity.CENTER_VERTICAL);
            lTmp.setPaddingRelative(10, 5, 5, 5);
            lGlob.addView(lTmp);
            lGlob.addView(traduction);
            lLayout.addView(lGlob);
        }
    }

}
