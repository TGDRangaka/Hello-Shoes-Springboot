package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class SaleEntity {
    @Id
    private String orderId;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentMethods paymentMethod;
    private Integer addedPoints;
    private LocalDate orderDate;
    private LocalTime orderTime;

    @ManyToOne
    private EmployeeEntity employee;
    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerEntity customer;
    @OneToMany(mappedBy = "saleItemId.sale", cascade = CascadeType.ALL)
    private List<SaleItemEntity> saleItems;
}
