package DAO;

import DTO.WarrantyDTO;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

// quản lí các bảo hành
public class WarrantyListDAO implements IRepository<WarrantyDTO> {
    private static WarrantyDTO[] warranties = new WarrantyDTO[0];
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public WarrantyListDAO() {
    }

    @Override
    public void add(WarrantyDTO obj) {
        warranties = Arrays.copyOf(warranties, warranties.length + 1);
        warranties[warranties.length - 1] = obj;
    }


    @Override
    public void remove(String id) {
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getWarrantyId().equals(id)) {
                w.setStatus(false);
                System.out.println("Da huy bao hanh: " + id + ".");
                return;
            }
        }
        System.out.println("Khong tim thay bao hanh: " + id + ".");
    }

    @Override
    public void update(WarrantyDTO obj) {
        for (int i = 0; i < warranties.length; i++) {
            if (warranties[i] != null && warranties[i].getWarrantyId().equals(obj.getWarrantyId())) {
                warranties[i] = obj;
                return;
            }
        }
    }

    @Override
    public WarrantyDTO findById(String id) {
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getWarrantyId().equals(id)) {
                return w;
            }
        }
        return null;
    }

    // Tìm bảo hành theo tên sản phẩm
    @Override
    public WarrantyDTO[] findByName(String productName) {
        WarrantyDTO[] temp = new WarrantyDTO[0];
        for (WarrantyDTO w : warranties) {
            if (w != null && w.getProduct() != null &&
                    w.getProduct().getProductName().toLowerCase().contains(productName.toLowerCase())) {
                temp = Arrays.copyOf(temp, temp.length + 1);
                temp[temp.length - 1] = w;
            }
        }
        return temp;
    }

    @Override
    public void displayAll() {
        for (WarrantyDTO w : warranties) {
            if (w != null) {
                String trangThai;
                if (w.isStatus()) {
                    trangThai = "Con hieu luc";
                } else {
                    trangThai = "Het hieu luc";
                }

                String tenSP = (w.getProduct() != null) ? w.getProduct().getProductName() : "N/A";
                System.out.println("Ma bao hanh : " + w.getWarrantyId()
                        + " | Hoa don   : " + w.getInvoiceId()
                        + " | San pham  : " + tenSP
                        + " | Tu        : " + sdf.format(w.getStartDate())
                        + " | Den       : " + sdf.format(w.getEndDate())
                        + " | Trang thai: " + trangThai);
            }
        }
    }

    @Override
    public void readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();

            while (line != null) {
                String[] parts = line.split(",");

                WarrantyDTO w = new WarrantyDTO();

                w.setWarrantyId(parts[0]);
                w.setInvoiceId(parts[1]);
                // parts[2] là productId - bỏ qua vì cần đối tượng ProductsDTO đầy đủ

                try {
                    Date ngayBatDau = sdf.parse(parts[3]);
                    w.setStartDate(ngayBatDau);
                    Date ngayKetThuc = sdf.parse(parts[4]);
                    w.setEndDate(ngayKetThuc);
                } catch (Exception e) {
                    // bỏ qua nếu không đọc được ngày
                }

                if (parts[5].equals("Active")) {
                    w.setStatus(true);
                } else {
                    w.setStatus(false);
                }

                w.setRepairCount(Integer.parseInt(parts[6]));

                int viTri = warranties.length;
                warranties = Arrays.copyOf(warranties, viTri + 1);
                warranties[viTri] = w;

                line = br.readLine();
            }

            br.close();
            System.out.println("Đọc dữ liệu từ file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void writeFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (WarrantyDTO w : warranties) {
                if (w != null) {
                    String productId;
                    if (w.getProduct() != null) {
                        productId = w.getProduct().getProductID();
                    } else {
                        productId = "N/A";
                    }

                    String status;
                    if (w.isStatus()) {
                        status = "Active";
                    } else {
                        status = "Cancelled";
                    }

                    String line = w.getWarrantyId() + "," +
                                 w.getInvoiceId() + "," +
                                 productId + "," +
                                 sdf.format(w.getStartDate()) + "," +
                                 sdf.format(w.getEndDate()) + "," +
                                 status + "," +
                                 w.getRepairCount();

                    bw.write(line);
                    bw.newLine();
                }
            }

            System.out.println("Ghi dữ liệu vào file " + filePath + " thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Kiểm tra bảo hành còn hiệu lực không
    public boolean isActive(String id) {
        WarrantyDTO w = findById(id);
        if (w == null) return false;
        if (!w.isStatus()) return false;
        java.util.Date today = new java.util.Date();
        return today.before(w.getEndDate());
    }
}
