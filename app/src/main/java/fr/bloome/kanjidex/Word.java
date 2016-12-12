package fr.bloome.kanjidex;

/**
 * Created by louis on 11/12/2016.
 */

public class Word {
    private int kanji_number;
    private String kanjis;
    private String kanji_hurigana;
    private String traduction;
    private int grade;

    public Word(int kanji_number, String kanjis, String kanji_hurigana, String traduction, int grade){
        this.kanji_number = kanji_number;
        this.kanjis = kanjis;
        this.kanji_hurigana = kanji_hurigana;
        this.traduction = traduction;
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getKanji_number() {
        return kanji_number;
    }

    public void setKanji_number(int kanji_number) {
        this.kanji_number = kanji_number;
    }

    public String getKanjis() {
        return kanjis;
    }

    public void setKanjis(String kanjis) {
        this.kanjis = kanjis;
    }

    public String getKanji_hurigana() {
        return kanji_hurigana;
    }

    public void setKanji_hurigana(String kanji_hurigana) {
        this.kanji_hurigana = kanji_hurigana;
    }

    public String getTraduction() {
        return traduction;
    }

    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }
}