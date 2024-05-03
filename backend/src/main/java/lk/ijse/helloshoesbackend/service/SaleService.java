package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.SaleDTO;

import java.util.List;

public interface SaleService {
    boolean save(SaleDTO saleDTO);
    List<SaleDTO> getSales();
}
