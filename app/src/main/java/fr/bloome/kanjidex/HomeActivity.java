package fr.bloome.kanjidex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mList = (ListView) findViewById(R.id.ListView);

    }

    private void getKanjis(){
        
    }
}
