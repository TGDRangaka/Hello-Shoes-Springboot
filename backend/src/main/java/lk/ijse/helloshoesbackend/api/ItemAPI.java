package lk.ijse.helloshoesbackend.api;

import lk.ijse.helloshoesbackend.bo.ItemBO;
import lk.ijse.helloshoesbackend.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
@RequiredArgsConstructor
public class ItemAPI {
    private final ItemBO itemBO;

    @GetMapping("/health")
    public String healthCheck(){
        return "Item Health Good";
    }

    @PostMapping
    public boolean saveItem(@RequestBody ItemDTO dto){
//        System.out.println(dto);
        return itemBO.saveItem(dto);
    }
}
