package pl.bussystem.domain.news.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.news.mapper.NewsMapper;
import pl.bussystem.domain.news.model.dto.CreateNewsDTO;
import pl.bussystem.domain.news.model.dto.ReadNewsDTO;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;
import pl.bussystem.domain.news.service.NewsService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/v1.0/news")
class NewsController {
  private NewsService newsService;

  @Autowired
  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('ROLE_BOK')")
  ResponseEntity<RestException> create(@RequestBody @Valid CreateNewsDTO dto,
                                       Principal principal) {
    NewsEntity news = NewsMapper.mapToNewsEntity(dto);

    newsService.create(news, principal);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasAuthority('ROLE_BOK')")
  ResponseEntity<RestException> deleteById(@PathVariable Integer id) {
    try {
      newsService.deleteById(id);
    } catch (NoSuchElementException e) {
      RestException restException = new RestException(
          RestExceptionCodes.NEWS_WITH_GIVEN_ID_DOES_NOT_EXISTS,
          "News with id: " + id + " does not exists"
      );
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<Page<ReadNewsDTO>> read(Pageable pageable) {
    Page<NewsEntity> allByPageable = newsService.readByPageable(pageable);

    Page<ReadNewsDTO> mappedNewsEntities = allByPageable.map(NewsMapper.mapToReadNewsDTO);

    return new ResponseEntity<>(mappedNewsEntities, HttpStatus.OK);
  }
}
