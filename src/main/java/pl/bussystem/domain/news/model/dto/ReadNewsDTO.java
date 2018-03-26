package pl.bussystem.domain.news.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReadNewsDTO {
  private Integer id;
  private String title;
  private LocalDateTime dateTime;
  private String body;
  private ReadNewsAuthorDTO author;
}
