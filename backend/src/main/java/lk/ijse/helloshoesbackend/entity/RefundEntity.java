package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refund")
public class RefundEntity {
    @Id
    private String refundId;
    private String description;
    private LocalDate refundDate;
    private int qty;
    private double refundTotal;

    @ManyToOne
    private EmployeeEntity employee;
    @ManyToOne
    private SaleItemEntity saleItem;
}
