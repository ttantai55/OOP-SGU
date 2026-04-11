package DTO;
import java.util.Date;

public class PaymentDTO {
    private double tongCong;
    private Date ngayThanhToan;
    private String maThanhToan;

    public PaymentDTO(String maThanhToan, double tongCong, Date ngayThanhToan) {
        this.maThanhToan = maThanhToan;
        this.tongCong = tongCong;
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getMaThanhToan() { return maThanhToan; }
    public double getTongCong() { return tongCong; }
    public Date getNgayThanhToan() { return ngayThanhToan; }

    public void setMaThanhToan(String maThanhToan) { this.maThanhToan = maThanhToan; }
    public void setTongCong(double tongCong) { this.tongCong = tongCong; }
    public void setNgayThanhToan(Date ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }
}