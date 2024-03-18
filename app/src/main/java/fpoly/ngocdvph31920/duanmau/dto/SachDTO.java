package fpoly.ngocdvph31920.duanmau.dto;

public class SachDTO {
    private int maSach;
    private String tenSach;
    private int giaThue;
    private int maLoai;
    private int soLuongDaMuon;
    private String tenLoai;

    public SachDTO(int maSach, String tenSach, int giaThue, int maLoai, String tenLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuongDaMuon() {
        return soLuongDaMuon;
    }

    public void setSoLuongDaMuon(int soLuongDaMuon) {
        this.soLuongDaMuon = soLuongDaMuon;
    }

    public SachDTO(int maSach, String tenSach, int soLuongDaMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongDaMuon = soLuongDaMuon;
    }

    public SachDTO() {
    }


    public SachDTO(int maSach, String tenSach, int giaThue, int maLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}
