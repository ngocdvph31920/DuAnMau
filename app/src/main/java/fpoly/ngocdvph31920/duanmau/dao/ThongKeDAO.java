package fpoly.ngocdvph31920.duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;
import fpoly.ngocdvph31920.duanmau.dto.SachDTO;

import java.util.ArrayList;

public class ThongKeDAO {
    DBHelper dbHelper;
    public ThongKeDAO(Context context){
        dbHelper = new DBHelper(context);
    }
    public ArrayList<SachDTO> getTop10(){
        ArrayList<SachDTO>list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.MaS, sc.TenS, COUNT(pm.MaS) FROM phieumuon pm, sach sc WHERE pm.MaS = sc.MaS GROUP BY pm.MaS, sc.TenS ORDER BY COUNT(pm.MaS) DESC LIMIT 10", null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
              list.add(new SachDTO(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/","");
        ngayketthuc = ngayketthuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(GiathueS) FROM phieumuon WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) between ? and ?", new String[]{ngaybatdau,ngayketthuc});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
