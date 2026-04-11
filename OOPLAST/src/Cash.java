public class Cash extends PaymentDTO {
    private double tienKhachDua;
    private double tienTraLai;

    public Cash(double tienKhachDua, PaymentDTO payment) {
        super(payment.getMaThanhToan(), payment.getTongCong(), payment.getNgayThanhToan());
        this.tienKhachDua = tienKhachDua;
        this.tienTraLai = tienKhachDua - payment.getTongCong();
    }

    public void thanhToan() {
        System.out.println("Tiền trả lại: " + tienTraLai);
    }

    public double getTienKhachDua() { return tienKhachDua; }
    public double getTienTraLai() { return tienTraLai; }

    public void setTienKhachDua(double tienKhachDua) { this.tienKhachDua = tienKhachDua; }
    public void setTienTraLai(double tienTraLai) { this.tienTraLai = tienTraLai; }
}
