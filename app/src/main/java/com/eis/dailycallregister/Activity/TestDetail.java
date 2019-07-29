package com.eis.dailycallregister.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.eis.dailycallregister.R;

public class TestDetail extends AppCompatActivity {
    TextView tname,sdate,edate,time,quest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_detail);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#00E0C6'>TEST DETAIL</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);

        tname=findViewById(R.id.tname);
        sdate=findViewById(R.id.sdate);
        edate=findViewById(R.id.edate);
        time=findViewById(R.id.time);
        quest=findViewById(R.id.quest);

        tname.setText(getIntent().getStringExtra("tname"));
        sdate.setText(getIntent().getStringExtra("sdate"));
        edate.setText(getIntent().getStringExtra("edate"));
        time.setText(getIntent().getStringExtra("time"));
        quest.setText(getIntent().getStringExtra("quest"));



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        TestDetail.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
