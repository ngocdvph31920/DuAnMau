package fpoly.ngocdvph31920.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;
import fpoly.ngocdvph31920.duanmau.dto.PhieuMuonDTO;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DBHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<PhieuMuonDTO> getdsPhieuMuon() {
        ArrayList<PhieuMuonDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.MaPM, pm.MaTV, tv.tenTV, pm.MaTT, tt.TenTT, pm.MaS, sc.TenS , pm.ngay, pm.traSach, pm.GiaThueS FROM phieumuon pm, thanhvien tv, thuthu tt, sach sc WHERE pm.MaTV = tv.MaTV AND pm.MaTT = tt.MaTT AND pm.MaS = sc.MaS order by pm.MaPM DESC", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuonDTO(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean thayDoiTrangThai(int MaPM) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("traSach", 1);
        long check = sqLiteDatabase.update("phieumuon", contentValues, "MaPM=?", new String[]{String.valueOf(MaPM)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean themPM(PhieuMuonDTO obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", obj.getMaTV());
        values.put("MaS", obj.getMaSach());
        values.put("MaTT", obj.getMaTT());
        values.put("ngay", obj.getNgay());
        values.put("traSach", obj.getTraSach());
        values.put("GiaThueS", obj.getTienThue());

        long check = sqLiteDatabase.insert("phieumuon", null, values);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

}
