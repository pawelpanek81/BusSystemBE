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
  Integer id;

  @Column(name = "title")
  String title;

  @Column(name = "date_time")
  LocalDateTime dateTime;

  @Column(name = "body")
  String body;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author")
  AccountEntity author;
}
