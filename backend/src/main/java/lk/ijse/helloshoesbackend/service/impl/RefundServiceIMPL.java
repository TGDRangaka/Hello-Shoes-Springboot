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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RefundServiceIMPL implements RefundService {
    private final RefundRepo refundRepo;

    @Override
    public void refundSaleItem(RefundDTO refundDTO) {
        refundDTO.setRefundId(UtilMatter.generateUUID());
        refundRepo.save(Conversion.toRefundEntity(refundDTO));
    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        List<RefundEntity> all = refundRepo.findAll();
        return Conversion.toRefundDTOList(all);
    }

    @Override
    public List<RefundDTO> checkRefundedBefore(SaleItemDTO saleItem) {
        List<RefundEntity> allBySaleItem = refundRepo.findAllBySaleItem(new ModelMapper().map(saleItem, SaleItemEntity.class));
        return Conversion.toRefundDTOList(allBySaleItem);
    }
}
