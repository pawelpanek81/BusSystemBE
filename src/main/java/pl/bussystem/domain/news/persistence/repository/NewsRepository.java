package pl.bussystem.domain.news.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;

@Repository
interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
}
