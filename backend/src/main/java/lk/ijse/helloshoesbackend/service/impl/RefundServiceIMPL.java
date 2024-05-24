package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.RefundDTO;
import lk.ijse.helloshoesbackend.dto.SaleItemDTO;
import lk.ijse.helloshoesbackend.entity.RefundEntity;
import lk.ijse.helloshoesbackend.entity.SaleItemEntity;
import lk.ijse.helloshoesbackend.repo.RefundRepo;
import lk.ijse.helloshoesbackend.service.RefundService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefundServiceIMPL implements RefundService {
    private final RefundRepo refundRepo;

    @Override
    public void refundSaleItem(RefundDTO refundDTO) {
        log.info("Saving refund");
        refundDTO.setRefundId(UtilMatter.generateUUID());
        refundRepo.save(Conversion.toRefundEntity(refundDTO));
        log.info("Refund saved successfully");
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        log.info("Fetching all refunds");
        List<RefundEntity> all = refundRepo.findAll();
        log.info("Fetched {} refunds", all.size());
        return Conversion.toRefundDTOList(all);
    }

    @Override
    public List<RefundDTO> checkRefundedBefore(SaleItemDTO saleItem) {
        log.info("Fetching refunds by orderId: {} and inventoryCode: {}", saleItem.getSaleItemId().getSale().getOrderId(), saleItem.getSaleItemId().getItem().getInventoryCode());
        List<RefundEntity> allBySaleItem = refundRepo.findAllBySaleItem(new ModelMapper().map(saleItem, SaleItemEntity.class));
        log.info("Fetched {} refunds", allBySaleItem.size());
        return Conversion.toRefundDTOList(allBySaleItem);
    }
}
