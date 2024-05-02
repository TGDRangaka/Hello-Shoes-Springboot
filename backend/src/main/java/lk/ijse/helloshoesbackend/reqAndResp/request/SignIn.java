package lk.ijse.helloshoesbackend.reqAndResp.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignIn {
    @NotNull(message = "User credentials must be provided")
    public String email;
    @NotNull(message = "User credentials must be provided")
    public String password;
}
