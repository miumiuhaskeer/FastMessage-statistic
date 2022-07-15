package com.miumiuhaskeer.fastmessage.statistic.service;

import com.miumiuhaskeer.fastmessage.statistic.model.UserDetailsImpl;
import com.miumiuhaskeer.fastmessage.statistic.model.entity.User;
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle;
import com.miumiuhaskeer.fastmessage.statistic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Class copied from FastMessage project
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(ErrorBundle.get("error.usernameNotFoundException.message"))
        );

        return UserDetailsImpl.from(user);
    }
}
