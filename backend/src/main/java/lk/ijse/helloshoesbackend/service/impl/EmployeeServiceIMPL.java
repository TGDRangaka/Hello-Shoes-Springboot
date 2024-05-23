package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lk.ijse.helloshoesbackend.entity.EmployeeEntity;
import lk.ijse.helloshoesbackend.entity.UserEntity;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.EmployeeRepo;
import lk.ijse.helloshoesbackend.repo.UserRepo;
import lk.ijse.helloshoesbackend.service.EmployeeService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceIMPL implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepo;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employees = Conversion.toEmployeeDTOList(employeeRepo.findAll());
        log.info("Fetched {} employees", employees.size());
        return employees;
    }

    @Override
    public EmployeeDTO getEmployee(String email) {
        log.info("Fetching employee with email: {}", email);
        Optional<EmployeeEntity> byEmail = employeeRepo.findByEmail(email);
        if (byEmail.isPresent()) {
            log.info("Found employee with email: {}", email);
            return new ModelMapper().map(byEmail.get(), EmployeeDTO.class);
        }
        log.error("Employee not found with email: {}", email);
        throw new NotFoundException("Not Found Employee : " + email);
    }

    @Override
    public String saveEmployee(EmployeeDTO employee) {
        log.info("Saving employee: {}", employee);
        EmployeeEntity savedEmployee = employeeRepo.save(Conversion.toEmployeeEntity(employee));
        log.info("Saved employee with code: {}", savedEmployee.getEmployeeCode());
        return savedEmployee.getEmployeeCode();
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode) {
        log.info("Updating employee with code: {}", employeeCode);
        Optional<EmployeeEntity> byId = employeeRepo.findById(employeeCode);
        if (byId.isPresent()) {
            EmployeeEntity entity = byId.get();

            Optional<UserEntity> byEmployee = userRepo.findByEmployee(entity);
            if (byEmployee.isPresent()) {
                UserEntity userEntity = byEmployee.get();
                userEntity.setEmail(employee.getEmail());
                userRepo.save(userEntity);
                log.info("Updated user email for employee with code: {}", employeeCode);
            }

            entity.setName(employee.getName());
            entity.setProfilePic(employee.getProfilePic());
            entity.setGender(employee.getGender());
            entity.setStatus(employee.getStatus());
            entity.setDesignation(employee.getDesignation());
            entity.setDob(employee.getDob());
            entity.setJoinedDate(employee.getJoinedDate());
            entity.setBranch(employee.getBranch());
            entity.setAddressNo(employee.getAddressNo());
            entity.setAddressLane(employee.getAddressLane());
            entity.setAddressCity(employee.getAddressCity());
            entity.setAddressState(employee.getAddressState());
            entity.setPostalCode(employee.getPostalCode());
            entity.setEmail(employee.getEmail());
            entity.setPhone(employee.getPhone());
            entity.setGuardianOrNominatedPerson(employee.getGuardianOrNominatedPerson());
            entity.setEmergencyContact(employee.getEmergencyContact());

            EmployeeDTO updatedEmployee = Conversion.toEmployeeDTO(entity);
            log.info("Updated employee with code: {}", employeeCode);
            return updatedEmployee;
        }
        log.error("Employee not found with code: {}", employeeCode);
        throw new NotFoundException("Not Found Employee : " + employeeCode);
    }
}
