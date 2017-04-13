package com.firdavsiimajidzoda.myvideplayer.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.firdavsiimajidzoda.myvideplayer.R;
import com.firdavsiimajidzoda.myvideplayer.Models.ReadRSS;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView channelTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        channelTextView = (TextView)findViewById(R.id.channel_title_text_view);
        ReadRSS readRSS = new ReadRSS(this, recyclerView);
        try {
            channelTextView.setText(readRSS.execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
