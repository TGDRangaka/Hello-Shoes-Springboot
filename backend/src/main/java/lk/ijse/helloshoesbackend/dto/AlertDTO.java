package lk.ijse.helloshoesbackend.dto;

import lk.ijse.helloshoesbackend.entity.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertDTO {
    private String id;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private AlertType type;
}
