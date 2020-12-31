package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT * FROM USERS")
    List<Usuario> findAll();

    @Insert("INSERT INTO USERS (username, password, salt, firstname, lastname) VALUES (#{username}, #{password}, #{salt}, #{firstname}, #{lastname})")
    public int insertUser(Usuario usuario);

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    public int deleteUser(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    public Usuario findOne(int userid);

    @Update("UPDATE USERS SET username = #{username}, password = #{password}, salt = #{salt}, firstname = #{firstname}, lastname = #{lastname} WHERE userid = #{userid}")
    public int updateUser(Usuario usuario);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    public Usuario findByUsername(String username);
}
