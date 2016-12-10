package fr.bloome.kanjidex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;
import static fr.bloome.kanjidex.R.id.hurigana_list_item;

public class HomeActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView mList;

    // URL to get contacts JSON
    private static String url = "http://slyldcorp.esy.es";

    ArrayList<HashMap<String, String>> kanjisList;
    ArrayList<Kanji> kanjisList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        kanjisList = new ArrayList<>();
        kanjisList2 = new ArrayList<>();
        mList = (ListView) findViewById(R.id.ListView);
        new GetKanjis().execute();

    }
    private class GetKanjis extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray kanjis = jsonObj.getJSONArray("kanjis");

                    // looping through All Contacts
                    for (int i = 0; i < kanjis.length(); i++) {
                        JSONObject c = kanjis.getJSONObject(i);

                        String hurigana = c.getString("kanji_hurigana");
                        String kanji_kanji = c.getString("kanji_kanji");
                        String number = c.getString("kanji_number");

                        // tmp hash map for single contact
                        HashMap<String, String> kanji = new HashMap<>();
                        Kanji kanji2 = new Kanji();

                        // adding each child node to HashMap key => value
                        kanji.put("kanji", kanji_kanji);
                        kanji.put("hurigana", hurigana);
                        kanji.put("number", number);
                        kanji2.setKanji(kanji_kanji);
                        kanji2.setHurigana(hurigana);
                        kanji2.setNumber(Integer.parseInt(number));

                        // adding contact to contact list
                        kanjisList.add(kanji);
                        kanjisList2.add(kanji2);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.e(TAG, String.valueOf(kanjisList2.size()));
            KanjiDAO kDAO = new KanjiDAO(getApplicationContext());
            kDAO.open();
            if(kanjisList2.size() > 0){
                kDAO.clear();
                Log.e(TAG, "\\o/");
            }
            for(Kanji k : kanjisList2){
                kDAO.ajouter(k);
                Log.i(TAG, k.toString());
            }
            Cursor cursor = kDAO.selectAll();

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    HomeActivity.this,
                    R.layout.list_item, cursor, new String[]{KanjiDAO.NUMBER, KanjiDAO.KANJI, KanjiDAO.HURIGANA}, new int[]{R.id.number_list_item,
                    R.id.kanji_list_item, R.id.hurigana_list_item});

            mList.setAdapter(adapter);
        }

    }
}
