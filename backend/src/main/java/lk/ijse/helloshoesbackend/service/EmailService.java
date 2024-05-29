package lk.ijse.helloshoesbackend.service;

import lk.ijse.helloshoesbackend.dto.EmailDTO;

public interface EmailService {
    void sendSimpleEmail(EmailDTO email);
}
