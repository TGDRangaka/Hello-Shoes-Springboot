package lk.ijse.helloshoesbackend.reqAndResp.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignIn {
    @NotNull(message = "email must be provided")
    @Email(message = "must be a valid email address")
    public String email;

    @NotNull(message = "Password must be provided")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^.{6,}$", message = "Password must be at least 6 characters long")
    public String password;
}
