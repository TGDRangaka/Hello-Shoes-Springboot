package lk.ijse.helloshoesbackend.exception;

public class RefundTimeExceededException extends RuntimeException {
    public RefundTimeExceededException(String message) {
        super(message);
    }
}
