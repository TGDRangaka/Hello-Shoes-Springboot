package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lk.ijse.helloshoesbackend.entity.enums.SupplierCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private String code;
    @NotEmpty(message = "name should not be empty")
    private String name;
    @Enumerated(EnumType.STRING)
    private SupplierCategories category;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String originCountry;
    @NotEmpty(message = "email should not be empty")
    @Email(message = "must be a valid email address")
    private String email;
    @Size(min = 10)
    private String contactNo1;
    @Size(min = 10)
    private String contactNo2;
}
