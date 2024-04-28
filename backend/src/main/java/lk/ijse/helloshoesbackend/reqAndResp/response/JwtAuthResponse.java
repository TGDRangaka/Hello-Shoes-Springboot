package lk.ijse.helloshoesbackend.reqAndResp.response;

import lk.ijse.helloshoesbackend.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {
    private String token;
    private EmployeeDTO user;
}
