package lk.ijse.helloshoesspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date refundDate;
    @OneToOne
    private EmployeeEntity employee;
    @OneToOne
    private SaleItemEntity saleItem;
}
