package pl.bussystem.domain.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;
import pl.bussystem.domain.news.persistence.repository.NewsRepository;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;

import java.security.Principal;
import java.util.NoSuchElementException;

@Service
public class NewsServiceImpl implements NewsService {
  private NewsRepository newsRepository;
  private AccountRepository accountRepository;

  @Autowired
  public NewsServiceImpl(NewsRepository newsRepository,
                         AccountRepository accountRepository) {
    this.newsRepository = newsRepository;
    this.accountRepository = accountRepository;
  }

  public NewsEntity create(NewsEntity newsEntity, Principal principal) {
    AccountEntity user = accountRepository.findByUsername(principal.getName());
    newsEntity.setAuthor(user);

    return newsRepository.save(newsEntity);
  }

  @Override
  public void deleteById(Integer id) {
    try {
      newsRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("News with id: " + id + " does not exists!");
    }
  }

  @Override
  public Page<NewsEntity> readByPageable(Pageable pageable) {
    return newsRepository.findAll(pageable);
  }
}
