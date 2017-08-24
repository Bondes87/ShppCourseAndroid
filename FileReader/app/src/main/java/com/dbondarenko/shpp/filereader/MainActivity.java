package com.dbondarenko.shpp.filereader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewFileContents;
    private Button buttonReadFile;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setContentView(R.layout.activity_main);
    }

    private void initViews() {
        setContentView(R.layout.activity_main);
        buttonReadFile = (Button) findViewById(R.id.buttonReadFile);
        buttonReadFile.setOnClickListener(this);
        textViewFileContents = (TextView) findViewById(R.id.textViewFileContents);
    }
}
