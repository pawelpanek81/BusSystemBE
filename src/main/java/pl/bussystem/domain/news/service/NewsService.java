package pl.bussystem.domain.news.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;

import java.security.Principal;

@Service
public interface NewsService {
  NewsEntity add(NewsEntity newsEntity, Principal principal);

  boolean remove(Integer id);

  Page<NewsEntity> findAllByPageable(Pageable pageable);
}
