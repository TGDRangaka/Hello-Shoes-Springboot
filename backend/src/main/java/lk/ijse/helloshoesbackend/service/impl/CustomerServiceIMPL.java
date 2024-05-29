package lk.ijse.helloshoesbackend.service.impl;

import lk.ijse.helloshoesbackend.dto.CustomerDTO;
import lk.ijse.helloshoesbackend.dto.EmailDTO;
import lk.ijse.helloshoesbackend.entity.CustomerEntity;
import lk.ijse.helloshoesbackend.entity.enums.CustomerLevel;
import lk.ijse.helloshoesbackend.exception.NotFoundException;
import lk.ijse.helloshoesbackend.repo.CustomerRepo;
import lk.ijse.helloshoesbackend.service.CustomerService;
import lk.ijse.helloshoesbackend.service.EmailService;
import lk.ijse.helloshoesbackend.util.Conversion;
import lk.ijse.helloshoesbackend.util.UtilMatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceIMPL implements CustomerService {
    private final CustomerRepo customerRepo;
    private final EmailService emailService;

    @Override
    public List<CustomerDTO> getAll() {
        log.info("Fetching all customers");
        List<CustomerDTO> customers = Conversion.toCustomerDTOList(customerRepo.findAll());
        log.debug("Fetched {} customers", customers.size());
        return customers;
    }

    @Override
    public CustomerDTO save(CustomerDTO dto) {
        log.info("Saving new customer with email: {}", dto.getEmail());
        dto.setCustomerCode(UtilMatter.generateUUID());
        dto.setLevel(CustomerLevel.NEW);
        dto.setTotalPoints(0);
        dto.setJoinedDateAsLoyalty(LocalDate.now());

        CustomerEntity savedEntity = customerRepo.save(Conversion.toCustomerEntity(dto));
        CustomerDTO savedCustomer = Conversion.toCustomerDTO(savedEntity);
        log.debug("Saved customer: {}", savedCustomer.getCustomerCode());
        return savedCustomer;
    }

    @Override
    public void update(CustomerDTO dto, String customerId) {
        log.info("Updating customer with ID: {}", customerId);
        if (customerRepo.existsById(customerId)) {
            CustomerEntity customerEntity = Conversion.toCustomerEntity(dto);
            customerEntity.setCustomerCode(customerId);
            customerRepo.save(customerEntity);
            log.debug("Updated customer with ID: {}", customerId);
            return;
        }
        log.warn("Customer not found with ID: {}", customerId);
        throw new NotFoundException("Not Found Customer : " + customerId);
    }

    @Override
    public CustomerDTO findByNameAndEmail(String name, String email) {
        log.info("Finding customer with name: {} and email: {}", name, email);
        Optional<CustomerEntity> customer = customerRepo.findByNameAndEmail(name, email);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = Conversion.toCustomerDTO(customer.get());
            log.debug("Found customer: {}", customerDTO.getCustomerCode());
            return customerDTO;
        }
        log.warn("Customer not found with name: {} and email: {}", name, email);
        return null;
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendBirthdayWishes(){
        log.info("Running scheduled task to send birthday wishes");
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        List<CustomerEntity> byBirthdayMonthAndDay = customerRepo.findByBirthdayMonthAndDay(month, day);
        for (CustomerEntity customer : byBirthdayMonthAndDay){
            String customerEmail = customer.getEmail();
            String subject = "\uD83C\uDF89 Happy Birthday from Hello Shoes! \uD83C\uDF89";
            String message = "Dear " + customer.getName() + "s," +
                    "\n\nHappy Birthday!" +
                    "\nWe hope your special day is filled with joy, laughter, and wonderful moments. We at Hello Shoes want to take a moment to celebrate you and let you know how much we appreciate your support."+
                    "Thank you for being a valued member of the Hello Shoes family. May your birthday be as wonderful and stylish as you are!\n" +
                    "\n\nBest wishes," +
                    "\nHello Shoes Pvt. Ltd";
            EmailDTO emailDTO = new EmailDTO(null, customerEmail, subject, message);

            // send emails to customers
            emailService.sendSimpleEmail(emailDTO);
        }
        log.info("Finished sending birthday wishes");
    }
}
