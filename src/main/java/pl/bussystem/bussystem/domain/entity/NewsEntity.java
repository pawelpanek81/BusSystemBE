package pl.bussystem.bussystem.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "date_time")
  private LocalDateTime dateTime;

  @Column(name = "body")
  private String body;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author")
  private AccountEntity author;
}
