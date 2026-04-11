package DTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class WarrantyDTO {

    private String maBaoHanh;
    private InvoiceDetailDTO chiTietDonHang;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private List<WarrantyHistoryDTO> dsLichSuBaoHanh;

    public WarrantyDTO(String maBaoHanh, InvoiceDetailDTO chiTietDonHang, Date ngayBatDau) {
        this.maBaoHanh = maBaoHanh;
        this.chiTietDonHang = chiTietDonHang;
        this.ngayBatDau = ngayBatDau;
        this.dsLichSuBaoHanh = new ArrayList<>();

        int soThang = chiTietDonHang.getSanPham().getThoiGianBaoHanh();
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayBatDau);
        cal.add(Calendar.MONTH, soThang);
        this.ngayKetThuc = cal.getTime();
    }

    public String getMaBaoHanh() { return maBaoHanh; }
    public InvoiceDetailDTO getChiTietDonHang() { return chiTietDonHang; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public List<WarrantyHistoryDTO> getDsLichSuBaoHanh() { return dsLichSuBaoHanh; }

    public void setMaBaoHanh(String maBaoHanh) { this.maBaoHanh = maBaoHanh; }
    public void setChiTietDonHang(InvoiceDetailDTO chiTietDonHang) { this.chiTietDonHang = chiTietDonHang; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public void setDsLichSuBaoHanh(List<WarrantyHistoryDTO> dsLichSuBaoHanh) { this.dsLichSuBaoHanh = dsLichSuBaoHanh; }
}