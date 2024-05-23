package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.dto.SaleDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;

import java.util.List;
import java.util.Map;

public interface SaleBO {
    Map saveSale(SaleDTO saleDTO, String user);
    List<SaleDTO> getSales();
    void refundSaleItems(List<RefundDTO> refunds, String user);
    List<SaleItemDTO> getSaleItems(String orderId);
    List<RefundDTO> getAllRefunds();
}
