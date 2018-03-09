package pl.bussystem.bussystem.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.domain.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;

  @Autowired
  public UserDetailsServiceImpl(AccountRepository accountRepository,
                                AuthorityRepository authorityRepository) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity account = accountRepository.findByUsername(username);
    if (account == null) {
      throw new UsernameNotFoundException(username);
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorityRepository.findByAccountId(account.getId()).forEach(acc -> authorities.add(new SimpleGrantedAuthority(acc.getAuthority())));

    return new User(account.getUsername(), account.getPassword(), authorities);
  }
}
