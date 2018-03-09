package pl.bussystem.bussystem.security.email.verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.security.email.verification.entity.VerificationTokenEntity;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Integer> {

  VerificationTokenEntity findByToken(String token);
}
