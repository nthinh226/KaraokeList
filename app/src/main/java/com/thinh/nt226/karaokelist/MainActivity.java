package com.thinh.nt226.karaokelist;

import static com.thinh.nt226.karaokelist.utils.SQLite.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.thinh.nt226.karaokelist.adapter.MusicAdapter;
import com.thinh.nt226.karaokelist.model.Music;
import com.thinh.nt226.karaokelist.utils.SQLite;

import java.io.File;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    EditText txtTimBaiHat;
    ListView lvMusic, lvMusicFavorite;
    ArrayList<Music> dsMusic;
    MusicAdapter musicAdapter;
    ArrayList<Music> dsMusicFavorite;
    MusicAdapter musicFavoritAdapter;
    SQLite sqLite = new SQLite(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xuLySaoChepCSDLTuAssetsVaoHeThongMobile();

        addControls();
        addEvents();

    }

    private void xuLySaoChepCSDLTuAssetsVaoHeThongMobile() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                sqLite.CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("1")) {
                    xyLyHienThiBaiHat();
                } else if (tabId.equalsIgnoreCase("2")) {
                    xuLyHienThiBaiHatYeuThich();
                }
            }
        });
        txtTimBaiHat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = txtTimBaiHat.getText().toString();
                xyLyTimBaiHat(search);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void xyLyTimBaiHat(String search) {
        sqLite.database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = sqLite.database.rawQuery("SELECT * FROM ArirangSongList where TENBH LIKE '%" + search + "%'", null);
        dsMusic.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String caSy = cursor.getString(3);
            int thich = cursor.getInt(5);
            dsMusic.add(new Music(ma, ten, caSy, thich));
        }
        cursor.close();
        musicAdapter.notifyDataSetChanged();
    }

    private void xuLyHienThiBaiHatYeuThich() {
        sqLite.database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = sqLite.database.rawQuery("Select * from ArirangSongList where YEUTHICH=1", null);
        dsMusicFavorite.clear();
        while (cursor.moveToNext()) {

            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String caSy = cursor.getString(3);
            int thich = cursor.getInt(5);

            Music music = new Music(ma, ten, caSy, thich);
            dsMusicFavorite.add(music);
        }
        cursor.close();
        musicFavoritAdapter.notifyDataSetChanged();

    }

    private void xyLyHienThiBaiHat() {
    }

    private void xuLyCSDL() {
        sqLite.database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursor = sqLite.database.query("ArirangSongList", null, null, null, null, null, null);
        dsMusic.clear();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String caSy = cursor.getString(3);
            int thich = cursor.getInt(5);
            dsMusic.add(new Music(ma, ten, caSy, thich));
        }
        cursor.close();
        musicAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        txtTimBaiHat = (EditText) findViewById(R.id.txtTimBaiHat);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.music));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("2");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.musicfavorite));
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        lvMusic = (ListView) findViewById(R.id.lvMusic);
        dsMusic = new ArrayList<>();
        musicAdapter = new MusicAdapter(
                MainActivity.this,
                R.layout.item,
                dsMusic
        );
        lvMusic.setAdapter(musicAdapter);

        lvMusicFavorite = (ListView) findViewById(R.id.lvMusicFavorite);
        dsMusicFavorite = new ArrayList<>();
        musicFavoritAdapter = new MusicAdapter(
                MainActivity.this,
                R.layout.item,
                dsMusicFavorite
        );
        lvMusicFavorite.setAdapter(musicFavoritAdapter);
        xuLyCSDL();
    }
}