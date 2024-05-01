package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.SaleDTO;

import java.util.Map;

public interface SaleBO {
    Map saveSale(SaleDTO saleDTO, String user);
}
