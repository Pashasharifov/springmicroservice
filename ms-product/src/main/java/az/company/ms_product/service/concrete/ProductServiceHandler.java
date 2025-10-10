package az.company.ms_product.service.concrete;

import az.company.ms_product.dao.repository.ProductRepository;
import az.company.ms_product.exception.NotFoundException;
import az.company.ms_product.mapper.ProductMapper;
import az.company.ms_product.model.enums.ErrorMessage;
import az.company.ms_product.model.request.CreateProductRequest;
import az.company.ms_product.model.response.ProductResponse;
import az.company.ms_product.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static az.company.ms_product.mapper.ProductMapper.*;
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
        return productRepository.findById(id)
                .map(PRODUCT_MAPPER::buildProductResponse)
                .orElseThrow(()-> new NotFoundException(
                        format(PRODUCT_NOT_FOUND.getMessage(), id)
                        ));
    }
}
