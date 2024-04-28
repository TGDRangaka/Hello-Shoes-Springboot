package lk.ijse.helloshoesbackend.util;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.dto.SupplierDTO;
import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Conversion {

    private static ModelMapper modelMapper = new ModelMapper();

    public static EmployeeDTO toEmployeeDTO(EmployeeEntity entity){
        return modelMapper.map(entity, EmployeeDTO.class);
    }
    public static EmployeeEntity toEmployeeEntity(EmployeeDTO dto){
        return modelMapper.map(dto, EmployeeEntity.class);
    }

    public static SupplierDTO toSupplierDTO(SupplierEntity entity){
        return modelMapper.map(entity, SupplierDTO.class);
    }
    public static SupplierEntity toSupplierEntity(SupplierDTO dto){
        return modelMapper.map(dto, SupplierEntity.class);
    }
}
