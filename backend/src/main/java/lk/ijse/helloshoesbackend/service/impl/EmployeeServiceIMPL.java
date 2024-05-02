package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return Conversion.toEmployeeDTOList(employeeRepo.findAll());
    }

    @Override
    public EmployeeDTO getEmployee(String email) {
        Optional<EmployeeEntity> byEmail = employeeRepo.findByEmail(email);
        if(byEmail.isPresent()){
            return new ModelMapper().map(byEmail.get(), EmployeeDTO.class);
        }
        throw new NotFoundException("Not Found Employee : " + email);
    }

    @Override
    public String saveEmployee(EmployeeDTO employee) {
        EmployeeEntity save = employeeRepo.save(Conversion.toEmployeeEntity(employee));
        return save.getEmployeeCode();
    }
}
