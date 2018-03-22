package pl.bussystem.security.email.verification.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bussystem.security.email.verification.persistence.entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Integer> {

  VerificationTokenEntity findByToken(String token);
}
