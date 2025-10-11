package az.company.ms_product.service.concrete;

import az.company.ms_product.dao.entity.ProductEntity;
import az.company.ms_product.dao.repository.ProductRepository;
import az.company.ms_product.exception.InsufficientQuantityException;
import az.company.ms_product.exception.NotFoundException;
import az.company.ms_product.model.request.CreateProductRequest;
import az.company.ms_product.model.request.ReduceQuantityRequest;
import az.company.ms_product.model.response.ProductResponse;
import az.company.ms_product.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;

import static az.company.ms_product.mapper.ProductMapper.*;
import static az.company.ms_product.model.enums.ErrorMessage.INSUFFICIENT_QUANTITY;
import static az.company.ms_product.model.enums.ErrorMessage.PRODUCT_NOT_FOUND;
import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class ProductServiceHandler implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void createProduct(CreateProductRequest createProductRequest){
        productRepository.save(PRODUCT_MAPPER.buildProductEntity(createProductRequest));
    }

    @Override
    public ProductResponse getPoductById(Long id) {
        return fetchProduct(id);
    }

    @Override
    public void reduceQuantity(ReduceQuantityRequest reduceQuantityRequest) {
        var productEntity = productRepository.findById(reduceQuantityRequest.getProductId())
                .orElseThrow(() -> new NotFoundException(
                        format(PRODUCT_NOT_FOUND.getMessage(), reduceQuantityRequest.getProductId())
                ));
        if (productEntity.getQuantity() < reduceQuantityRequest.getQuantity()){
            throw new InsufficientQuantityException(
                    format(INSUFFICIENT_QUANTITY.getMessage(), productEntity.getId())
            );
        }
        productEntity.setQuantity(productEntity.getQuantity()-reduceQuantityRequest.getQuantity());
        productRepository.save(productEntity);
    }

    private ProductResponse fetchProduct(Long id) {
        return productRepository.findById(id)
                .map(PRODUCT_MAPPER::buildProductResponse)
                .orElseThrow(() -> new NotFoundException(
                        format(PRODUCT_NOT_FOUND.getMessage(), id)
                ));
    }
}
