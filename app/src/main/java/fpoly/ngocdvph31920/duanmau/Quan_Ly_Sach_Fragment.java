package fpoly.ngocdvph31920.duanmau;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.R;

import fpoly.ngocdvph31920.duanmau.adapter.SachAdapter;
import fpoly.ngocdvph31920.duanmau.dao.LoaiSachDAO;
import fpoly.ngocdvph31920.duanmau.dao.SachDAO;
import fpoly.ngocdvph31920.duanmau.dto.LoaiSachDTO;
import fpoly.ngocdvph31920.duanmau.dto.SachDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class Quan_Ly_Sach_Fragment extends Fragment {
    Spinner spinner;
    SachDAO sachDAO;
    RecyclerView recyclerView;
    ArrayList<SachDTO> list;
    SachAdapter sachAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan__ly__sach, container, false);

        recyclerView = view.findViewById(R.id.rycSach);
        FloatingActionButton fltAddSach = view.findViewById(R.id.fltAddSach);
        Button btn_tangdan = view.findViewById(R.id.btn_tangdan);
        Button btn_giamdan= view.findViewById(R.id.btn_giamdan);

        btn_tangdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(sachDAO.getSapxep("asc"));
                sachAdapter.notifyDataSetChanged();
            }
        });
        btn_giamdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(sachDAO.getSapxep("desc"));
                sachAdapter.notifyDataSetChanged();
            }
        });



        sachDAO = new SachDAO(getContext());
        loadData();

        fltAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_sach,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextInputEditText edt_tensach = view.findViewById(R.id.edt_tensach);
        TextInputEditText edt_giathue = view.findViewById(R.id.edt_giathue);
        Button btn_save_adds = view.findViewById(R.id.btn_save_adds);
        Button btn_cancel_adds = view.findViewById(R.id.btn_cancel_adds);
        Spinner spLoaiSach_itemAddSach = view.findViewById(R.id.spLoaiSach_itemAddSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDsLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"TenLS"},
                new int[]{android.R.id.text1}
        );
        spLoaiSach_itemAddSach.setAdapter(simpleAdapter);
        btn_save_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edt_tensach.getText().toString();
                int tienThue = Integer.parseInt(edt_giathue.getText().toString()) ;
                HashMap<String, Object> hs = (HashMap<String, Object>) spLoaiSach_itemAddSach.getSelectedItem();
                int maLoai = (int) hs.get("MaLS");

                boolean check = sachDAO.themSachMoi(tenSach, tienThue, maLoai);
                if (check){
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Them ko thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public void loadData(){
        list = sachDAO.getAllSachToString();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        sachAdapter = new SachAdapter(getContext(), list, getDsLoaiSach(), sachDAO);
        recyclerView.setAdapter(sachAdapter);
    }
    private ArrayList<HashMap<String, Object>> getDsLoaiSach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSachDTO> list = loaiSachDAO.getAllLoaiSachToString();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSachDTO loaiSachDTO : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaLS", loaiSachDTO.getMaLoai());
            hs.put("TenLS", loaiSachDTO.getHoTen());
            listHM.add(hs);
        }
        return listHM;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}