package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.dto.ResupplyItemDTO;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.repo.ResupplyRepo;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.service.ResupplyService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ResupplyServiceIMPL implements ResupplyService {
    private final ResupplyRepo resupplyRepo;
    private final InventoryService inventoryService;

    @Override
    public boolean save(ResupplyDTO resupplyDTO) {
        log.info("Attempting to save resupply");

//        set resupply details (id, date)
        log.info("Setting resupply details (Id, supplyId, date");
        String resupplyId = UtilMatter.generateUUID();
        resupplyDTO.setSupplyId(resupplyId);
        resupplyDTO.setSuppliedDate(LocalDate.now());

        for(int i = 0; i < resupplyDTO.getResupplyItems().size(); i++){
            ResupplyItemDTO resupplyItem = resupplyDTO.getResupplyItems().get(i);
            String inventoryCode = resupplyItem.getResupplyItemId().getInventory().getInventoryCode();

//            update inventory item stock
            log.info("Attempting to update inventory stock");
            inventoryService.restock(inventoryCode, resupplyItem.getSuppliedQty());

//            set resupplyId for each item
            resupplyItem.getResupplyItemId().getResupply().setSupplyId(resupplyId);
        }
//
        log.info("Saving resupply: {}", resupplyDTO.getSupplyId());
        ResupplyEntity save = resupplyRepo.save(Conversion.toResupplyEntity(resupplyDTO));
        if(save!= null){
            log.info("Saved resupply: {}", resupplyDTO.getSupplyId());
            return true;
        }
        log.error("Could not save resupply: {}", resupplyDTO.getSupplyId());
        throw new RuntimeException("Could not saved resupply: " + resupplyDTO.getSupplyId());
    }

    @Override
    public List<ResupplyDTO> getAllResupplies() {
        log.info("Fetching all resupplies");
        List<ResupplyEntity> all = resupplyRepo.findAll();
        log.info("Fetched {} resupplies", all.size());
        return Conversion.toResupplyDTOList(all);
    }
}
