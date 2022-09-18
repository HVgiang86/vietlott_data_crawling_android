package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.adapter.Max3DResultAdapter;
import com.example.vietlottdatacrawl.decoration.ItemDecoration;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;
import com.example.vietlottdatacrawl.model.SessionManager;

import java.util.List;

//This is main activity, where Max 3D result will be displayed
//This activity will be displayed data was loaded
public class MainActivity extends AppCompatActivity {
    private Max3DResultAdapter adapter;
    private ItemDecoration decoration;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private List<PrizeDrawSession> sessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSessionManagerAndItemList();
        initRecyclerViewAndAdapter();
        initItemDecorationForPinnedHeader();
        initSpinnerForMonthYearPicker();

    }

    private void initSessionManagerAndItemList() {
        sessionManager = SessionManager.getInstance();
        sessionList = sessionManager.getSessionList();
    }

    private void initRecyclerViewAndAdapter() {

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Max3DResultAdapter(sessionList,this);

        recyclerView.setAdapter(adapter);


    }

    private void initSpinnerForMonthYearPicker() {
        AppCompatSpinner spinner = findViewById(R.id.month_picker_spinner);
        String[] monthList = sessionManager.getMonthArray();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,monthList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    displayAllList();
                else
                    displayBySelectedMonth(monthList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayAllList();
            }
        });
    }

    private void initItemDecorationForPinnedHeader() {
        //Override Callback for Item Decoration
        ItemDecoration.SectionCallback callback = new ItemDecoration.SectionCallback() {
            @Override
            public boolean isSectionHeader(int pos) {
                if (pos == 0)
                    return true;
                else {
                    PrizeDrawSession session = sessionManager.getSessionByIndex(pos);
                    PrizeDrawSession preSession = sessionManager.getSessionByIndex(pos-1);
                    String monthYear = sessionManager.getMonthYearStringBySession(session);
                    String preMonthYear = sessionManager.getMonthYearStringBySession(preSession);
                    return !monthYear.equals(preMonthYear);
                }
            }

            @Override
            public String getSectionHeaderContentString(int pos) {
                PrizeDrawSession session = sessionManager.getSessionByIndex(pos);
                return sessionManager.getMonthYearStringBySession(session);
            }
        };

        //Add divider for recycler view
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //add Item decoration for pinned header
        decoration = new ItemDecoration(this,getResources().getDimensionPixelSize(R.dimen.header_height), callback);
        recyclerView.addItemDecoration(decoration);
    }

    private void displayAllList() {
        sessionList = sessionManager.getSessionList();
        recyclerView.addItemDecoration(decoration);
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }

    private void displayBySelectedMonth(String selectedMonth) {
        //get session list by selected month year
        List<PrizeDrawSession> sessionList = sessionManager.getSessionListByMonth(selectedMonth);

        //remove Pinned Header
        recyclerView.removeItemDecoration(decoration);
        recyclerView.invalidateItemDecorations();
        int count = recyclerView.getItemDecorationCount();
        while (count > 1) {
            recyclerView.removeItemDecoration(decoration);
            count = recyclerView.getItemDecorationCount();
        }

        //change dataset and notify to adapter
        adapter.setSessionList(sessionList);
        adapter.notifyDataSetChanged();
    }

}