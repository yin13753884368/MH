package com.haxi.mh.utils.db;

import android.content.Context;

import com.haxi.mh.constant.Constant;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * 加密数据库
 * Created by Han on 2018/4/10
 * Email:yin13753884368@163.com
 * CSDN:http://blog.csdn.net/yin13753884368/article
 * Github:https://github.com/yin13753884368
 */

public class DBencrypt {
    public static DBencrypt dBencrypt;
    private Boolean isOpen = true;

    public static DBencrypt getInstences() {
        if (dBencrypt == null) {
            synchronized (DBencrypt.class) {
                if (dBencrypt == null) {
                    dBencrypt = new DBencrypt();
                }
            }
        }
        return dBencrypt;
    }

    /**
     * 如果有旧表 先加密数据库
     *
     * @param context
     * @param passphrase
     */
    public void encrypt(Context context, String passphrase) {
        File file = new File("/data/data/" + context.getPackageName() + "/databases/"+ Constant.DB_NAME);
        if (file.exists()) {
            if (isOpen) {
                try {
                    File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());

                    SQLiteDatabase db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);

                    db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                            newFile.getAbsolutePath(), passphrase));
                    db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
                    db.rawExecSQL("DETACH DATABASE encrypted;");

                    int version = db.getVersion();
                    db.close();

                    db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(),passphrase, null,SQLiteDatabase.OPEN_READWRITE);

                    db.setVersion(version);
                    db.close();
                    file.delete();
                    newFile.renameTo(file);
                    isOpen = false;
                } catch (Exception e) {
                    isOpen = false;
                }
            }
        }
    }
}
