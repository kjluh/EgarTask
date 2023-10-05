package egar.egartask.security;

import egar.egartask.dto.mapper.ConsumerMapper;
import egar.egartask.entites.Consumer;
import egar.egartask.repository.ConsumerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ConsumerRepository consumerRepository;
    private final MyUserDetails myUserDetails;

    public MyUserDetailsService(ConsumerRepository consumerRepository, MyUserDetails myUserDetails) {
        this.consumerRepository = consumerRepository;
        this.myUserDetails = myUserDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Consumer consumer = consumerRepository.findByLoginIgnoreCase(login);
        if (consumer == null) {
            throw new UsernameNotFoundException(login + "not found");
        }
        myUserDetails.setConsumer(consumer);
        return myUserDetails;
    }
}
