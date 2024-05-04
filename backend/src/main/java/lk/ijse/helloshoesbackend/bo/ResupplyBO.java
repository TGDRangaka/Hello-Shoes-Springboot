package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;

import java.util.List;

public interface ResupplyBO {
    boolean saveResupply(ResupplyDTO resupplyDTO);
    List<ResupplyDTO> getAllResupplies();
}
