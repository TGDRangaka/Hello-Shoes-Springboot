package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.ItemImageDTO;

public interface ItemImageService {
    String saveImage(String image);

    void updateImage(ItemImageDTO itemImageDTO);
}
