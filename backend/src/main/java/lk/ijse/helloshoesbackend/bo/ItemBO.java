package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ItemDTO;

import java.util.List;

public interface ItemBO {
    boolean saveItem(ItemDTO dto) throws Exception;
    List<ItemDTO> getAllItems();
}
