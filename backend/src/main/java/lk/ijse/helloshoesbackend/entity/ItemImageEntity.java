package lk.ijse.helloshoesbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
