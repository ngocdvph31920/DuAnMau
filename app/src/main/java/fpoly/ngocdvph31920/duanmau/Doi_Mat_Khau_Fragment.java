package fpoly.ngocdvph31920.duanmau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.ngocdvph31920.duanmau.R;

import fpoly.ngocdvph31920.duanmau.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputEditText;

public class Doi_Mat_Khau_Fragment extends Fragment {
    TextInputEditText edt_passold, edt_newpassword, edt_repassword;
    Button btn_relogin, btn_cancel;

    private ThuThuDAO thuThuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doi__mat__khau, container, false);
        edt_passold = view.findViewById(R.id.edt_passold);
        edt_newpassword = view.findViewById(R.id.edt_newpassword);
        edt_repassword = view.findViewById(R.id.edt_repassword);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_relogin = view.findViewById(R.id.btn_relogin);
        btn_relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edt_passold.getText().toString();
                String newPass = edt_newpassword.getText().toString();
                String reNewPass = edt_repassword.getText().toString();
                if (oldPass.equals("") || newPass.equals("") || reNewPass.equals("")){
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                if (newPass.equals(reNewPass)) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                    String matt = sharedPreferences.getString("MaTT", "");
                    thuThuDAO = new ThuThuDAO(getContext());
                    int check = thuThuDAO.capNhatMatKhau(matt, oldPass, newPass);
                    if (check == 1) {
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Man_Hinh_Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (check == 0) {
                        Toast.makeText(getContext(), "Mật khẩu cũ ko đúng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Nhập ko được trùng mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}