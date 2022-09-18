package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;
import com.example.vietlottdatacrawl.decoration.ItemDecoration;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Max3DResultAdapter adapter;
    private ItemDecoration decoration;
    private RecyclerView recyclerView;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = SessionManager.getInstance();
        List<PrizeDrawSession> sessionList = manager.getSessionList();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Max3DResultAdapter(sessionList,this);

        recyclerView.setAdapter(adapter);

        ItemDecoration.SectionCallback callback = new ItemDecoration.SectionCallback() {
            @Override
            public boolean isSectionHeader(int pos) {
                if (pos == 0)
                    return true;
                else {
                    PrizeDrawSession session = manager.getSessionByIndex(pos);
                    PrizeDrawSession preSession = manager.getSessionByIndex(pos-1);
                    String monthYear = manager.getMonthYearStringBySession(session);
                    String preMonthYear = manager.getMonthYearStringBySession(preSession);
                    return !monthYear.equals(preMonthYear);
                }
            }

            @Override
            public String getSectionHeaderContentString(int pos) {
                PrizeDrawSession session = manager.getSessionByIndex(pos);
                return manager.getMonthYearStringBySession(session);
            }
        };

        decoration = new ItemDecoration(this,getResources().getDimensionPixelSize(R.dimen.header_height),true,callback);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        AppCompatSpinner spinner = findViewById(R.id.month_picker_spinner);
        String[] monthList = manager.getMonthArray();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,monthList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    displayAllList();
                }

                else {
                    displayBySelectedMonth(monthList[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                decoration.setSticky(true);
                displayAllList();
            }
        });

    }

    private void displayAllList() {
        List<PrizeDrawSession> sessionList = manager.getSessionList();
        recyclerView.addItemDecoration(decoration);
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }

    private void displayBySelectedMonth(String selectedMonth) {
        List<PrizeDrawSession> sessionList = manager.getSessionListByMonth(selectedMonth);
        recyclerView.removeItemDecoration(decoration);
        recyclerView.invalidateItemDecorations();
        int count = recyclerView.getItemDecorationCount();
        while (count > 1) {
            recyclerView.removeItemDecoration(decoration);
            count = recyclerView.getItemDecorationCount();
        }
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }


}