package fpoly.ngocdvph31920.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.R;
import fpoly.ngocdvph31920.duanmau.dao.ThanhVienDAO;
import fpoly.ngocdvph31920.duanmau.dto.ThanhVienDTO;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVienDTO> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVienDTO> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaTV_itemThanhVien.setText(String.valueOf(list.get(position).getMaTV()));
        holder.tvHoTen_itemThanhVien.setText(list.get(position).getHoTen());
        holder.tvNamSinh_itemThanhVien.setText(list.get(position).getNamSinh());

        holder.btnUpdate_ThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhVienDTO thanhVienDTO = list.get(holder.getAdapterPosition());
                showDialogUpdate(thanhVienDTO);
            }
        });
        holder.btnDelete_ThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa thành viên này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = thanhVienDAO.xoaThanhVien(list.get(holder.getAdapterPosition()).getMaTV());
                        switch (check){
                            case 1:
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                                loadData();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Thành viên tồn tại phiếu mượn, không được xóa", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaTV_itemThanhVien,tvHoTen_itemThanhVien,tvNamSinh_itemThanhVien;
        Button btnDelete_ThanhVien, btnUpdate_ThanhVien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV_itemThanhVien = itemView.findViewById(R.id.tvMaTV_itemThanhVien);
            tvHoTen_itemThanhVien = itemView.findViewById(R.id.tvHoTen_itemThanhVien);
            tvNamSinh_itemThanhVien = itemView.findViewById(R.id.tvNamSinh_itemThanhVien);
            btnDelete_ThanhVien = itemView.findViewById(R.id.btnDelete_ThanhVien);
            btnUpdate_ThanhVien = itemView.findViewById(R.id.btnUpdate_ThanhVien);
        }
    }
    public void showDialogUpdate(ThanhVienDTO thanhVienDTO){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_thanhvien,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView txtMaTV = view.findViewById(R.id.txtMaTV);
        TextInputEditText edtTenTV_itemUpThanhVien = view.findViewById(R.id.edtTenTV_itemUpThanhVien);
        TextInputEditText edtNamSinh_itemUpThanhVien = view.findViewById(R.id.edtNamSinh_itemUpThanhVien);
        Button btnSave_itemUpThanhVien = view.findViewById(R.id.btnSave_itemUpThanhVien);
        Button btnHuy_itemUpThanhVien = view.findViewById(R.id.btnHuy_itemUpThanhVien);

        txtMaTV.setText(thanhVienDTO.getMaTV()+"");
        edtTenTV_itemUpThanhVien.setText((thanhVienDTO.getHoTen()));
        edtNamSinh_itemUpThanhVien.setText((thanhVienDTO.getNamSinh()));

        btnSave_itemUpThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edtTenTV_itemUpThanhVien.getText().toString();
                String namsinh = edtNamSinh_itemUpThanhVien.getText().toString();
                int id = thanhVienDTO.getMaTV();

                boolean check = thanhVienDAO.capNhatThongTinTV(id, hoten, namsinh);
                if  (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật ko thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        btnHuy_itemUpThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void loadData(){
        list.clear();
        list = thanhVienDAO.getDataThanhVien();
        notifyDataSetChanged();
    }
}
