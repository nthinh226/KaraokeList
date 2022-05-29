package com.thinh.nt226.karaokelist.adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.thinh.nt226.karaokelist.utils.SQLite.DATABASE_NAME;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thinh.nt226.karaokelist.R;
import com.thinh.nt226.karaokelist.model.Music;
import com.thinh.nt226.karaokelist.utils.SQLite;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    Activity context;
    int resource;
    List<Music> objects;

    public MusicAdapter(@NonNull Activity context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item, null);

        TextView txtMa, txtTen, txtCaSy;
        ImageButton btnThich, btnKhongThich;

        txtMa = (TextView) row.findViewById(R.id.txtMa);
        txtTen = (TextView) row.findViewById(R.id.txtTen);
        txtCaSy = (TextView) row.findViewById(R.id.txtCaSy);

        Music music = objects.get(position);

        txtMa.setText(music.getMa());
        txtTen.setText(music.getTen());
        txtCaSy.setText(music.getCaSy());

        btnThich = (ImageButton) row.findViewById(R.id.btnThich);
        btnKhongThich = (ImageButton) row.findViewById(R.id.btnKhongThich);

        if (music.getThich() == 1) {
            btnThich.setVisibility(View.INVISIBLE);
            btnKhongThich.setVisibility(View.VISIBLE);
        } else if(music.getThich() == 0){
            btnThich.setVisibility(View.VISIBLE);
            btnKhongThich.setVisibility(View.INVISIBLE);
        }
        btnThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đã thêm vào mục yêu thích", Toast.LENGTH_SHORT).show();
                xulyThich(true, position);
                btnThich.setVisibility(View.INVISIBLE);
                btnKhongThich.setVisibility(View.VISIBLE);
            }
        });
        btnKhongThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Đã xoá khỏi mục yêu thích", Toast.LENGTH_SHORT).show();
                xulyThich(false,position);
                btnThich.setVisibility(View.VISIBLE);
                btnKhongThich.setVisibility(View.INVISIBLE);


            }
        });

        return row;
    }

    private void xulyThich(boolean b, int position) {

        SQLite sqLite = new SQLite(context);
        sqLite.database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues row = new ContentValues();
        if(b==true){
            row.put("YEUTHICH",1);
            sqLite.database.update("ArirangSongList",row,"MABH=?", new String[] {objects.get(position).getMa()});
        }else if(b==false){
            row.put("YEUTHICH",0);
            sqLite.database.update("ArirangSongList",row,"MABH=?", new String[] {objects.get(position).getMa()});

        }
    }
}
