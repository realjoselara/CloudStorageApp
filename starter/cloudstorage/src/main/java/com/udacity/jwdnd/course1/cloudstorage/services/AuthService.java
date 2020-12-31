package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    @Autowired
    public AuthService(EncryptionService encryptionService, CredentialMapper credentialMapper){
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    private Credentials passwordEncryption(Credentials credential){
        String key = RandomStringUtils.random(16, true, true);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credentials decryptPassword(Credentials credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public List<Credentials> getAllCredential(Long userid) throws Exception {
        List<Credentials> Credential = credentialMapper.findByUserId(userid);
        if (Credential == null) {
            throw new Exception();
        }
        return Credential.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public void addCredential(Credentials credential, Long userid) {
        credentialMapper.insert(passwordEncryption(credential), userid);
    }

    public void updateCredential(Credentials credential) {
        credentialMapper.update(passwordEncryption(credential));
    }

    public void deleteCredential(long id, long userId) {
        credentialMapper.delete(id, userId);
    }

}
