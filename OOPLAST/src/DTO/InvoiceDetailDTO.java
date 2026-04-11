package DTO;
public class InvoiceDetailDTO {

        private ProductDTO sanPham;
        private int soLuong;
        private double donGia;
        private WarrantyDTO baoHanh;
        private PromotionDTO khuyenMai;

        public InvoiceDetailDTO(ProductDTO sanPham, int soLuong, WarrantyDTO baoHanh, PromotionDTO khuyenMai) {
            this.sanPham = sanPham;
            this.soLuong = soLuong;
            this.donGia = sanPham.getGia();
            this.baoHanh = baoHanh;
            this.khuyenMai = khuyenMai;
        }
    public double getThanhTien() {
        double gia = donGia * soLuong;
        if (khuyenMai != null) {
            gia -= gia * khuyenMai.getPhanTramGiamGia() / 100;
        }
        return gia;
    }

    public ProductDTO getSanPham() { return sanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
    public WarrantyDTO getBaoHanh() { return baoHanh; }
    public PromotionDTO getKhuyenMai() { return khuyenMai; }

    public void setSanPham(ProductDTO sanPham) { this.sanPham = sanPham; }
    public void setBaoHanh(WarrantyDTO baoHanh) { this.baoHanh = baoHanh; }
    public void setKhuyenMai(PromotionDTO khuyenMai) { this.khuyenMai = khuyenMai; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
}