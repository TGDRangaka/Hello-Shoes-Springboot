package lk.ijse.helloshoesbackend.bo.impl;

import lk.ijse.helloshoesbackend.bo.ItemBO;
import lk.ijse.helloshoesbackend.dto.InventoryDTO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lk.ijse.helloshoesbackend.dto.ItemImageDTO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.enums.Sizes;
import lk.ijse.helloshoesbackend.service.ItemImageService;
import lk.ijse.helloshoesbackend.service.ItemService;
import lk.ijse.helloshoesbackend.service.SupplierService;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ItemBOIMPL implements ItemBO {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final SupplierService supplierService;

    @Override
    public boolean saveItem(ItemDTO dto) throws Exception {
        Random random = new Random();
        dto.setItemCode(dto.getItemCode() + (random.nextInt(40000) + 10000));
        List<InventoryDTO> colors = dto.getInventoryItems();
        List<InventoryDTO> allInventory = new ArrayList<>();

        if(dto.getCategory().name().equals("ACC")){
            String imgId = itemImageService.saveImage(colors.get(0).getItemImage().getImage());
            colors.get(0).getItemImage().setId(imgId);
            allInventory.add(new InventoryDTO(
                    dto.getItemCode() +"_"+ colors.get(0).getSize() +"_"+ colors.get(0).getColors(),
                    colors.get(0).getSize(),
                    colors.get(0).getColors(),
                    0,
                    0,
                    "not available",
                    new ItemDTO(
                            dto.getItemCode(),
                            dto.getDescription(),
                            dto.getCategory(),
                            dto.getSupplierName(),
                            null,
                            dto.getUnitPriceSale(),
                            dto.getUnitPriceBuy(),
                            dto.getExpectedProfit(),
                            dto.getProfitMargin(),
                            new ArrayList<>()
                    ),
                    colors.get(0).getItemImage(),
                    colors.get(0).getResupplyItems(),
                    colors.get(0).getSaleItems()
            ));
        }else{
            colors.forEach(color -> {
                String imgId = itemImageService.saveImage(color.getItemImage().getImage());
                color.getItemImage().setId(imgId);
                for(int i = 5; i < 12; i++) {
                    allInventory.add(new InventoryDTO(
                            dto.getItemCode() +"_SIZE_"+ i +"_"+ color.getColors(),
                            Sizes.valueOf("SIZE_"+i),
                            color.getColors(),
                            0,
                            0,
                            "not available",
                            new ItemDTO(
                                    dto.getItemCode(),
                                    dto.getDescription(),
                                    dto.getCategory(),
                                    dto.getSupplierName(),
                                    null,
                                    dto.getUnitPriceSale(),
                                    dto.getUnitPriceBuy(),
                                    dto.getExpectedProfit(),
                                    dto.getProfitMargin(),
                                    new ArrayList<>()
                            ),
                            color.getItemImage(),
                            color.getResupplyItems(),
                            color.getSaleItems()
                    ));
                }
            });
        }

//        String tempColor = "EMPTY";
//        String imgId = "";
//        String image = "";
//        for(int i = 0; i < dto.getInventoryItems().size(); i++){
//            if(!tempColor.equals(dto.getInventoryItems().get(i).getColors())){
//                imgId = itemImageService.saveImage(dto.getInventoryItems().get(i).getItemImage().getImage());
//                image = dto.getInventoryItems().get(i).getItemImage().getImage();
//                tempColor = dto.getInventoryItems().get(i).getColors();
//            }
//            dto.getInventoryItems().get(i).setInventoryCode(dto.getItemCode() +"_"+ dto.getInventoryItems().get(i).getSize().name() +"_"+ tempColor);
//            dto.getInventoryItems().get(i).setItemImage(new ItemImageDTO(imgId, image));
//            dto.getInventoryItems().get(i).setOriginalQty(0);
//            dto.getInventoryItems().get(i).setCurrentQty(0);
//            dto.getInventoryItems().get(i).setStatus("not available");
//            dto.getInventoryItems().get(i).setItem(new ItemDTO(
//                    dto.getItemCode(),
//                    dto.getDescription(),
//                    dto.getCategory(),
//                    dto.getSupplierName(),
//                    null,
//                    dto.getUnitPriceSale(),
//                    dto.getUnitPriceBuy(),
//                    dto.getExpectedProfit(),
//                    dto.getProfitMargin(),
//                    new ArrayList<>()
//            ));
//        }
        dto.setInventoryItems(allInventory);
        SupplierDTO supplierByName = supplierService.findByName(dto.getSupplierName());
        dto.setSupplier(supplierByName);
        dto.setSupplierName(supplierByName.getName());
//        System.out.println(dto.getInventoryItems());

        return itemService.save(dto);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> allItems = itemService.getAllItems();



        return allItems;
    }
}
