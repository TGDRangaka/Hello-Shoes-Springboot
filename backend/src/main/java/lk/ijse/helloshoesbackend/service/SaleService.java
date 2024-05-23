package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;

import java.util.List;

public interface SaleService {
    void updateSaleTotal(String orderId, Double amount);
    boolean save(SaleDTO saleDTO);
    List<SaleDTO> getSales();
    List<SaleItemDTO> getSaleItems(String orderId);
    void refundUpdateSaleItem(SaleItemId saleItemId, int refundQty);
    boolean checkRefundAvailable(String orderId);
}
