package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.ResupplyDTO;

import java.util.List;

public interface ResupplyService {
    boolean save(ResupplyDTO resupplyDTO);
    List<ResupplyDTO> getAllResupplies();
}
