package pl.bussystem.domain.news.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadNewsAuthorDTO {
  private String authorName;
  private String authorSurname;
  private String authorUsername;
  private String authorEmail;
  private String authorPhoto;
}
