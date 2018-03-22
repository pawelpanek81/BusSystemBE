package pl.bussystem.domain.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.domain.user.persistence.entity.AuthorityEntity;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
  List<AuthorityEntity> findByAccountId(Integer id);

  List<AuthorityEntity> findByAccountUsername(String username);
}
