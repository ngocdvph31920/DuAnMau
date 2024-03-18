package fpoly.ngocdvph31920.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.R;
import fpoly.ngocdvph31920.duanmau.dao.SachDAO;
import fpoly.ngocdvph31920.duanmau.dto.SachDTO;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<SachDTO> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<SachDTO> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO)  {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaSach_itemSach.setText(list.get(position).getMaSach() + "");
        holder.tvTenSach_itemSach.setText(list.get(position).getTenSach());
        holder.tvGiaThue_itemSach.setText(list.get(position).getGiaThue() + "");
        holder.tvMaLoai_itemSach.setText(list.get(position).getMaLoai() + "");
        holder.tvTenLoai_itemSach.setText(list.get(position).getTenLoai());

        holder.btnUpdate_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.btnDelete_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMaSach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa ko thanh cong", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Xoa ko dc vi sach nay co trong phieu muon", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void showDialog(SachDTO sachDTO){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_sach,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextInputEditText edt_tensach = view.findViewById(R.id.edt_tensach);
        TextInputEditText edt_giathue = view.findViewById(R.id.edt_giathue);
        TextView txtMaS = view.findViewById(R.id.txtMaS);
        Spinner spLoaiSach_itemAddSach = view.findViewById(R.id.spLoaiSach_itemAddSach);
        Button btn_save_updates = view.findViewById(R.id.btn_save_updates);
        Button btn_cancel_updates = view.findViewById(R.id.btn_cancel_updates);

        txtMaS.setText("Mã sách: "+ sachDTO.getMaSach());
        edt_tensach.setText(sachDTO.getTenSach());
        edt_giathue.setText(sachDTO.getGiaThue()+ "");

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TenLS"},
                new int[]{android.R.id.text1}
        );
        spLoaiSach_itemAddSach.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;

        for (HashMap<String, Object> item: listHM){
            if ((int)item.get("MaLS") == sachDTO.getMaLoai()){
                position = index;
            }
            index++;
        }
        spLoaiSach_itemAddSach.setSelection(position);

        btn_save_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edt_tensach.getText().toString();
                int tienThue = Integer.parseInt(edt_giathue.getText().toString()) ;
                HashMap<String, Object> hs = (HashMap<String, Object>) spLoaiSach_itemAddSach.getSelectedItem();
                int maLoai = (int) hs.get("MaLS");

                boolean check = sachDAO.capNhatSach(sachDTO.getMaSach(), tenSach, tienThue, maLoai);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void loadData(){
        list.clear();
        list = sachDAO.getAllSachToString();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaSach_itemSach,tvTenSach_itemSach,tvGiaThue_itemSach,tvMaLoai_itemSach,tvTenLoai_itemSach;
        Button btnDelete_Sach,btnUpdate_sach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaSach_itemSach = itemView.findViewById(R.id.tvMaSach_itemSach);
            tvTenSach_itemSach = itemView.findViewById(R.id.tvTenSach_itemSach);
            tvGiaThue_itemSach = itemView.findViewById(R.id.tvGiaThue_itemSach);
            tvMaLoai_itemSach = itemView.findViewById(R.id.tvMaLoai_itemSach);
            tvTenLoai_itemSach = itemView.findViewById(R.id.tvTenLoai_itemSach);
            btnDelete_Sach = itemView.findViewById(R.id.btnDelete_Sach);
            btnUpdate_sach = itemView.findViewById(R.id.btnUpdate_sach);
        }
    }
}
