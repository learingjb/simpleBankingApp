package com.simpleBankingApp.service.impl;

import com.simpleBankingApp.model.database.User;
import com.simpleBankingApp.model.security.UserAccessDetails;
import com.simpleBankingApp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service()
public class UserAccessDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserAccessDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("fetching user access details");
        User user = userRepository.findOneByUserName(s);
        if (user != null) {
            return new UserAccessDetails(user);
        } else {
            logger.error("unable to fetch details, user not available");
            throw new UsernameNotFoundException("User not available");
        }
    }
}
