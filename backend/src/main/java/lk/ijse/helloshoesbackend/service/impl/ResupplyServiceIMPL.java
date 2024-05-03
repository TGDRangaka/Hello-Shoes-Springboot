package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.entity.ResupplyEntity;
import lk.ijse.helloshoesbackend.repo.ResupplyRepo;
import lk.ijse.helloshoesbackend.service.ResupplyService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResupplyServiceIMPL implements ResupplyService {
    private final ResupplyRepo resupplyRepo;

    @Override
    public boolean save(ResupplyDTO resupplyDTO) {
        ResupplyEntity save = resupplyRepo.save(Conversion.toResupplyEntity(resupplyDTO));
        if(save!= null){
            return true;
        }else{
            return false;
        }
    }
}
