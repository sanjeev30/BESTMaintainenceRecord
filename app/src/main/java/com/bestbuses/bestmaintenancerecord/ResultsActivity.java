package com.bestbuses.bestmaintenancerecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> headings = new ArrayList<String>();
    List<RecordItems> temp;
    HashMap<String,List<RecordItems>> childItems = new HashMap<>();

    private static final String TAG = "ResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_results);
        expandableListView = findViewById(R.id.expandable_listview);
        TextView busNoValue = (TextView) findViewById(R.id.busNoValue);
        TextView rtoNoValue = (TextView) findViewById(R.id.rtoNoValue);
        String JSONResult = getIntent().getStringExtra("json_data");
        try {
            JSONObject jsonObject = new JSONObject(JSONResult);

            JSONObject server_response = jsonObject.getJSONObject("server_response");
            JSONObject busInfo = server_response.getJSONObject("bus_info");
            String busNo,rtoNo;
            busNo = busInfo.getString("busNo");
            rtoNo = busInfo.getString("busRTONo");
            busNoValue.setText(busNo);
            rtoNoValue.setText(rtoNo);
            String unitName;
            JSONArray unitInfo = server_response.getJSONArray("unit_info");
            int count = 0;
            while (count<unitInfo.length()) {
                JSONObject unit = unitInfo.getJSONObject(count);
                unitName = unit.getString("unit_name");
                headings.add(unitName);
                JSONArray unitChangeRec = unit.getJSONArray("unit_change_rec");
                    int records = 0;
                    temp = new ArrayList<>();
                    RecordItems listHeader = new RecordItems("Date Fitted", "Date Removed", "Kilometres");
                    temp.add(listHeader);
                    while (records < unitChangeRec.length()) {
                        JSONObject record = unitChangeRec.getJSONObject(records);
                        String dateFitted = record.getString("dateFitted");
                        String dateRemoved = record.getString("dateRemoved");
                        String kms = record.getString("kms");
                        RecordItems recordItems = new RecordItems(dateFitted, dateRemoved, kms);
                        temp.add(recordItems);
                        records++;
                    }
                    childItems.put(unitName, temp);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ExpListAdapter expListAdapter = new ExpListAdapter(ResultsActivity.this, headings,childItems);
        expandableListView.setAdapter(expListAdapter);
    }
}
