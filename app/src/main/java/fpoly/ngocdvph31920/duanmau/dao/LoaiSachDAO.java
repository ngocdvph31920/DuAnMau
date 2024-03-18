package fpoly.ngocdvph31920.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;
import fpoly.ngocdvph31920.duanmau.dto.LoaiSachDTO;

import java.util.ArrayList;

public class LoaiSachDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;

    public LoaiSachDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int InsertLoaiSach(LoaiSachDTO obj) {
        ContentValues values = new ContentValues();
        values.put("MaLS", obj.getMaLoai());
        values.put("TenLS", obj.getHoTen());
        long kq = db.insert("loaisach", null, values);
        if (kq <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int UpdateLoaiSach(LoaiSachDTO obj) {
        ContentValues values = new ContentValues();
        values.put("MaLS", obj.getMaLoai());
        values.put("TenLS", obj.getHoTen());
        String[] dk = new String[]{String.valueOf(obj.getMaLoai())};
        return db.update("loaisach", values, "MaLS=?", dk);
    }

    public int deleteLoaiSach(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM sach WHERE MaLS = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("loaisach", "MaLS = ?", new String[]{String.valueOf(id)});
        if (check == -1){
            return 0;
        }
        return 1;
    }

    public ArrayList<LoaiSachDTO> getAllLoaiSachToString() {
        ArrayList<LoaiSachDTO> ls = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM loaisach", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id_ls = c.getInt(0);
                String ten_ls = c.getString(1);

                LoaiSachDTO loaiSachDTO = new LoaiSachDTO(id_ls, ten_ls);
                ls.add(loaiSachDTO);
                c.moveToNext();
            }
        }
        return ls;
    }
}
