package DTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDTO {

    private String maHoaDon;
    private CustomerDTO khachHang;
    private EmployeeDTO nhanVien;
    private List<InvoiceDetailDTO> dsChiTiet;
    private Date ngayTao;
    private String trangThai;
    private double tongTienThanhToan;

    public InvoiceDTO(String maHoaDon, CustomerDTO khachHang, EmployeeDTO nhanVien, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.dsChiTiet = new ArrayList<>();
        this.ngayTao = new Date();
        this.trangThai = trangThai;
        this.tongTienThanhToan = 0;
    }

    public String getMaHoaDon() { return maHoaDon; }
    public CustomerDTO getKhachHang() { return khachHang; }
    public EmployeeDTO getNhanVien() { return nhanVien; }
    public List<InvoiceDetailDTO> getDsChiTiet() { return dsChiTiet; }
    public Date getNgayTao() { return ngayTao; }
    public String getTrangThai() { return trangThai; }

    public double getTongTienThanhToan() {
        return dsChiTiet.stream()
                .mapToDouble(InvoiceDetailDTO::getThanhTien)
                .sum();
    }

    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }
    public void setKhachHang(CustomerDTO khachHang) { this.khachHang = khachHang; }
    public void setNhanVien(EmployeeDTO nhanVien) { this.nhanVien = nhanVien; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
    public void setDsChiTiet(List<InvoiceDetailDTO> dsChiTiet) { this.dsChiTiet = dsChiTiet; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public void themChiTiet(InvoiceDetailDTO ct) { dsChiTiet.add(ct); }
}