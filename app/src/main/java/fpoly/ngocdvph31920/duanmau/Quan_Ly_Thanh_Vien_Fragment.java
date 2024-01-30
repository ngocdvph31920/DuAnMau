package fpoly.ngocdvph31920.duanmau;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.adapter.ThanhVienAdapter;
import fpoly.ngocdvph31920.duanmau.dao.ThanhVienDAO;
import fpoly.ngocdvph31920.duanmau.dto.ThanhVienDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Quan_Ly_Thanh_Vien_Fragment extends Fragment {
    ThanhVienDAO thanhVienDAO;
    ThanhVienDTO thanhVienDTO;
    RecyclerView rycThanhVien;
    ArrayList<ThanhVienDTO> list;
    ArrayList<ThanhVienDTO> templist;
    ThanhVienAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan__ly__thanh__vien,container,false);

        rycThanhVien = view.findViewById(R.id.rycThanhVien);
        FloatingActionButton fltAddThanhVien = view.findViewById(R.id.fltAddThanhVien);
        EditText edSearch = view.findViewById(R.id.edSearch);

        thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();
        fltAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                for (ThanhVienDTO tv: templist){
                    if (tv.getHoTen().toLowerCase().contains(s.toString().toLowerCase())){
                        list.add(tv);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_thanh_vien,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextInputEditText edt_TenThanhVien = view.findViewById(R.id.edt_TenThanhVien);
        TextInputEditText edt_namsinh = view.findViewById(R.id.edt_namsinh);
        Button btn_save_addTV = view.findViewById(R.id.btn_save_addTV);
        Button btn_cancel_addTV = view.findViewById(R.id.btn_cancel_addTV);

        btn_save_addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edt_TenThanhVien.getText().toString();
                String namsinh = edt_namsinh.getText().toString();

                boolean check = thanhVienDAO.themThanhVien(hoten,namsinh);
                if (check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel_addTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    private void loadData(){
        list = thanhVienDAO.getDataThanhVien();
        templist = thanhVienDAO.getDataThanhVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rycThanhVien.setLayoutManager(linearLayoutManager);
        adapter = new ThanhVienAdapter(getContext(), list, thanhVienDAO);
        rycThanhVien.setAdapter(adapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}