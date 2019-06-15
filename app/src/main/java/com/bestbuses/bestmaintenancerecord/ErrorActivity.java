package com.bestbuses.bestmaintenancerecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        TextView errorTextView = findViewById(R.id.tv_error);
        String errorValue = getIntent().getStringExtra("error");
        errorTextView.setText(errorValue);
        Button homeBtn = findViewById(R.id.homeBtn);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        homeBtn.setOnClickListener(onClickListener);
    }
}
