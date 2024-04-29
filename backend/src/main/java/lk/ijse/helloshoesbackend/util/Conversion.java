package lk.ijse.helloshoesbackend.util;

import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Conversion {

    private static ModelMapper modelMapper = new ModelMapper();

    public static EmployeeDTO toEmployeeDTO(EmployeeEntity entity){
        return modelMapper.map(entity, EmployeeDTO.class);
    }
    public static EmployeeEntity toEmployeeEntity(EmployeeDTO dto){
        return modelMapper.map(dto, EmployeeEntity.class);
    }

    public static ItemDTO toItemDTO(ItemEntity entity){
        return modelMapper.map(entity, ItemDTO.class);
    }
    public static ItemEntity toItemEntity(ItemDTO dto){
        ItemEntity entity = modelMapper.map(dto, ItemEntity.class);
        System.out.println(entity.getSupplier());
        return entity;
    }

    public static List<InventoryDTO> toInventoryDTOList(List<InventoryEntity> entities){
        List<InventoryDTO> list = new ArrayList();
        for(InventoryEntity entity : entities){
            InventoryDTO dto = modelMapper.map(entity, InventoryDTO.class);
            dto.setItemImage(modelMapper.map(entity.getItemImage(), ItemImageDTO.class));
            list.add(dto);
        }
        return list;
    }
    public static List<InventoryEntity> toInventoryEntityList(List<InventoryDTO> dtos){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<InventoryEntity> list = new ArrayList();
        for(InventoryDTO dto : dtos){
            InventoryEntity entity = modelMapper.map(dto, InventoryEntity.class);
            entity.setItemImage(modelMapper.map(dto.getItemImage(), ItemImageEntity.class));
            list.add(entity);
        }
        return list;
    }

    public static SupplierDTO toSupplierDTO(SupplierEntity entity){
        return modelMapper.map(entity, SupplierDTO.class);
    }
    public static SupplierEntity toSupplierEntity(SupplierDTO dto){
        return modelMapper.map(dto, SupplierEntity.class);
    }
}
