package andry.hais.shopapi.repository;

import andry.hais.shopapi.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsRepository extends JpaRepository<News, String> {
    News findByNewsId(Long newsId);

    Page<News> findAll(Pageable pageable);
}
