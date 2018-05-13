package pl.bussystem.security.payment.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
  @Id
  @Column(name = "order_id", nullable = false)
  private String orderId;

  @Column(name = "url", nullable = false, length = 5000)
  private String URL;
}
