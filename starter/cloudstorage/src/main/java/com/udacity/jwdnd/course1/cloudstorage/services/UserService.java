package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final HashService hashPass;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashPass) {
        this.userMapper = userMapper;
        this.hashPass = hashPass;
    }

    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario user = userMapper.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public Usuario register(Usuario usuario) throws Exception{
        try {
            Usuario existingUser = userMapper.findByUsername(usuario.getUsername());

            if (existingUser != null)
            {
                throw new Exception("Username already exists.");
            }

            String encodedPassword = hashPass.getHashedValue(usuario.getPassword(), usuario.getSalt());
            usuario.setPassword(encodedPassword);
            userMapper.insertUser(usuario);

        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }

}
