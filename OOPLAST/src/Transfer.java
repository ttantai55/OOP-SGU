public class Transfer extends PaymentDTO {

    private String soTaiKhoan;
    private String tenTaiKhoan;
    private String nganHang;

    public Transfer(String soTaiKhoan, String tenTaiKhoan, String nganHang, PaymentDTO payment) {
        super(payment.getMaThanhToan(), payment.getTongCong(), payment.getNgayThanhToan());
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.nganHang = nganHang;
    }

    public void thanhToan() {
        System.out.println("Chuyển khoản thành công!");
    }

    public String getSoTaiKhoan() { return soTaiKhoan; }
    public String getTenTaiKhoan() { return tenTaiKhoan; }
    public String getNganHang() { return nganHang; }

    public void setSoTaiKhoan(String soTaiKhoan) { this.soTaiKhoan = soTaiKhoan; }
    public void setTenTaiKhoan(String tenTaiKhoan) { this.tenTaiKhoan = tenTaiKhoan; }
    public void setNganHang(String nganHang) { this.nganHang = nganHang; }
}