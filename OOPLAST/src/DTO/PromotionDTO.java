package DTO;
import java.util.Date;

public class PromotionDTO {

    private String maKhuyenMai;
    private String tenChuongTrinhKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String dieuKienApDung;
    private double phanTramGiamGia;

    public PromotionDTO(String maKhuyenMai, String tenChuongTrinhKM, Date ngayBatDau,
                     Date ngayKetThuc, String dieuKienApDung, double phanTramGiamGia) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenChuongTrinhKM = tenChuongTrinhKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dieuKienApDung = dieuKienApDung;
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public String getTenChuongTrinhKM() { return tenChuongTrinhKM; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public String getDieuKienApDung() { return dieuKienApDung; }
    public double getPhanTramGiamGia() { return phanTramGiamGia; }

    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }
    public void setTenChuongTrinhKM(String ten) { this.tenChuongTrinhKM = ten; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public void setDieuKienApDung(String dk) { this.dieuKienApDung = dk; }
    public void setPhanTramGiamGia(double phanTram) { this.phanTramGiamGia = phanTram; }
}