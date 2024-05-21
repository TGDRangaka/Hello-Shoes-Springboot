package lk.ijse.helloshoesbackend.bo;

import lk.ijse.helloshoesbackend.dto.AdminPanelDTO;
import lk.ijse.helloshoesbackend.dto.AlertDTO;

import java.time.LocalDate;
import java.util.List;

public interface AdminPanelBO {
    AdminPanelDTO getAdminPanelDate(LocalDate date);

    List<AlertDTO> recordAlert(AlertDTO alert);

    List<AlertDTO> getAllAlerts();
}
