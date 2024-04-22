package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itemImage")
public class ItemImageEntity {
    @Id
    private String id;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
}
