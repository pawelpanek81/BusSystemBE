package pl.bussystem.domain.news.mapper;

import pl.bussystem.domain.news.model.dto.CreateNewsDTO;
import pl.bussystem.domain.news.model.dto.ReadNewsAuthorDTO;
import pl.bussystem.domain.news.model.dto.ReadNewsDTO;
import pl.bussystem.domain.news.persistence.entity.NewsEntity;

import java.util.function.Function;

public class NewsMapper {

  public static Function<? super NewsEntity, ? extends ReadNewsDTO> mapToReadNewsDTO =
      entity -> new ReadNewsDTO(
          entity.getId(),
          entity.getTitle(),
          entity.getDateTime(),
          entity.getBody(),
          new ReadNewsAuthorDTO(
              entity.getAuthor().getName(),
              entity.getAuthor().getSurname(),
              entity.getAuthor().getUsername(),
              entity.getAuthor().getEmail(),
              entity.getAuthor().getPhoto(
          )));

  public static NewsEntity mapToNewsEntity(CreateNewsDTO dto) {
    return NewsEntity.builder()
        .title(dto.getTitle())
        .dateTime(dto.getDate())
        .body(dto.getBody())
        .build();
  }
}
