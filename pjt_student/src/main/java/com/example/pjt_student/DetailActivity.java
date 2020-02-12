package com.example.pjt_student;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView studentImageView;
    TextView nameView;
    TextView phoneView;
    TextView emailView;
    TabHost host;

    int studentId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initData();
        initTab();
    }

    private void initData() {
        studentImageView = findViewById(R.id.detail_student_image);
        nameView = findViewById(R.id.detail_name);
        phoneView = findViewById(R.id.detail_phone);
        emailView = findViewById(R.id.detail_phone);

        OpenHelper helper = new OpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_student where _id=?",
                new String[]{String.valueOf(studentId)});

        String photo = null;

        if (cursor.moveToFirst()) {
            nameView.setText(cursor.getString(1));
            emailView.setText(cursor.getString(2));
            phoneView.setText(cursor.getString(3));

            photo = cursor.getString(4);
        }
        db.close();


    }

    private void initTab() {
        host = findViewById(R.id.host);

        // Initialize FrameLayout & TabWidget
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setIndicator("Score");
        spec.setContent(R.id.detail_score_list);
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setIndicator("Chart");
        spec.setContent(R.id.detail_score_chart);
        host.addTab(spec);

        spec = host.newTabSpec("tab3");
        spec.setIndicator("Add");
        spec.setContent(R.id.detail_score_add);
        host.addTab(spec);

        spec = host.newTabSpec("tab4");
        spec.setIndicator("Memo");
        spec.setContent(R.id.detail_memo);
        host.addTab(spec);
    }
}
