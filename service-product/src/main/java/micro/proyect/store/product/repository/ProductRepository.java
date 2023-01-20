package micro.proyect.store.product.repository;

import micro.proyect.store.product.entity.Category;
import micro.proyect.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);
}
