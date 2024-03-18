package fpoly.ngocdvph31920.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;
import fpoly.ngocdvph31920.duanmau.dto.ThanhVienDTO;

import java.util.ArrayList;

public class ThanhVienDAO {
    DBHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    public ArrayList<ThanhVienDTO> getDataThanhVien() {
        ArrayList<ThanhVienDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thanhvien", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVienDTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themThanhVien(String tenTV, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTV", tenTV);
        contentValues.put("namsinh", namsinh);
        long check = sqLiteDatabase.insert("thanhvien", null,contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }
    public boolean capNhatThongTinTV(int MaTV, String tenTV, String namSinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTV",tenTV);
        contentValues.put("namSinh",namSinh);
        long check = sqLiteDatabase.update("thanhvien", contentValues, "MaTV = ?", new String[]{String.valueOf(MaTV)});
        if (check == -1){
            return false;
        } else {
            return true;
        }
    }
    public int xoaThanhVien(int MaTV){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM phieumuon WHERE MaTV = ?", new String[]{String.valueOf(MaTV)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("thanhvien", "MaTV=?", new String[]{String.valueOf(MaTV)});
        if (check == -1){
            return 0;
        }
        return 1;
    }
}
