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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final UserRepo userRepo;

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

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employee, String employeeCode) {
        Optional<EmployeeEntity> byId = employeeRepo.findById(employeeCode);
        if(byId.isPresent()){
            EmployeeEntity entity = byId.get();

            Optional<UserEntity> byEmployee = userRepo.findByEmployee(entity);
            if(byEmployee.isPresent()){
                UserEntity userEntity = byEmployee.get();
                userEntity.setEmail(employee.getEmail());
                userRepo.save(userEntity);
            }else{
                throw new NotFoundException("Not Found User : " + employeeCode);
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

            return Conversion.toEmployeeDTO(entity);
        }
        throw new NotFoundException("Not Found Employee : " + employeeCode);
    }
}
