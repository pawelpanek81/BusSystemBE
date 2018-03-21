package pl.bussystem.domain.news.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
  Page<NewsEntity> findAll(Pageable pageable);
}
