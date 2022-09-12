package com.example.vietlottdatacrawl.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.asynctask.ParseUrl;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText urlInput = findViewById(R.id.url_input_edt);
        Button button = findViewById(R.id.crawl_button);
        resultTextView = findViewById(R.id.result_text_view);

        button.setOnClickListener((View v) -> {
            String url = urlInput.getText().toString().trim();
            crawlData(url);
        });

    }

    public void crawlData(String url) {
        String[] s = new String[1];
        s[0] = url;
        ParseUrl parseUrl = new ParseUrl(this,resultTextView);
        parseUrl.execute(s);
    }
}