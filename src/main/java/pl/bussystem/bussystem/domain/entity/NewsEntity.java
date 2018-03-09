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
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "date_time", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "body", nullable = false)
  private String body;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author", nullable = false)
  private AccountEntity author;
}
