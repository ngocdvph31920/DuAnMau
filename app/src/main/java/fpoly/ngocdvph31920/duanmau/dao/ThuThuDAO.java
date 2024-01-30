package fpoly.ngocdvph31920.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.ngocdvph31920.duanmau.database.DBHelper;

public class ThuThuDAO {
    private DBHelper dbHelper;
    SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    public  boolean checklogin(String MaTT,String MatKhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql="SELECT * FROM thuthu WHERE MaTT=? and MatKhau=?";
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM thuthu WHERE MaTT=? AND MatKhau=?",new String[]{MaTT,MatKhau});

        if (c.getCount()!=0) {
            return true;
        }
        return false;


    }
    public int capNhatMatKhau(String username, String oldpass, String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM thuthu WHERE MaTT=? AND MatKhau=?", new String[]{username,oldpass});
        if (cursor.getCount() >0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("MatKhau",newPass);
            long check = sqLiteDatabase.update("thuthu", contentValues, "MaTT=?",new String[]{username});
            if (check == -1){
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
}
