package pl.bussystem.domain.news.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class AddNewsDTO {
  @NotNull
  @Size(min = 1)
  private String title;

  @NotNull
  private LocalDateTime date;

  @NotNull
  @Size(min = 1)
  private String body;
}