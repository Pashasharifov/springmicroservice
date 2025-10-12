package az.company.orders.exception;

public class CustomFeignException extends RuntimeException {
    public CustomFeignException(String message){
        super(message);
    }
}
