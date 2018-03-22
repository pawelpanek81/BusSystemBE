package pl.bussystem.domain.news.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.news.model.AddNewsDTO;
import pl.bussystem.domain.news.model.NewsDTO;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;
import pl.bussystem.domain.news.service.NewsService;
import pl.bussystem.rest.exception.RestException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1.0")
class NewsController {
  private NewsService newsService;

  @Autowired
  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @RequestMapping(method = RequestMethod.POST, path = "/auth/addNews")
  @PreAuthorize("hasAuthority('group:BOK')")
  ResponseEntity<RestException> addNews(@RequestBody @Valid AddNewsDTO addNewsDTO,
                                        Principal principal) {
    NewsEntity news = NewsEntity.builder()
        .title(addNewsDTO.getTitle())
        .dateTime(addNewsDTO.getDate())
        .body(addNewsDTO.getBody())
        .build();
    newsService.add(news, principal);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/auth/removeNews/{id}")
  @PreAuthorize("hasAuthority('group:BOK')")
  ResponseEntity<RestException> removeNews(@PathVariable Integer id) {
    if (newsService.remove(id)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/getNews")
  ResponseEntity getNews(Pageable pageable) {
    Page<NewsEntity> allByPageable = newsService.findAllByPageable(pageable);

    Page<NewsDTO> mappedNewsEntities = allByPageable.map(n -> new NewsDTO(
        n.getId(),
        n.getTitle(),
        n.getDateTime(),
        n.getBody(),
        n.getAuthor().getName(),
        n.getAuthor().getSurname(),
        n.getAuthor().getUsername(),
        n.getAuthor().getEmail(),
        n.getAuthor().getPhoto()
    ));

    return new ResponseEntity<>(mappedNewsEntities, HttpStatus.OK);
  }
}
