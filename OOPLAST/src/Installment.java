public class Installment extends PaymentDTO {

    private String maCongTyTaiChinh;
    private String tenCongTyTaiChinh;
    private String soHopDong;
    private int soThangTraGop;
    private double tienTraTruoc;

    public Installment(String maCongTyTaiChinh, String tenCongTyTaiChinh,
                       String soHopDong, int soThangTraGop, double tienTraTruoc, PaymentDTO payment) {
        super(payment.getMaThanhToan(), payment.getTongCong(), payment.getNgayThanhToan());
        this.maCongTyTaiChinh = maCongTyTaiChinh;
        this.tenCongTyTaiChinh = tenCongTyTaiChinh;
        this.soHopDong = soHopDong;
        this.soThangTraGop = soThangTraGop;
        this.tienTraTruoc = tienTraTruoc;
    }

    public void inThongTin() {
        System.out.println("Công ty tài chính : " + tenCongTyTaiChinh);
        System.out.println("Số hợp đồng       : " + soHopDong);
        System.out.println("Số tháng trả góp  : " + soThangTraGop);
        System.out.println("Tiền trả trước    : " + tienTraTruoc);
    }

    public String getMaCongTyTaiChinh() { return maCongTyTaiChinh; }
    public String getTenCongTyTaiChinh() { return tenCongTyTaiChinh; }
    public String getSoHopDong() { return soHopDong; }
    public int getSoThangTraGop() { return soThangTraGop; }
    public double getTienTraTruoc() { return tienTraTruoc; }

    public void setMaCongTyTaiChinh(String ma) { this.maCongTyTaiChinh = ma; }
    public void setTenCongTyTaiChinh(String ten) { this.tenCongTyTaiChinh = ten; }
    public void setSoHopDong(String soHopDong) { this.soHopDong = soHopDong; }
    public void setSoThangTraGop(int soThang) { this.soThangTraGop = soThang; }
    public void setTienTraTruoc(double tien) { this.tienTraTruoc = tien; }
}