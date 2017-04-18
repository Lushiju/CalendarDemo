package com.lushiju.calendardemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RelativeLayout full = (RelativeLayout) findViewById(R.id.rl_guest_full);
        RelativeLayout nowork = (RelativeLayout) findViewById(R.id.rl_guest_nowork);
        full.setOnClickListener(this);
        nowork.setOnClickListener(this);
    }
    String title = "";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_guest_full:
                title = getString(R.string.guest_full);
                break;
            case R.id.rl_guest_nowork:
                title = getString(R.string.no_work);
                break;

        }
        Intent intent = new Intent(this,CalendarActivity.class);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}
