public class Credit extends PaymentDTO {

    private String soThe;
    private String tenChuThe;
    private String nganHang;

    public Credit(String soThe, String tenChuThe, String nganHang, PaymentDTO payment) {
        super(payment.getMaThanhToan(), payment.getTongCong(), payment.getNgayThanhToan());
        this.soThe = soThe;
        this.tenChuThe = tenChuThe;
        this.nganHang = nganHang;
    }

    public void thanhToan() {
        System.out.println("Thanh toán thẻ thành công!");
    }

    public String getSoThe() { return soThe; }
    public String getTenChuThe() { return tenChuThe; }
    public String getNganHang() { return nganHang; }

    public void setSoThe(String soThe) { this.soThe = soThe; }
    public void setTenChuThe(String tenChuThe) { this.tenChuThe = tenChuThe; }
    public void setNganHang(String nganHang) { this.nganHang = nganHang; }
}