package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lk.ijse.helloshoesbackend.repo.ResupplyRepo;
import lk.ijse.helloshoesbackend.service.ResupplyService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ResupplyServiceIMPL implements ResupplyService {
    private final ResupplyRepo resupplyRepo;

    @Override
    public boolean save(ResupplyDTO resupplyDTO) {
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
