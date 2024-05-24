package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.ResupplyBO;
import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.dto.ResupplyItemDTO;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;
import lk.ijse.helloshoesbackend.service.InventoryService;
import lk.ijse.helloshoesbackend.service.ResupplyService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResupplyBOIMPL implements ResupplyBO {
    private final ResupplyService resupplyService;
    private final InventoryService inventoryService;

    @Override
    public boolean saveResupply(ResupplyDTO resupplyDTO){
        log.info("Attempting to save resupply");
        if(resupplyDTO.getTotalQty() <= 0) {
            log.error("Qty must be greater than 1");
            throw new InvalidDataException("Qty must be greater than 1");
        }

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

        return resupplyService.save(resupplyDTO);
    }

    @Override
    public List<ResupplyDTO> getAllResupplies() {
        log.info("Attempting to get all resupplies");
        return resupplyService.getAllResupplies();
    }
}
