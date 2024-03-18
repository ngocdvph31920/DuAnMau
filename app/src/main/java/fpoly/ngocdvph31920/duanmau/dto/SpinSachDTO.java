package fpoly.ngocdvph31920.duanmau.dto;

public class SpinSachDTO {
    private int ma;
    private String name;

    public SpinSachDTO() {
    }

    public SpinSachDTO(int ma, String name) {
        this.ma = ma;
        this.name = name;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
