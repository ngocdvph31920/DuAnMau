package fpoly.ngocdvph31920.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "PHUONGNAMLIBRARY", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String bangthanhvien = "CREATE TABLE thanhvien(" +
                "MaTV integer primary key autoincrement," +
                "tenTV text NOT NULL," +
                "namSinh text NOT NULL)";
        db.execSQL(bangthanhvien);

        String bangloaisach = "CREATE TABLE loaisach(" +
                "MaLS integer primary key autoincrement," +
                "TenLS text NOT NULL)";
        db.execSQL(bangloaisach);

        String bangsach = "CREATE TABLE sach(" +
                "MaS integer primary key autoincrement," +
                "TenS text UNIQUE NOT NULL," +
                "GiathueS integer NOT NULL," +
                "MaLS integer NOT NULL REFERENCES loaisach(MaLS))";
        db.execSQL(bangsach);

        String bangthuthu = "CREATE TABLE thuthu(" +
                "MaTT text primary key," +
                "TenTT text NOT NULL ," +
                "MatKhau text NOT NULL )";
        db.execSQL(bangthuthu);

        String bangphieumuon = "CREATE TABLE phieumuon(" +
                "MaPM integer primary key autoincrement," +
                "MaTV integer NOT NULL REFERENCES thanhvien(MaTV)," +
                "MaS integer NOT NULL REFERENCES sach(MaS)," +
                "MaTT text NOT NULL REFERENCES thuthu(MaTT)," +
                "ngay text NOT NULL ,"+
                "traSach integer NOT NULL ,"+
                "GiathueS integer NOT NULL )";
        db.execSQL(bangphieumuon);

        db.execSQL("INSERT INTO loaisach VALUES (1,'Thiếu nhi'),(2, 'Khoa học'), (3,'Tình cảm')");
        db.execSQL("INSERT INTO sach VALUES (1,'Con vịt con',2500,1),(2,'Chú hề daocon',30000,3),(3,'Hai khẩu súng',2000,2)");
        db.execSQL("INSERT INTO thanhvien VALUES (1,'Đàm Viết Ngọc','2004'),(2,'Nguyễn Văn Cường','2001')");
        db.execSQL("INSERT INTO phieumuon VALUES (1,1,1,'admin','12/04/2023',1,20000),(2,2,2,'admin1','15/04/2023',0,25000)");
        String inseachAdmin = "INSERT INTO thuthu VALUES ('admin','vietngoc','vietngoc'),('admin1','vietngoc1','vietngoc1')";
        db.execSQL(inseachAdmin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS thuthu");
            db.execSQL("DROP TABLE IF EXISTS thanhvien");
            db.execSQL("DROP TABLE IF EXISTS loaisach");
            db.execSQL("DROP TABLE IF EXISTS sach");
            db.execSQL("DROP TABLE IF EXISTS phieumuon");
            onCreate(db);
        }
    }
}
