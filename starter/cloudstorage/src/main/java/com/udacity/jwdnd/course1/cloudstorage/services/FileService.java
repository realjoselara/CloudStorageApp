package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FilesMapper fileMapper;

    public FileService(FilesMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getById(Long id, Long userId) {
        return fileMapper.findById(id, userId);
    }

    public List<File> getAllFilesByUserId(Long userid) throws Exception {
        List<File> files = fileMapper.findByUserId(userid);
        return files;
    }

    public File addFile(MultipartFile fileUpload, Long userid) throws IOException {

        try {
            File file = new File(fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    fileUpload.getSize(), fileUpload.getBytes());

            fileMapper.inset(file, userid);
            return file;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public int deleteFile(Long id) throws IOException  {
        return fileMapper.delete(id);
    }
}