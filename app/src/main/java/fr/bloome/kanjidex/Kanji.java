package fr.bloome.kanjidex;

import java.util.ArrayList;

import static android.R.attr.lines;

/**
 * Created by louis on 10/12/2016.
 */

public class Kanji {

    private int number;
    private String kanji;
    private String hurigana;
    private int lines;
    private ArrayList<Word> words;

    public Kanji(int number, String kanji, String hurigana, int lines){
        super();
        this.number = number;
        this.kanji = kanji;
        this.hurigana = hurigana;
        this.lines = lines;
        this.words = new ArrayList<>();
    }
    public Kanji(){
        super();
    }

    public String toString(){
        return new String("Number : " + this.number + " - Kanji : " + this.kanji);
    }

    public void addWord(Word word){
        this.words.add(word);
    }
    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHurigana() {
        return hurigana;
    }

    public void setHurigana(String hurigana) {
        this.hurigana = hurigana;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

}
