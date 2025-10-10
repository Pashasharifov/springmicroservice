package az.company.ms_product.controller;

import az.company.ms_product.model.request.CreateProductRequest;
import az.company.ms_product.model.response.ProductResponse;
import az.company.ms_product.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody CreateProductRequest createProductRequest){
        productService.createProduct(createProductRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getPoductById(id);
    }

}
