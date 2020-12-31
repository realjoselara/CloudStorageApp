package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Authenticator implements AuthenticationProvider  {

    private UserMapper userMapper;
    private CredentialMapper credentialMapper;
    private HashService hashService;

    @Autowired
    public Authenticator(CredentialMapper credentialMapper, UserMapper userMapper,HashService hashService){
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.credentialMapper = credentialMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Usuario user = userMapper.findByUsername(username);
        if(user!=null){
            String encrypt = user.getSalt();
            String hashedPass = hashService.getHashedValue(password, encrypt);
            if(user.getPassword().equals(hashedPass)){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}