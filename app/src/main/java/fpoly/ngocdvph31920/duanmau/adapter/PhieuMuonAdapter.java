package fpoly.ngocdvph31920.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.ngocdvph31920.duanmau.R;
import fpoly.ngocdvph31920.duanmau.dao.PhieuMuonDAO;
import fpoly.ngocdvph31920.duanmau.dto.PhieuMuonDTO;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{

    private ArrayList<PhieuMuonDTO>list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuonDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText("Mã PM: "+ list.get(position).getMaPM());
        holder.txtMaTV.setText("Mã TV: "+ list.get(position).getMaTV());
        holder.txtTenTV.setText("Tên TV: "+ list.get(position).getTenTV());
        holder.txtMaTT.setText("Mã TT: "+ list.get(position).getMaTT());
        holder.txtTenTT.setText("Tên TT: "+ list.get(position).getTenTT());
        holder.txtMaSach.setText("Mã Sách: "+ list.get(position).getMaSach());
        holder.txtTenSach.setText("Tên Sách: "+ list.get(position).getTenS());
        holder.txtNgay.setText("Ngày: "+ list.get(position).getNgay());
        String trangthai = "";
        if (list.get(position).getTraSach() == 1){
            trangthai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangthai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trạng thái: "+ trangthai);
        holder.txtTien.setText("Tiền thuê: "+ list.get(position).getTienThue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kt = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMaPM());
                if (kt == true){
                    list.clear();
                    list = phieuMuonDAO.getdsPhieuMuon();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM,txtMaTV,txtTenTV,txtMaTT,txtTenTT,txtMaSach,txtTenSach,txtNgay,txtTrangThai,txtTien;
        Button btnTraSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtTenTT = itemView.findViewById(R.id.txtTenTT);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTien = itemView.findViewById(R.id.txtTien);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
