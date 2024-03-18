package fpoly.ngocdvph31920.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import fpoly.ngocdvph31920.duanmau.R;
import fpoly.ngocdvph31920.duanmau.dao.LoaiSachDAO;
import fpoly.ngocdvph31920.duanmau.dto.LoaiSachDTO;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolderLoaiSach> {
    Context context;
    List<LoaiSachDTO> list;

    public LoaiSachAdapter(Context context, List<LoaiSachDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderLoaiSach onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_loaisach, parent, false);

        ViewHolderLoaiSach viewHolderLoaiSach = new ViewHolderLoaiSach(v);
        return viewHolderLoaiSach;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.ViewHolderLoaiSach holder, int position) {
        LoaiSachDTO loaiSachDTO = list.get(position);

        holder.tvMaLoai_itemLoaiSach.setText(loaiSachDTO.getMaLoai() + "");
        holder.tvTenLoai_itemLoaiSach.setText(loaiSachDTO.getHoTen());

        holder.btnUpdate_LoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(loaiSachDTO);
            }
        });
        holder.btnDelete_LoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogDelete(loaiSachDTO);
            }
        });
    }

    public static class ViewHolderLoaiSach extends RecyclerView.ViewHolder {
        TextView tvMaLoai_itemLoaiSach, tvTenLoai_itemLoaiSach;
        Button btnUpdate_LoaiSach, btnDelete_LoaiSach;

        public ViewHolderLoaiSach(@NonNull View itemView) {
            super(itemView);

            tvMaLoai_itemLoaiSach = itemView.findViewById(R.id.tvMaLoai_itemLoaiSach);
            tvTenLoai_itemLoaiSach = itemView.findViewById(R.id.tvTenLoai_itemLoaiSach);

            btnDelete_LoaiSach = itemView.findViewById(R.id.btnDelete_LoaiSach);
            btnUpdate_LoaiSach = itemView.findViewById(R.id.btnUpdate_LoaiSach);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    void showDialogUpdate(LoaiSachDTO obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_update_loai_sach, null);

        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        EditText edtmaloaisachup = v.findViewById(R.id.edt_maloaisachup);
        EditText edttenloaisachup = v.findViewById(R.id.edt_tenloaisachup);
        Button btn_save_updatels = v.findViewById(R.id.btn_save_updatels);
        Button btn_cancel_updatels = v.findViewById(R.id.btn_cancel_updatels);

        edtmaloaisachup.setText(String.valueOf(obj.getMaLoai()));
        edttenloaisachup.setText(obj.getHoTen());

        btn_save_updatels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maloaisachup = Integer.parseInt(edtmaloaisachup.getText().toString());
                String tenloaisachup = edttenloaisachup.getText().toString();

                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                LoaiSachDTO sachDTO = new LoaiSachDTO(maloaisachup, tenloaisachup);
                sachDTO.setMaLoai(obj.getMaLoai());
                int kq = loaiSachDAO.UpdateLoaiSach(sachDTO);
                if (kq > 0) {
                    //load lại rc
                    list.clear();
                    list.addAll(loaiSachDAO.getAllLoaiSachToString());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cập nhât thàn công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel_updatels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void ShowDialogDelete(LoaiSachDTO obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("THông báo xóa");
        builder.setMessage("Bạn có đồng ý xóa lớp: " + obj.getHoTen());
        builder.setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //thực hiện xóa
                LoaiSachDAO dao = new LoaiSachDAO(context);
                int kq = dao.deleteLoaiSach(obj.getMaLoai());

                switch (kq) {
                    case 1:
                        list.clear();
                        list.addAll(dao.getAllLoaiSachToString());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case -1:
                        Toast.makeText(context, "Ko the xoa vi loai sach da co sach thuoc the loai nay", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa ko thanh cong", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.setNegativeButton("Không xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        (builder.create()).show();
    }
}
