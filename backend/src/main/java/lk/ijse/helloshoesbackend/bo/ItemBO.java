package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ItemDTO;

import java.util.List;

public interface ItemBO {
    boolean saveItem(ItemDTO dto);

    void updateItem(ItemDTO dto, String id);

    List<ItemDTO> getAllItems();
}
