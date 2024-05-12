package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.ItemImageDTO;
import lk.ijse.helloshoesbackend.entity.ItemImageEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.ItemImageRepo;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImageServiceIMPL implements ItemImageService {
    private final ItemImageRepo itemImageRepo;

    @Override
    public String saveImage(String image) {
        ItemImageEntity entity = new ItemImageEntity(UtilMatter.generateUUID(), image);
        ItemImageEntity savedImage = itemImageRepo.save(entity);
        return savedImage.getId();
    }

    @Override
    public void updateImage(ItemImageDTO itemImageDTO){
        Optional<ItemImageEntity> byId = itemImageRepo.findById(itemImageDTO.getId());
        if(byId.isPresent()){
            byId.get().setImage(itemImageDTO.getImage());
            return;
        }
        throw new NotFoundException("Image not found for id " + itemImageDTO.getId());
    }
}
