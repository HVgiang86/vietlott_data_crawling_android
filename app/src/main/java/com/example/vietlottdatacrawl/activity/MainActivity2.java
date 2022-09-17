package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private Max3DResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SessionManager manager = SessionManager.getInstance();
        List<PrizeDrawSession> sessionList = manager.getSessionList();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new Max3DResultAdapter(sessionList,this);
        recyclerView.setAdapter(adapter);


        AppCompatSpinner spinner = findViewById(R.id.month_picker_spinner);
        String[] monthList = manager.getMonthArray();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,monthList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayBySelectedMonth(monthList[position],manager);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayAllList(manager);
            }
        });

    }

    private void displayAllList(SessionManager manager) {
        List<PrizeDrawSession> sessionList = manager.getSessionList();
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }

    private void displayBySelectedMonth(String selectedMonth, SessionManager manager) {
        List<PrizeDrawSession> sessionList = manager.getSessionListByMonth(selectedMonth);
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }


}