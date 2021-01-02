package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    @Autowired
    public AuthService(EncryptionService encryptionService, CredentialMapper credentialMapper){
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    private Credential passwordEncryption(Credential credential){
        String key = RandomStringUtils.random(16, true, true);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credential decryptPassword(Credential credential) {
        credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public Credential getCredential(long credentialId){
        return credentialMapper.findOne(credentialId);
    }

    public Credential getCredentialByUsername(String username){
        return credentialMapper.findCredentialByUsername(username);
    }

    public List<Credential> getAllCredential(Long userid) throws Exception {
        List<Credential> credentials = credentialMapper.findByUserId(userid);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials;
    }


    public void addCredential(Credential credential, Long userid) {
        credentialMapper.insert(passwordEncryption(credential), userid);
    }

    public void updateCredential(Credential credential, Long userid) {
        credentialMapper.update(passwordEncryption(credential), userid);
    }

    public void deleteCredential(long id, long userId) {
        credentialMapper.delete(id, userId);
    }

}
