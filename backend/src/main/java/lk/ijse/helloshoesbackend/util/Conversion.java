package lk.ijse.helloshoesbackend.util;

import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Conversion {

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toUserDTO(UserEntity entity){
        return modelMapper.map(entity, UserDTO.class);
    }
    public static UserEntity toUserEntity(UserDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }

    public static EmployeeDTO toEmployeeDTO(EmployeeEntity entity){
        return modelMapper.map(entity, EmployeeDTO.class);
    }
    public static EmployeeEntity toEmployeeEntity(EmployeeDTO dto){
        return modelMapper.map(dto, EmployeeEntity.class);
    }
    public static List<EmployeeDTO> toEmployeeDTOList(List<EmployeeEntity> entities){
        return modelMapper.map(entities, new TypeToken<List<EmployeeDTO>>(){}.getType());
    }

    public static ItemDTO toItemDTO(ItemEntity entity){
        return modelMapper.map(entity, ItemDTO.class);
    }
    public static ItemEntity toItemEntity(ItemDTO dto){
        ItemEntity entity = modelMapper.map(dto, ItemEntity.class);
        return entity;
    }
    public static List<ItemDTO> toItemDTOList(List<ItemEntity> items){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ItemDTO> dtos = new ArrayList<>();
        items.forEach(item -> {
            dtos.add(modelMapper.map(item, ItemDTO.class));
        });
        return dtos;
    }

    public static List<InventoryDTO> toInventoryDTOList(List<InventoryEntity> entities){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        entities.forEach(entity -> {
//            entity.setItem(null);
//            entity.setSaleItems(null);
//            entity.setResupplyItems(null);
        });
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

    public static CustomerDTO toCustomerDTO(CustomerEntity entity){
        return modelMapper.map(entity, CustomerDTO.class);
    }
    public static CustomerEntity toCustomerEntity(CustomerDTO dto){
        return modelMapper.map(dto, CustomerEntity.class);
    }
    public static List<CustomerDTO> toCustomerDTOList(List<CustomerEntity> entities){
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CustomerDTO.class))
                .collect(Collectors.toList());
    }
    public static List<CustomerEntity> toCustomerEntityList(List<CustomerDTO> dtos){
        return dtos.stream()
                .map(dto -> modelMapper.map(dto, CustomerEntity.class))
                .collect(Collectors.toList());
    }

    public static SaleEntity toSaleEntity(SaleDTO dto){
        SaleEntity entity = modelMapper.map(dto, SaleEntity.class);
        entity.setEmployee(toEmployeeEntity(dto.getEmployee()));
        entity.setCustomer(toCustomerEntity(dto.getCustomer()));
        List<SaleItemEntity> saleItemEntities = new ArrayList<>();
        for(SaleItemDTO saleItemDTO : dto.getSaleItems()){
            saleItemEntities.add(modelMapper.map(saleItemDTO, SaleItemEntity.class));
        }
        entity.setSaleItems(saleItemEntities);
        return entity;
    }
    public static SaleDTO toSaleDTO(SaleEntity entity){
        SaleDTO dto = modelMapper.map(entity, SaleDTO.class);
        dto.setEmployee(toEmployeeDTO(entity.getEmployee()));
        dto.setCustomer(toCustomerDTO(entity.getCustomer()));
        for(SaleItemEntity saleItemEntity : entity.getSaleItems()){
            dto.getSaleItems().add(modelMapper.map(saleItemEntity, SaleItemDTO.class));
        }
        return dto;
    }
}
