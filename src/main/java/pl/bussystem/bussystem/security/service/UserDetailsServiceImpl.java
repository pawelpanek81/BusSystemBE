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

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private AccountRepository accountRepository;

  @Autowired
  public UserDetailsServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity account = accountRepository.findByUsername(username);
    if (account == null) {
      throw new UsernameNotFoundException(username);
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    account.getAuthorities().forEach(acc -> authorities.add(new SimpleGrantedAuthority(acc.getAuthority())));

    return new User(account.getUsername(), account.getPassword(), authorities);
  }
}
