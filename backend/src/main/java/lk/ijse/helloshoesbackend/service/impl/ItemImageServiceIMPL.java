package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.entity.ItemImageEntity;
import lk.ijse.helloshoesbackend.repo.ItemImageRepo;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
