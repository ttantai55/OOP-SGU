package DAO;

import DTO.WarrantyDTO;
import java.util.Arrays;

// quản lí các bảo hành
public class WarrantyListDAO implements IRepository<WarrantyDTO> {
    private WarrantyDTO[] warranties;

    public WarrantyListDAO() {
        warranties = new WarrantyDTO[0];
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
                w.setStatus("HET_HIEU_LUC");
                return;
            }
        }
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
            if (w != null && w.getProduct().getProductName()
                    .toLowerCase().contains(productName.toLowerCase())) {
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
                System.out.println("Ma bao hanh : " + w.getWarrantyId()
                        + " | Hoa don   : " + w.getInvoiceId()
                        + " | San pham  : " + w.getProduct().getProductName()
                        + " | Tu        : " + w.getStartDate()
                        + " | Den       : " + w.getEndDate()
                        + " | Trang thai: " + w.getStatus());
            }
        }
    }

    // thiếu read và write

    public WarrantyDTO[] getAll() {
        return Arrays.copyOf(warranties, warranties.length);
    }

    // Kiểm tra bảo hành còn hiệu lực không
    public boolean isActive(String id) {
        WarrantyDTO w = findById(id);
        if (w == null) return false;
        java.util.Date today = new java.util.Date();
        return today.before(w.getEndDate());
    }
}
