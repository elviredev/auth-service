package intra.poleemploi.dao;

import intra.poleemploi.entities.AppProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppProductRepository extends JpaRepository<AppProduct, Long> {
    public AppProduct findProductByProductName(String productName);
}
