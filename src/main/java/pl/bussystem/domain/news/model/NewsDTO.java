package pl.bussystem.domain.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewsDTO {
  private Integer id;
  private String title;
  private LocalDateTime dateTime;
  private String body;
  private String authorName;
  private String authorSurname;
  private String authorUsername;
  private String authorEmail;
  private String authorPhoto;
}
