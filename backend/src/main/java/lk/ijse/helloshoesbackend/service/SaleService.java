package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;

import java.util.List;

public interface SaleService {
    boolean save(SaleDTO saleDTO, String user);
    List<SaleDTO> getSales();
    List<SaleItemDTO> getSaleItems(String orderId);
    boolean checkRefundAvailable(String orderId);
}
