package egar.egartask.security;

import egar.egartask.entites.Consumer;
import egar.egartask.repository.ConsumerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ConsumerRepository consumerRepository;

    public MyUserDetailsService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Consumer consumer = consumerRepository.findByLoginIgnoreCase(login);
        if (consumer == null) {
            throw new UsernameNotFoundException(login + "not found");
        }
        return User.builder()
                .username(login)
                .password(consumer.getPassword())
                .roles(consumer.getRole().name())
                .build();
    }
}
