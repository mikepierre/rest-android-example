package com.otravela.otravela;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import model.Flower;
import parsers.FlowerXMLParser;
import parsers.FlowerJSONParser;
public class SearchActivity extends AppCompatActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> task;

    List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //pb = (ProgressBar) findViewById(R.id.progressBar);
        if (isOnline()) {
            //requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
            requestData("http://services.hanselandpetal.com/feeds/flowers.json");
        } else {
            Toast.makeText(this, "Network isnt Avaiable", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);

    }

    // check if user is online.
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    protected void updateDisplay(){
       // output.append(message);
        if(flowerList != null){
            for(Flower flower : flowerList){
                output.append(flower.getName());
            }
        }
    }
    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting task");
            if (task.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            task.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            // this is doing the real work in the background.
            String content = HttpManager.getData(params[0]); // w/o autentication
            //String content = HttpManager.getDataAutenticated(params[0], "feeduser", "feedpassword"); // with autentication passing username and password.
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            // Check if autenticated
            if(result == null){
                Toast.makeText(SearchActivity.this, "Cant connect", Toast.LENGTH_LONG).show();
            }
            //flowerList = FlowerXMLParser.parseFeed(result);
            flowerList = FlowerJSONParser.parseFeed(result);
            updateDisplay();

            task.remove(this);
            if (task.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }

    }

}