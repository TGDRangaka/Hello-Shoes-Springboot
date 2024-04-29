package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.dto.ItemImageDTO;

public interface ItemBO {
    boolean saveItem(ItemDTO dto);
}
