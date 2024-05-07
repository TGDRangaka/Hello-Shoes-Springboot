package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;

import java.util.List;

public interface RefundService {
    void refundSaleItem(RefundDTO refundDTO);
    List<RefundDTO> getAllRefunds();
    List<RefundDTO> checkRefundedBefore(SaleItemDTO saleItem);
}
