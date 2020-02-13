package com.example.pjt_student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        return convertView;
    }
}
