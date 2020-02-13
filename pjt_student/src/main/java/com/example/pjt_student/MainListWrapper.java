package com.example.pjt_student;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListWrapper {

    ImageView studentImageView;
    TextView nameView;
    ImageView phoneView;

    public MainListWrapper(View root) {
        studentImageView = root.findViewById(R.id.main_item_student_image);
        nameView = root.findViewById(R.id.main_item_name);
        phoneView = root.findViewById(R.id.main_item_contact);
    }

}
