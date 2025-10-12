package az.company.orders.client;

import az.company.orders.client.decoder.CustomErrorDescoder;
import az.company.orders.model.client.request.ReduceQuantityRequest;
import az.company.orders.model.client.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ms-product",
        url = "http://127.0.0.1:8080/v1/products",
        configuration = CustomErrorDescoder.class
)
public interface ProductClient {
    @PostMapping("/reduce-quantity")
    void reduceQuantity(@RequestBody ReduceQuantityRequest reduceQuantityRequest);

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id);
}
