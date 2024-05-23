package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoesbackend.entity.enums.SupplierCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private String code;
    private String name;
    @Enumerated(EnumType.STRING)
    private SupplierCategories category; //INTERNATIONAL OR LOCAL
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String originCountry;
    private String email;
    private String contactNo1;
    private String contactNo2;
}
