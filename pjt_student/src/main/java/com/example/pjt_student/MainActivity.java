package com.example.pjt_student;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button testBtn;
    ImageView addBtn;

    double initTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBtn = findViewById(R.id.main_test_btn);
        addBtn = findViewById(R.id.main_btn);

        testBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addBtn) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }else if (v == testBtn) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast toast = Toast.makeText(this, R.string.main_back_end, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                finish();
            }
            initTime = System.currentTimeMillis();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
