package lk.ijse.helloshoesbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotEmpty
    private String message;
    private LocalDate date;
    private LocalTime time;
    @NotNull
    private AlertType type;
}
