package com.thinh.nt226.karaokelist.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLite {
    private Context context;
    public static final String DATABASE_NAME = "Arirang.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public SQLiteDatabase database = null;

    public SQLite(Context context) {
        this.context = context;
    }

    public void xuLySaoChepCSDLTuAssetsVaoHeThongMobile() {
        File dbFile = this.context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this.context, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this.context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = layDuongDanLuuTru();

            // if the path doesn't exist first, create it
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (Exception ex) {
            Log.e("LOI_SAO_CHEP", ex.toString());
        }
    }

    public String layDuongDanLuuTru() {
        //tra ve data/data/APP_NAME/databases/FILENAME
        return this.context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}
