package com.example.pjt_student;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, View.OnClickListener {

    ImageView studentImageView;
    TextView nameView;
    TextView phoneView;
    TextView emailView;
    TabHost host;

    TextView addScoreView;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnAdd, btnBack;

    int studentId;

    MyView scoreView;

    ListView listView;
    ArrayList<HashMap<String, String>> scoreList;
    SimpleAdapter adapter;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        studentId = intent.getIntExtra("id", 1);

        initData();
        initTab();
        initAddScore();
        initSpannable();
        initList();
        initWebView();
    }

    private void initWebView() {
        webView = findViewById(R.id.detail_score_chart);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new JavascriptText(), "android");

    }

    private void initList() {
        listView = findViewById(R.id.detail_score_list);
        scoreList = new ArrayList<>();

        OpenHelper helper = new OpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select score, date from tb_score " +
                        "where student_id=? order by date desc",
                        new String[]{String.valueOf(studentId)});

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("score", cursor.getString(0));
            Date d = new Date(Long.parseLong(cursor.getString(1)));
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            map.put("date", sd.format(d));
            scoreList.add(map);
        }
        db.close();

        adapter = new SimpleAdapter(
                this,
                scoreList,
                R.layout.read_list_item,
                new String[]{"score", "date"},
                new int[]{R.id.read_list_score, R.id.read_list_date}
        );

        listView.setAdapter(adapter);
    }

    private void initData() {
        studentImageView = findViewById(R.id.detail_student_image);
        nameView = findViewById(R.id.detail_name);
        phoneView = findViewById(R.id.detail_phone);
        emailView = findViewById(R.id.detail_email);
        scoreView = findViewById(R.id.detail_score);

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

            Cursor scoreCursor = db.rawQuery("select score from tb_score " +
                    "where student_id=? order by date desc limit 1",
                    new String[]{String.valueOf(studentId)});
            int score = 0;
            if (scoreCursor.moveToFirst()) {
                score = scoreCursor.getInt(0);
            }
            scoreView.setScore(score);
        }
        db.close();

        studentImageView.setOnClickListener(this);
        initStudentImage(photo);
    }

    private void initStudentImage(String path) {
        if (path != null && !path.equals("")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap != null) {
                studentImageView.setImageBitmap(bitmap);
            }
        }
    }

    private void initTab() {
        host = findViewById(R.id.host);
        host.setOnTabChangedListener(this);

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

    private void initAddScore() {
        btn0 = (Button) findViewById(R.id.key_0);
        btn1 = (Button) findViewById(R.id.key_1);
        btn2 = (Button) findViewById(R.id.key_2);
        btn3 = (Button) findViewById(R.id.key_3);
        btn4 = (Button) findViewById(R.id.key_4);
        btn5 = (Button) findViewById(R.id.key_5);
        btn6 = (Button) findViewById(R.id.key_6);
        btn7 = (Button) findViewById(R.id.key_7);
        btn8 = (Button) findViewById(R.id.key_8);
        btn9 = (Button) findViewById(R.id.key_9);
        btnBack = (Button) findViewById(R.id.key_back);
        btnAdd = (Button) findViewById(R.id.key_add);

        addScoreView = (TextView) findViewById(R.id.key_edit);

        btn0.setOnClickListener(addScoreListener);
        btn1.setOnClickListener(addScoreListener);
        btn2.setOnClickListener(addScoreListener);
        btn3.setOnClickListener(addScoreListener);
        btn4.setOnClickListener(addScoreListener);
        btn5.setOnClickListener(addScoreListener);
        btn6.setOnClickListener(addScoreListener);
        btn7.setOnClickListener(addScoreListener);
        btn8.setOnClickListener(addScoreListener);
        btn9.setOnClickListener(addScoreListener);
        btnBack.setOnClickListener(addScoreListener);
        btnAdd.setOnClickListener(addScoreListener);
    }

    View.OnClickListener addScoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnAdd) {
                String score = addScoreView.getText().toString();
                long date = System.currentTimeMillis();

                OpenHelper helper = new OpenHelper(DetailActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("insert into tb_score (student_id, date, score) values (?, ?, ?)",
                        new String[]{String.valueOf(studentId), String.valueOf(date), score});
                db.close();

                host.setCurrentTab(0);
                addScoreView.setText("0");

                scoreView.setScore(Integer.parseInt(score));

                HashMap<String, String> map = new HashMap<>();
                map.put("score", score);
                Date d = new Date(date);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                map.put("date", sd.format(d));
                scoreList.add(0, map);
                adapter.notifyDataSetChanged();
            } else if (v == btnBack) {
                String score = addScoreView.getText().toString();

                if (score.length() == 1) {
                    addScoreView.setText("0");
                } else {
                    String newScore = score.substring(0, score.length() - 1);
                    addScoreView.setText(newScore);
                }
            } else {
                Button btn = (Button) v;
                String txt = btn.getText().toString();

                String score = addScoreView.getText().toString();
                if (score.equals("0")) {
                    addScoreView.setText(txt);
                } else {
                    String newScore = score + txt;
                    int intScore = Integer.parseInt(newScore);
                    if (intScore > 100) {
                        Toast toast = Toast.makeText(DetailActivity.this, R.string.read_add_score_over_score, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        addScoreView.setText(newScore);
                    }
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSpannable() {
        TextView spanvView = findViewById(R.id.spanView);
        TextView htmlView = findViewById(R.id.htmlView);

        URLSpan urlSpan = new URLSpan("") {
            @Override
            public void onClick(View widget) {
                Toast toast = Toast.makeText(DetailActivity.this, "more click", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        String data = spanvView.getText().toString();
        Spannable spannable = (Spannable) spanvView.getText();

        int pos = data.indexOf("EXID");
        while (pos > -1) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), pos, pos +4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            pos = data.indexOf("EXID", pos + 1);
        }

        pos = data.indexOf("more");
        spannable.setSpan(urlSpan, pos, pos + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanvView.setMovementMethod(LinkMovementMethod.getInstance());

        String html = "<font color='blue'>HANI</font><br/><img src='myImage'/>";
        htmlView.setText(
                Html.fromHtml(
                        html,
                        new MyImageGetter(),
                        null
                )
        );
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("tab2")) {
            webView.loadUrl("file:///android_asset/test.html");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String[] columns = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, columns, null, null, null);
            cursor.moveToFirst();
            String path = cursor.getString(0);
            Log.d("Rybczinski", "path......" + path);
            if (path != null) {
                OpenHelper helper = new OpenHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("update tb_student set photo=? where _id=?",
                        new String[]{path, String.valueOf(studentId)});
                db.close();
                initStudentImage(path);
            }
        }
    }

    class MyImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            if (source.equals("myImage")) {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(),
                        R.drawable.hani_1, null);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
            return null;
        }
    }

    public class JavascriptText {
        @JavascriptInterface
        public String getWebData() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            if (scoreList.size() <= 10) {
                int j = 0;
                for (int i = scoreList.size(); i > 0; i--) {
                    buffer.append("[" + j + ",");
                    buffer.append(scoreList.get(i - 1).get("score"));
                    buffer.append("]");
                    if (i > 1) buffer.append(",");
                    j++;
                }
            } else {
                int j = 0;
                for (int i = 10; i > 0; i--) {
                    buffer.append("[" + j + ",");
                    buffer.append(scoreList.get(i - 1).get("score"));
                    buffer.append("]");
                    if (i > 1) buffer.append(",");
                    j++;
                }
            }
            buffer.append("]");
            Log.d("Rybczinski", buffer.toString());
            return buffer.toString();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String sendData = scoreList.get(0).get("score") + " " + scoreList.get(0).get("date");
        int id = item.getItemId();

        if (id == R.id.menu_detail_sms) {
            String phone = phoneView.getText().toString();
            if (phone != null && !phone.equals("")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + phone));
                intent.putExtra("sms_body", sendData);
                startActivity(intent);
            }
        } else if (id == R.id.menu_detail_email) {
            String email = emailView.getText().toString();
            if (email != null && !email.equals("")) {
                String url = "mailto:" + email + "?subject=score&body=" + sendData;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
