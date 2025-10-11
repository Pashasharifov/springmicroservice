package az.company.ms_product.service.abstraction;

import az.company.ms_product.model.request.CreateProductRequest;
import az.company.ms_product.model.request.ReduceQuantityRequest;
import az.company.ms_product.model.response.ProductResponse;

public interface ProductService {
    void createProduct(CreateProductRequest createProductRequest);

    ProductResponse getPoductById(Long id);

    void reduceQuantity(ReduceQuantityRequest reduceQuantityRequest);
}
