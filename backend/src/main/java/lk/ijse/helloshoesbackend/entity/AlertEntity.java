package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alert")
public class AlertEntity {
    @Id
    private String id;
    private String message;
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private AlertType type;
}
