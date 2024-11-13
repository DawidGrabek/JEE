package com.pollub.lab_6.configuration;

import com.pollub.lab_6.dao.UserDao;
import com.pollub.lab_6.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserAuthenticationDetails implements UserDetailsService {

    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = dao.findByLogin(login);
        if (user != null) {
            List<GrantedAuthority> grupa = new ArrayList<>();
            grupa.add(new SimpleGrantedAuthority("normalUser"));
            return new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(),
                    true, true, true, true, grupa);
        } else {
            throw new UsernameNotFoundException("Zły login lub hasło.");
        }
    }
}
        
//@Component
//public class UserAuthenticationDetails implements UserDetailsService {
//    @Autowired
//    private UserDao dao;
//
//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        User user = dao.findByLogin(login);
//        if (user == null) throw new UsernameNotFoundException("Zły login lub hasło.");
//
//        List<GrantedAuthority> group = new ArrayList<>();
//        group.add(new SimpleGrantedAuthority("normalUser"));                      
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getLogin())
//                .password(user.getPassword())
//                .disabled(false)
//                .accountExpired(false)
//                .credentialsExpired(false)
//                .accountLocked(false)
//                .authorities(group)
//                .build();
//    }
//}