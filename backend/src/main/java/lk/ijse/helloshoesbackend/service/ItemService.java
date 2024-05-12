package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    boolean save(ItemDTO dto) throws Exception;

    void update(ItemDTO dto, String itemCode);

    List<ItemDTO> getAllItems();
}
