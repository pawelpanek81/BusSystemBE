package pl.bussystem.domain.news.persistence.entity;

import lombok.*;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsEntity {
  @Id
  @SequenceGenerator(name = "news_generator",
      sequenceName = "news_id_seq", initialValue = 5)
  @GeneratedValue(generator = "news_generator")
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "date_time", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "body", length = 65536, nullable = false)
  private String body;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author", nullable = false)
  private AccountEntity author;

}
