package lk.ijse.helloshoesspringboot.entity.keys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.helloshoesspringboot.entity.ItemEntity;
import lk.ijse.helloshoesspringboot.entity.ResupplyEntity;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ResupplyItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemEntity item;
    @ManyToOne(cascade = CascadeType.ALL)
    private ResupplyEntity resupply;
}