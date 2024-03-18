package fpoly.ngocdvph31920.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fpoly.ngocdvph31920.duanmau.R;
import fpoly.ngocdvph31920.duanmau.dao.LoaiSachDAO;
import fpoly.ngocdvph31920.duanmau.dto.LoaiSachDTO;

import java.util.ArrayList;

public class SpinLOaiSachAdapter extends ArrayAdapter<LoaiSachDTO> {
    Context context;
    ArrayList<LoaiSachDTO> list;

    public SpinLOaiSachAdapter(@NonNull Context context, ArrayList<LoaiSachDTO> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_spin_sach, null);
        }
        final LoaiSachDTO loaiSachDTO = list.get(position);
        if (loaiSachDTO!= null){
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        }


        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
