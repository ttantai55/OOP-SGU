package DTO;
import java.util.Date
    public class WarrantyHistoryDTO {

        private String maLichSu;
        private Date ngayBaoHanh;
        private int lanThu;
        private String noiDungLoi;
        private String phuongAnXuLy;
        private String linhKienThayThe;
        private double chiPhiSuaChua;
        private String tinhTrangMayTruocSua;
        private String tinhTrangMaySauSua;
        private String ghiChu;
        private String nhanVienKyThuat;
        private String trangThaiXuLy;

        public WarrantyHistoryDTO(String maLichSu, Date ngayBaoHanh, int lanThu, String noiDungLoi,
                                  String phuongAnXuLy, String linhKienThayThe, double chiPhiSuaChua,
                                  String tinhTrangMayTruocSua, String tinhTrangMaySauSua,
                                  String ghiChu, String nhanVienKyThuat, String trangThaiXuLy) {
            this.maLichSu = maLichSu;
            this.ngayBaoHanh = ngayBaoHanh;
            this.lanThu = lanThu;
            this.noiDungLoi = noiDungLoi;
            this.phuongAnXuLy = phuongAnXuLy;
            this.linhKienThayThe = linhKienThayThe;
            this.chiPhiSuaChua = chiPhiSuaChua;
            this.tinhTrangMayTruocSua = tinhTrangMayTruocSua;
            this.tinhTrangMaySauSua = tinhTrangMaySauSua;
            this.ghiChu = ghiChu;
            this.nhanVienKyThuat = nhanVienKyThuat;
            this.trangThaiXuLy = trangThaiXuLy;
        }

        public String getMaLichSu() { return maLichSu; }
        public Date getNgayBaoHanh() { return ngayBaoHanh; }
        public int getLanThu() { return lanThu; }
        public String getNoiDungLoi() { return noiDungLoi; }
        public String getPhuongAnXuLy() { return phuongAnXuLy; }
        public String getLinhKienThayThe() { return linhKienThayThe; }
        public double getChiPhiSuaChua() { return chiPhiSuaChua; }
        public String getTinhTrangMayTruocSua() { return tinhTrangMayTruocSua; }
        public String getTinhTrangMaySauSua() { return tinhTrangMaySauSua; }
        public String getGhiChu() { return ghiChu; }
        public String getNhanVienKyThuat() { return nhanVienKyThuat; }
        public String getTrangThaiXuLy() { return trangThaiXuLy; }

        public void setMaLichSu(String maLichSu) { this.maLichSu = maLichSu; }
        public void setNgayBaoHanh(Date ngayBaoHanh) { this.ngayBaoHanh = ngayBaoHanh; }
        public void setLanThu(int lanThu) { this.lanThu = lanThu; }
        public void setNoiDungLoi(String noiDungLoi) { this.noiDungLoi = noiDungLoi; }
        public void setPhuongAnXuLy(String phuongAnXuLy) { this.phuongAnXuLy = phuongAnXuLy; }
        public void setLinhKienThayThe(String linhKienThayThe) { this.linhKienThayThe = linhKienThayThe; }
        public void setChiPhiSuaChua(double chiPhiSuaChua) { this.chiPhiSuaChua = chiPhiSuaChua; }
        public void setTinhTrangMayTruocSua(String tinhTrangMayTruocSua) { this.tinhTrangMayTruocSua = tinhTrangMayTruocSua; }
        public void setTinhTrangMaySauSua(String tinhTrangMaySauSua) { this.tinhTrangMaySauSua = tinhTrangMaySauSua; }
        public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
        public void setNhanVienKyThuat(String nhanVienKyThuat) { this.nhanVienKyThuat = nhanVienKyThuat; }
        public void setTrangThaiXuLy(String trangThaiXuLy) { this.trangThaiXuLy = trangThaiXuLy; }
    }

