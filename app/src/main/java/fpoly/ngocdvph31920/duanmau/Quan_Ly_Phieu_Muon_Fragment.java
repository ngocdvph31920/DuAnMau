package fpoly.ngocdvph31920.duanmau;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import fpoly.ngocdvph31920.duanmau.R;

import fpoly.ngocdvph31920.duanmau.adapter.PhieuMuonAdapter;
import fpoly.ngocdvph31920.duanmau.dao.PhieuMuonDAO;
import fpoly.ngocdvph31920.duanmau.dao.SachDAO;
import fpoly.ngocdvph31920.duanmau.dao.ThanhVienDAO;
import fpoly.ngocdvph31920.duanmau.dto.PhieuMuonDTO;
import fpoly.ngocdvph31920.duanmau.dto.SachDTO;
import fpoly.ngocdvph31920.duanmau.dto.ThanhVienDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Quan_Ly_Phieu_Muon_Fragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerViewPM;
    ArrayList<PhieuMuonDTO> list;
    ArrayList<PhieuMuonDTO> tempList;
    PhieuMuonAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan__ly__phieu__muon,container,false);
        recyclerViewPM = view.findViewById(R.id.rycPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id.fltAddPhieuMuon);
        EditText edSearch = view.findViewById(R.id.edSearch);
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
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

                for (PhieuMuonDTO pm: tempList){
                    if (String.valueOf(pm.getMaPM()).contains(s.toString())){
                        list.add(pm);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_phieu_muon,null);
        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // lấy mã tv
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("MaTV");
                Toast.makeText(getActivity(), matv+"abc", Toast.LENGTH_SHORT).show();
                // lay ma sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("MaS");

                int tien = Integer.parseInt(edtTien.getText().toString());

                themPhieuMuon(matv,masach,tien);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void themPhieuMuon(int matv, int masach, int tien){
        phieuMuonDAO = new PhieuMuonDAO(getContext());

        // lấy mã thủ thư
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("MaTT", "");

        //Lấy ngày hiện tại
        Date curentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(curentTime);

        //tạo ph
        PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO(matt,matv,masach,ngay,0,tien);
        boolean kiemtra = phieuMuonDAO.themPM(phieuMuonDTO);
        if (kiemtra){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();

        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại" +  matv, Toast.LENGTH_SHORT).show();
        }
    }
    private void loadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getdsPhieuMuon();
        tempList = phieuMuonDAO.getdsPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPM.setLayoutManager(linearLayoutManager);
        adapter = new PhieuMuonAdapter(list, getContext());
        recyclerViewPM.setAdapter(adapter);
    }
    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVienDTO> list = thanhVienDAO.getDataThanhVien();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (ThanhVienDTO tv: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaTV",tv.getMaTV());
            hs.put("tenTV",tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenTV"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);

    }
    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<SachDTO> list = sachDAO.getAllSachToString();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (SachDTO sc: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaS",sc.getMaSach());
            hs.put("TenS",sc.getTenSach());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TenS"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);

    }
}