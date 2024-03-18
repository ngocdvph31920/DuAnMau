package fpoly.ngocdvph31920.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import fpoly.ngocdvph31920.duanmau.R;

public class Man_Hinh_Cho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Man_Hinh_Cho.this,Man_Hinh_Login.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}