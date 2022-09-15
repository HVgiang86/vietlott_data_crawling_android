package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Max3DResultAdapter adapter = new Max3DResultAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
    }
}