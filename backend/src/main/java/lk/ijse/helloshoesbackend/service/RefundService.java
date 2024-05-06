package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.RefundDTO;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface RefundService {
    void refundSaleItem(RefundDTO refundDTO);
    List<RefundDTO> getAllRefunds();
}
