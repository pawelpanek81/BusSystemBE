package pl.bussystem.domain.news.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;

import java.security.Principal;

@Service
public interface NewsService {
  NewsEntity create(NewsEntity newsEntity, Principal principal);

  Page<NewsEntity> readByPageable(Pageable pageable);

  void deleteById(Integer id);
}
