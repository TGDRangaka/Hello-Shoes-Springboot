package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> employeeRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public void saveUser(EmployeeDTO dto) {
        employeeRepo.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public EmployeeDTO getEmployee(String email) {
        EmployeeEntity entity = employeeRepo.findByEmail(email).get();
        return new ModelMapper().map(entity, EmployeeDTO.class);
    }
}
