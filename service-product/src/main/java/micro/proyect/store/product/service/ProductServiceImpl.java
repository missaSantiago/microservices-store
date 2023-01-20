package micro.proyect.store.product.service;

import lombok.RequiredArgsConstructor;
import micro.proyect.store.product.entity.Category;
import micro.proyect.store.product.entity.Product;
import micro.proyect.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreatedAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productInDB = getProduct(product.getId());
        if (null == productInDB) {
            return null;
        }

        productInDB.setName(product.getName());
        productInDB.setDescription(product.getDescription());
        productInDB.setCategory(product.getCategory());
        productInDB.setPrice(product.getPrice());

        return productRepository.save(productInDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productInDB = getProduct(id);
        if (null == productInDB) {
            return null;
        }

        productInDB.setStatus("DELETED");

        return productRepository.save(productInDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productInDB = getProduct(id);
        if (null == productInDB) {
            return null;
        }

        Double stock = productInDB.getStock() + quantity;
        productInDB.setStock(stock);

        return productRepository.save(productInDB);
    }
}
