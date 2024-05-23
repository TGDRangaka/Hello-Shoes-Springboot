package lk.ijse.helloshoesbackend.service.impl;

import java.util.Optional;

import lk.ijse.helloshoesbackend.dto.ItemImageDTO;
import lk.ijse.helloshoesbackend.entity.ItemImageEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.ItemImageRepo;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemImageServiceIMPL implements ItemImageService {
    private final ItemImageRepo itemImageRepo;

    @Override
    public String saveImage(String image) {
        log.info("Saving image");
        String imageId = UtilMatter.generateUUID();
        ItemImageEntity entity = new ItemImageEntity(imageId, image);
        ItemImageEntity savedImage = itemImageRepo.save(entity);

        log.info("Image saved successfully with ID: {}", imageId);
        return savedImage.getId();
    }

    @Override
    public void updateImage(ItemImageDTO itemImageDTO){
        log.info("Updating image with id: {}", itemImageDTO.getId());
        Optional<ItemImageEntity> byId = itemImageRepo.findById(itemImageDTO.getId());
        if(byId.isPresent()){
            byId.get().setImage(itemImageDTO.getImage());

            log.info("Image updated successfully with ID: {}", itemImageDTO.getId());
            return;
        }
        log.error("Image not found for id: {}", itemImageDTO.getId());
        throw new NotFoundException("Image not found for id " + itemImageDTO.getId());
    }
}
