package lk.ijse.helloshoesbackend.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lk.ijse.helloshoesbackend.entity.ResupplyItemEntity;
import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResupplyDTO {
    private String supplyId;
    private LocalDate suppliedDate;
    private Integer totalQty;

    private SupplierDTO supplier;
    private List<ResupplyItemDTO> resupplyItems;
}
