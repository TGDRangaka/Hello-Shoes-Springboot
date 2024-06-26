package lk.ijse.helloshoesbackend.util;

import lk.ijse.helloshoesbackend.dto.*;
import lk.ijse.helloshoesbackend.entity.*;
import lk.ijse.helloshoesbackend.entity.keys.ResupplyItemId;
import lk.ijse.helloshoesbackend.entity.keys.SaleItemId;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Conversion {

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toUserDTO(UserEntity entity){
        log.info("Converting UserEntity to UserDTO");
        return modelMapper.map(entity, UserDTO.class);
    }
    public static UserEntity toUserEntity(UserDTO dto){
        log.info("Converting UserDTO to UserEntity");
        return modelMapper.map(dto, UserEntity.class);
    }

    public static EmployeeDTO toEmployeeDTO(EmployeeEntity entity){
        log.info("Converting EmployeeEntity to EmployeeDTO");
        return modelMapper.map(entity, EmployeeDTO.class);
    }
    public static EmployeeEntity toEmployeeEntity(EmployeeDTO dto){
        log.info("Converting EmployeeDTO to EmployeeEntity");
        return modelMapper.map(dto, EmployeeEntity.class);
    }
    public static List<EmployeeDTO> toEmployeeDTOList(List<EmployeeEntity> entities){
        log.info("Converting List<EmployeeEntity> to List<EmployeeDTO>");
        return modelMapper.map(entities, new TypeToken<List<EmployeeDTO>>(){}.getType());
    }

    public static ItemDTO toItemDTO(ItemEntity entity){
        log.info("Converting ItemEntity to ItemDTO");
        return modelMapper.map(entity, ItemDTO.class);
    }
    public static ItemEntity toItemEntity(ItemDTO dto){
        log.info("Converting ItemDTO to ItemEntity");
        ItemEntity entity = modelMapper.map(dto, ItemEntity.class);
        return entity;
    }
    public static List<ItemDTO> toItemDTOList(List<ItemEntity> items){
        log.info("Converting List<ItemEntity> to List<ItemDTO>");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return items.stream()
                .map(entity -> {
                    List<InventoryDTO> inventoryDTOList = new ArrayList<>();
                    entity.getInventoryItems().forEach(inventory -> {
                        inventoryDTOList.add(new InventoryDTO(
                                inventory.getInventoryCode(),
                                inventory.getSize(),
                                inventory.getColors(),
                                inventory.getOriginalQty(),
                                inventory.getCurrentQty(),
                                inventory.getStatus(),
                                null,
                                modelMapper.map(inventory.getItemImage(), ItemImageDTO.class),
                                null,
                                null
                        ));
                    });
                    return new ItemDTO(
                            entity.getItemCode(),
                            entity.getDescription(),
                            entity.getCategory(),
                            entity.getSupplierName(),
                            null,
                            entity.getUnitPriceSale(),
                            entity.getUnitPriceBuy(),
                            entity.getExpectedProfit(),
                            entity.getProfitMargin(),
                            inventoryDTOList
                    );
                })
                .collect(Collectors.toList());
    }

    public static List<InventoryDTO> toInventoryDTOList(List<InventoryEntity> entities){
        log.info("Converting List<InventoryEntity> to List<InventoryDTO>");
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
        log.info("Converting List<InventoryDTO> to List<InventoryEntity>");
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
        log.info("Converting SupplierEntity to SupplierDTO");
        return modelMapper.map(entity, SupplierDTO.class);
    }
    public static SupplierEntity toSupplierEntity(SupplierDTO dto){
        log.info("Converting SupplierDTO to SupplierEntity");
        return modelMapper.map(dto, SupplierEntity.class);
    }
    public static List<SupplierDTO> toSupplierDTOList(List<SupplierEntity> entities){
        log.info("Converting List<SupplierEntity> to List<SupplierDTO>");
        return entities.stream()
                .map(entity -> modelMapper.map(entity, SupplierDTO.class))
                .collect(Collectors.toList());
    }

    public static CustomerDTO toCustomerDTO(CustomerEntity entity){
        log.info("Converting CustomerEntity to CustomerDTO");
        return modelMapper.map(entity, CustomerDTO.class);
    }
    public static CustomerEntity toCustomerEntity(CustomerDTO dto){
        log.info("Converting CustomerDTO to CustomerEntity");
        return dto == null ? null : modelMapper.map(dto, CustomerEntity.class);
    }
    public static List<CustomerDTO> toCustomerDTOList(List<CustomerEntity> entities){
        log.info("Converting List<CustomerEntity> to List<CustomerDTO>");
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    public static SaleEntity toSaleEntity(SaleDTO dto){
        log.info("Converting SaleDTO to SaleEntity");
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

    public static List<SaleDTO> toSaleDTOList(List<SaleEntity> entities){
        log.info("Converting List<SaleEntity> to List<SaleDTO>");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return entities.stream()
                .map(entity -> {
                    SaleDTO dto = new SaleDTO(
                            entity.getOrderId(),
                            entity.getTotalPrice(),
                            entity.getPaymentMethod(),
                            entity.getAddedPoints(),
                            entity.getOrderDate(),
                            entity.getOrderTime(),
                            new EmployeeDTO(entity.getEmployee().getName()),
                            entity.getCustomer() == null ? null : modelMapper.map(entity.getCustomer(), CustomerDTO.class),
                            toSaleItemDTOList(entity.getSaleItems())
//                            entity.getSaleItems().stream().map(saleItem -> {
//                                InventoryEntity dbInventory = saleItem.getSaleItemId().getItem();
//                                InventoryEntity inventory = new InventoryEntity();
//                                inventory.setInventoryCode(dbInventory.getInventoryCode());
//                                inventory.setItemImage(dbInventory.getItemImage());
//                                return new SaleItemDTO(new SaleItemId(null, inventory), saleItem.getQty(), saleItem.getUnitPrice());
//                            }).collect(Collectors.toList())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static List<SaleItemDTO> toSaleItemDTOList(List<SaleItemEntity> entities){
        log.info("Converting List<SaleItemEntity> to List<SaleItemDTO>");
        return entities.stream().map(saleItem -> {
            InventoryEntity dbInventory = saleItem.getSaleItemId().getItem();
            InventoryEntity inventory = new InventoryEntity();
            SaleEntity sale = new SaleEntity();

            sale.setOrderId(saleItem.getSaleItemId().getSale().getOrderId());
            inventory.setInventoryCode(dbInventory.getInventoryCode());
            inventory.setItemImage(dbInventory.getItemImage());

            return new SaleItemDTO(new SaleItemId(sale, inventory), saleItem.getQty(), saleItem.getUnitPrice());
        }).collect(Collectors.toList());
    }

    public static ResupplyEntity toResupplyEntity(ResupplyDTO dto){
        log.info("Converting ResupplyDTO to ResupplyEntity");
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ResupplyItemEntity> entityList = new ArrayList<>();
        for(ResupplyItemDTO resupplyItemDTO : dto.getResupplyItems()){
            entityList.add(modelMapper.map(resupplyItemDTO, ResupplyItemEntity.class));
        }
        ResupplyEntity entity = modelMapper.map(dto, ResupplyEntity.class);
        entity.setResupplyItems(entityList);
        return entity;
    }
    public static List<ResupplyDTO> toResupplyDTOList(List<ResupplyEntity> entities){
        log.info("Converting List<ResupplyEntity> to List<ResupplyDTO>");
        return entities.stream().map(
                entity -> {
                    ResupplyDTO resupplyDTO = new ResupplyDTO(
                            entity.getSupplyId(),
                            entity.getSuppliedDate(),
                            entity.getTotalQty(),
                            modelMapper.map(entity.getSupplier(), SupplierDTO.class),
                            entity.getResupplyItems().stream().map(resupplyItem -> {
                                InventoryEntity dbInventory = resupplyItem.getResupplyItemId().getInventory();
                                InventoryEntity inventoryEntity = new InventoryEntity();
                                inventoryEntity.setInventoryCode(dbInventory.getInventoryCode());
                                inventoryEntity.setItemImage(dbInventory.getItemImage());
                                inventoryEntity.setCurrentQty(dbInventory.getCurrentQty());
                                inventoryEntity.setStatus(dbInventory.getStatus());

                                ItemEntity item = new ItemEntity();
                                item.setDescription(dbInventory.getItem().getDescription());
                                item.setItemCode(dbInventory.getItem().getItemCode());
                                inventoryEntity.setItem(item);

                                return new ResupplyItemDTO(new ResupplyItemId(inventoryEntity, null), resupplyItem.getSuppliedQty());

                            }).collect(Collectors.toList())
                    );
                    return resupplyDTO;
                }
        ).collect(Collectors.toList());
    }

    public static RefundEntity toRefundEntity(RefundDTO refundDTO){
        log.info("Converting RefundDTO to RefundEntity");
        return modelMapper.map(refundDTO, RefundEntity.class);
    }

    public static List<RefundDTO> toRefundDTOList(List<RefundEntity> entities){
        log.info("Converting List<RefundEntity> to List<RefundDTO>");
        return entities.stream().map(entity -> {
            InventoryEntity dbTempInventory = entity.getSaleItem().getSaleItemId().getItem();
            InventoryEntity tempInventory = new InventoryEntity();
            SaleEntity tempSale = new SaleEntity();

            tempInventory.setInventoryCode(dbTempInventory.getInventoryCode());
            tempInventory.setItemImage(dbTempInventory.getItemImage());
            tempSale.setOrderId(entity.getSaleItem().getSaleItemId().getSale().getOrderId());

            RefundDTO refundDTO = new RefundDTO(
                    entity.getRefundId(),
                    entity.getDescription(),
                    entity.getRefundDate(),
                    entity.getQty(),
                    entity.getRefundTotal(),
                    new EmployeeDTO(),
                    new SaleItemDTO(
                            new SaleItemId(
                                    tempSale,
                                    tempInventory
                            ),
                            null, null
                    )
            );
            return refundDTO;
        }).collect(Collectors.toList());
    }

    public static AlertEntity toAlertEntity(AlertDTO alertDTO){
        log.info("Converting AlertDTO to AlertEntity");
        return modelMapper.map(alertDTO, AlertEntity.class);
    }

    public static List<AlertDTO> toAlertDTOList(List<AlertEntity> list){
        log.info("Converting List<AlertEntity> to List<AlertDTO>");
        return modelMapper.map(list, new TypeToken<List<AlertDTO>>(){}.getType());
    }
}
