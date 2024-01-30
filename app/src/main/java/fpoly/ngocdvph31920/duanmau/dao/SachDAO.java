package fpoly.ngocdvph31920.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;
import fpoly.ngocdvph31920.duanmau.dto.SachDTO;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private DBHelper dbHelper;

    public SachDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<SachDTO> getAllSachToString(){
        ArrayList<SachDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT sc.MaS, sc.TenS, sc.GiathueS, sc.MaLS, lo.TenLS FROM sach sc,loaisach lo WHERE sc.MaLS = lo.MaLS", null);
        if (c.getCount() !=0){
            c.moveToFirst();
            do {
                list.add(new SachDTO(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3), c.getString(4)));

            } while (c.moveToNext());
        }
        return list;
    }
    public List<SachDTO> getdata(String sql, String... dieukien) {
        List<SachDTO> list = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, dieukien);
        if ( cursor != null && cursor.getCount() >0){
            cursor.moveToFirst();
            do {
                list.add(new SachDTO(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public List<SachDTO> getSapxep(String dieuKien){
        String sql = ("SELECT * FROM sach order BY GiathueS "+ dieuKien);

        return getdata(sql);
    }
    public boolean themSachMoi(String TenS, int GiathueS, int MaLS){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenS", TenS);
        contentValues.put("GiathueS", GiathueS);
        contentValues.put("MaLS",MaLS);
        long check = sqLiteDatabase.insert("sach", null,contentValues);
        if (check == -1){
            return false;
        }
        return true;
    }
    public boolean capNhatSach(int MaS,String TenS, int GiathueS, int MaLS){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenS", TenS);
        contentValues.put("GiathueS", GiathueS);
        contentValues.put("MaLS",MaLS);
        long check = sqLiteDatabase.update("sach", contentValues, "MaS=?", new String[]{String.valueOf(MaS)});
        if (check == -1){
            return false;
        }
        return true;
    }
    public int xoaSach(int MaS){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM phieumuon WHERE MaS=?", new String[]{String.valueOf(MaS)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("sach", "MaS=?", new String[]{String.valueOf(MaS)});
        if (check == -1){
            return 0;
        }
        return 1;
    }
}
