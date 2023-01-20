package micro.proyect.store.product;

import micro.proyect.store.product.entity.Category;
import micro.proyect.store.product.entity.Product;
import micro.proyect.store.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct() {
        Product product = Product.builder()
                .name("Computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createdAt(new Date()).build();

        productRepository.save(product);

        List<Product> found = productRepository.findByCategory(product.getCategory());

        Assertions.assertThat(found.size()).isEqualTo(3);
    }
}
