package com.example.pjt_student;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class MainListAdapter extends ArrayAdapter<StudentVO> {
    Context context;
    ArrayList<StudentVO> datas;
    int resId;

    public MainListAdapter(Context context, int resId, ArrayList<StudentVO> datas) {
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
            convertView = inflater.inflate(resId, null);

            MainListWrapper wrapper = new MainListWrapper(convertView);
            convertView.setTag(wrapper);
        }

        MainListWrapper wrapper = (MainListWrapper) convertView.getTag();

        ImageView studentImageView = wrapper.studentImageView;
        TextView nameView = wrapper.nameView;
        final ImageView phoneView = wrapper.phoneView;

        final StudentVO vo = datas.get(position);

        nameView.setText(vo.name);

        if (vo.photo != null && !vo.photo.equals("")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            Bitmap bitmap = BitmapFactory.decodeFile(vo.photo, options);
            if (bitmap != null) {
                studentImageView.setImageBitmap(bitmap);
            }
        } else {
            studentImageView.setImageDrawable(ResourcesCompat.getDrawable(
                    context.getResources(), R.drawable.ic_student_small, null
            ));
        }

        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                );
                View dialogRoot = inflater.inflate(R.layout.dialog_student_image, null);
                ImageView dialogImageView = dialogRoot.findViewById(R.id.dialog_image);

                if (vo.photo != null && !vo.photo.equals("")) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    Bitmap bitmap = BitmapFactory.decodeFile(vo.photo, options);
                    if (bitmap != null) {
                        dialogImageView.setImageBitmap(bitmap);
                    }
                } else {
                    dialogImageView.setImageDrawable(ResourcesCompat.getDrawable(
                            context.getResources(), R.drawable.ic_student_large, null
                    ));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogRoot);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return convertView;
    }
}
