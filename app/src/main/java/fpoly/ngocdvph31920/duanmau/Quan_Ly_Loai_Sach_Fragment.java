package fpoly.ngocdvph31920.duanmau;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.R;

import fpoly.ngocdvph31920.duanmau.adapter.LoaiSachAdapter;
import fpoly.ngocdvph31920.duanmau.dao.LoaiSachDAO;
import fpoly.ngocdvph31920.duanmau.dto.LoaiSachDTO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Quan_Ly_Loai_Sach_Fragment extends Fragment {
    RecyclerView rycLoaiSach;
    LoaiSachAdapter loaiSachAdapter;
    LoaiSachDAO loaiSachDAO;
    List<LoaiSachDTO> listloaisach;
    FloatingActionButton fltAddLoaiSach;
    LoaiSachDAO sachDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan__ly__loai__sach, container, false);
        rycLoaiSach = view.findViewById(R.id.rycLoaiSach);
        fltAddLoaiSach = view.findViewById(R.id.fltAddLoaiSach);


        loaiSachDAO = new LoaiSachDAO(getActivity());

        listloaisach = loaiSachDAO.getAllLoaiSachToString();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        loaiSachAdapter = new LoaiSachAdapter(getActivity(), listloaisach);
        if(listloaisach == null){
            Toast.makeText(requireContext(), listloaisach.get(1).getMaLoai() + "", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), listloaisach.get(1).getMaLoai() + "", Toast.LENGTH_SHORT).show();
        }
        rycLoaiSach.setLayoutManager(layoutManager);
        rycLoaiSach.setAdapter(loaiSachAdapter);
        loaiSachAdapter.notifyDataSetChanged();

        fltAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
        return view;
    }

    void openDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_loai_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edt_maloaisach = view.findViewById(R.id.edt_maloaisach);
        EditText edt_tenloaisach = view.findViewById(R.id.edt_tenloaisach);
        Button btn_save_addls = view.findViewById(R.id.btn_save_addls);
        Button btn_cancel_addls = view.findViewById(R.id.btn_cancel_addls);

        btn_save_addls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maLS = Integer.parseInt(edt_maloaisach.getText().toString().trim());
                String tenLS = edt_tenloaisach.getText().toString();

                loaiSachDAO = new LoaiSachDAO(getActivity());
                LoaiSachDTO loaiSachDTO = new LoaiSachDTO(maLS, tenLS);

                int id = loaiSachDAO.InsertLoaiSach(loaiSachDTO);
                if (id > 0) {
                    //load lại rc
                    refereshList();
                    Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), maLS + tenLS, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Lỗi thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel_addls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void refereshList() {
        listloaisach.clear();
        loaiSachDAO = new LoaiSachDAO(requireContext());
        listloaisach.addAll(loaiSachDAO.getAllLoaiSachToString());
        loaiSachAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}