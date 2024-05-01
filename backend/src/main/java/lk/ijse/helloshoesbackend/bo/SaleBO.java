package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.SaleDTO;

public interface SaleBO {
    boolean saveSale(SaleDTO saleDTO, String user);
}
