package az.company.orders.client.decoder;

import az.company.orders.exception.CustomFeignException;
import az.company.orders.model.enums.ErrorMessage;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

import static az.company.orders.model.enums.ErrorMessage.CLIENT_ERROR;
import static az.company.orders.model.enums.ErrorMessage.SERVER_ERROR;

public class CustomErrorDescoder implements ErrorDecoder {
    private final ErrorDecoder defaulErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        String body = null;
        try {
            if(response.body() != null){
                body = new String(response.body().asInputStream().readAllBytes());
            }
        } catch (Exception e) {
            body = "Couldn't read response";
        }
        if (response.status() >= 400 && response.status() <= 499){
            return new CustomFeignException(CLIENT_ERROR.getMessage());
        }
        if (response.status() >= 500 && response.status() <= 599){
            return new CustomFeignException(
                    "SERVER ERROR | status=" + response.status() + " | body=" + body
            );
        }
        return defaulErrorDecoder.decode(s, response);
    }
}
