package pl.bussystem.security.payment.service;

import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.security.payment.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderService {
  List<OrderEntity> readByAccountEntity(AccountEntity accountEntity);
}
