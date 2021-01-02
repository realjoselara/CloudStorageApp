package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Mapper
@Repository
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> findAll();

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public Credential findOne(long credentialid);


    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    public Credential findCredentialByUsername(String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    public List<Credential> findByUserId(Long userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{credential.url}, #{credential.username}, #{credential.key}, #{credential.password}, #{userid})")
    public int insert(Credential credential, long userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    public int delete(Long credentialid, long userid);

    @Update("UPDATE CREDENTIALS SET url = #{credential.url}, username = #{credential.username}, key = #{credential.key}, password = #{credential.password} WHERE credentialid = #{credential.credentialId} AND userid = #{userid}")
    public int update(Credential credential, long userid);
}
