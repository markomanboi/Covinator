package com.example.covidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        Button btnPop=findViewById(R.id.btnPopUp);
        Button btnProximitySensor=findViewById(R.id.btnProximitySensor);
        Button btnSymptoms=findViewById(R.id.btnSymptoms);

        if(Build.VERSION.SDK_INT>20) {
            Explode explode = new Explode();
            getWindow().setEnterTransition(explode);

        }

        btnPop.setOnClickListener(new View.OnClickListener() {
            final Intent intent=new Intent(MainActivity.this,PhonePopUp.class);
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>20) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
            }
        });

        btnSymptoms.setOnClickListener(new View.OnClickListener() {
            final Intent intent=new Intent(MainActivity.this,Symptoms.class);
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>20) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
            }
        });

        new MyTask().execute();
    }


    private class MyTask extends AsyncTask<Void, Void, Void>  {
        String result;
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            try {
                url = new URL("https://api.apify.com/v2/key-value-stores/lFItbkoNDXKeSWBBA/records/LATEST?disableRedirect=true");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String stringBuffer;
                String string = "";
                while ((stringBuffer = bufferedReader.readLine()) != null){
                    string = String.format("%s%s", string, stringBuffer);
                }
                bufferedReader.close();
                result = string;
            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            final TextView txtData=findViewById(R.id.txtData);
            final TextView txtDataIndicator=findViewById(R.id.txtDataIndicator);
            String copy=result.toString();
            String[] values=copy.split(",");
            String[] infected=values[0].split(":");
            String[] recovered=values[2].split(":");
            String[] deceased=values[3].split(":");
            String[] activeCases=values[4].split(":");
            final String[] data={infected[1],recovered[1],deceased[1],activeCases[1]};
            final String[] dataIndicator={"Total Infected","Recovered","Deceased","Active Cases"};
            txtData.post(new Runnable() {
                int i = 0;
                @Override
                public void run() {
                    txtData.setText(data[i]);
                    txtDataIndicator.setText(dataIndicator[i]);
                    i++;
                    if (i ==4)
                        i = 0;
                    txtData.postDelayed(this, 3000);
                }
            });

        }
    }
}

