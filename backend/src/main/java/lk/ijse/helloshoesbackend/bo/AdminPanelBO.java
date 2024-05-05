package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;

import java.time.LocalDate;

public interface AdminPanelBO {
    AdminPanelDTO getAdminPanelDate(LocalDate date);
}
