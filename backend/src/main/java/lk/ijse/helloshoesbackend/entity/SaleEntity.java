package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private PaymentMethods paymentMethod;
    private Integer addedPoints;

    @ManyToOne
    private EmployeeEntity employee;
    @ManyToOne
    private CustomerEntity customer;
    @OneToMany(mappedBy = "saleItemId.sale", cascade = CascadeType.ALL)
    private List<SaleItemEntity> saleItems;
}