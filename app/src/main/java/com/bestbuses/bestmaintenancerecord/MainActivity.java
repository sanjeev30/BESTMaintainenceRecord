package com.bestbuses.bestmaintenancerecord;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String busNo;
    EditText etBusNo;
    String url = "http://192.168.43.156/best/getResult.php?bus_no=";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button checkStatus = (Button) findViewById(R.id.checkStatus);
        etBusNo = (EditText) findViewById(R.id.etBusNo);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busNo = etBusNo.getText().toString();
                if (busNo.length() == 4) {
                    checkStatus.setText(R.string.wait);
                    Toast.makeText(MainActivity.this, R.string.downloading_data, Toast.LENGTH_LONG).show();
                    DownloadJSONData downloadJSONData = new DownloadJSONData();
                    downloadJSONData.execute(url+busNo);
                    Log.d(TAG, "onClick: Link : "+(url+busNo));
                } else {
                    Toast.makeText(MainActivity.this,R.string.length,Toast.LENGTH_LONG).show();
                }
            }
        };
        checkStatus.setOnClickListener(onClickListener);
    }
    private class DownloadJSONData extends AsyncTask<String,Void,String> {
        private static final String TAG = "DownloadJSONData";

        @Override
        protected String doInBackground(String... strings) {
            return downloadJSON(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Checking if downloaded data is null or not.
            //If the downloaded data is null then it will not do further processing
            Intent error = new Intent(MainActivity.this, ErrorActivity.class);
            if (s == null || ("error_conn".equalsIgnoreCase(s))) {
                error.putExtra("error", "Error connecting to server");
                startActivity(error);
            } else if("no_bus".equalsIgnoreCase(s) || "no_record".equalsIgnoreCase(s)) {
                error.putExtra("error", "No such bus/record is present");
                startActivity(error);
            }
            else {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("json_data",s);
                startActivity(intent);
            }
            Button button = findViewById(R.id.checkStatus);
            button.setText(R.string.check_status);
        }

        private String downloadJSON(String url) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL urlPath = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
                Log.d(TAG, "downloadJSON: Response Code : "+httpURLConnection.getResponseCode());
                //Checking if response code is 200 i.e. Successful Internet Connection
                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine())!=null) {
                        String temp = json+"\n";
                        stringBuilder.append(temp);
                    }
                    bufferedReader.close();

                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } else {
                    return "error_conn";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
