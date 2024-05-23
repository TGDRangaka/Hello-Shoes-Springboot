package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;
import lk.ijse.helloshoesbackend.exception.InvalidDataException;

import java.util.List;

public interface ResupplyBO {
    boolean saveResupply(ResupplyDTO resupplyDTO);
    List<ResupplyDTO> getAllResupplies();
}
