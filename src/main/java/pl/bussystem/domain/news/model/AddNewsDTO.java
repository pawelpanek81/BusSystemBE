package pl.bussystem.domain.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewsDTO {
  @NotBlank
  private String title;

  @NotNull
  private LocalDateTime date;

  @NotBlank
  private String body;
}