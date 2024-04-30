package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ItemDTO;

public interface ItemBO {
    boolean saveItem(ItemDTO dto) throws Exception;
}
