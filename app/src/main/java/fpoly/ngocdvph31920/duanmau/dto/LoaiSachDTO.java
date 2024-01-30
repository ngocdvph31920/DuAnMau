package fpoly.ngocdvph31920.duanmau.dto;

public class LoaiSachDTO {
    private int maLoai;
    private String hoTen;

    public LoaiSachDTO() {
    }

    public LoaiSachDTO(int maLoai, String hoTen) {
        this.maLoai = maLoai;
        this.hoTen = hoTen;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
}
