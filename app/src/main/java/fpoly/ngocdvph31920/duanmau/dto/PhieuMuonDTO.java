package fpoly.ngocdvph31920.duanmau.dto;

public class PhieuMuonDTO {
    private int maPM;
    private String maTT;
    private int maTV;
    private int maSach;
    private String ngay;
    private int traSach;
    private int tienThue;

    private String tenTV;
    private String TenTT;
    private String TenS;

    public PhieuMuonDTO() {
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getTenTT() {
        return TenTT;
    }

    public void setTenTT(String tenTT) {
        TenTT = tenTT;
    }

    public String getTenS() {
        return TenS;
    }

    public void setTenS(String tenS) {
        TenS = tenS;
    }
    //pm.MaPM, pm.MaTV, tv.tenTV, pm.MaTT, tt.TenTT, pm.MaS, sc.TenS , pm.ngay, pm.traSach, pm.GiaThueS
    public PhieuMuonDTO(int maPM, int maTV, String tenTV, String maTT, String tenTT, int maSach, String tenS, String ngay, int traSach, int tienThue) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.traSach = traSach;
        this.tienThue = tienThue;
        this.tenTV = tenTV;
        this.TenTT = tenTT;
        this.TenS = tenS;
    }

    public PhieuMuonDTO(String maTT, int maTV, int maSach, String ngay, int traSach, int tienThue) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.traSach = traSach;
        this.tienThue = tienThue;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }
}
