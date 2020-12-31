package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Mapper
@Repository
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> findAll();

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public Credentials findOne(int id);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credentials> findByUserId(Long userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{credential.url}, #{credential.username}, #{credential.key}, #{credential.password}, #{userid})")
    public int insert(Credentials credential, Long userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    public int delete(Long credentialid, Long userid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    public int update(Credentials credential);
}
