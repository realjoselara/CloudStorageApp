package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FilesController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FilesController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file")
    public String saveFile(Authentication authentication, MultipartFile fileUpload) throws IOException {
        Usuario user = userService.loadUserByUsername(authentication.getName());
        File file = fileService.getByFileName(fileUpload.getOriginalFilename());

        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        if(file != null){
            return "redirect:/result?error_file_already_exist";
        }

        fileService.addFile(fileUpload, user.getUserId());
        return "redirect:/result?success";

    }

    @GetMapping("/file/delete/{id}")
    public String deleteFile(@PathVariable("id") long fileid, Authentication authentication) throws IOException {
        Usuario user = userService.loadUserByUsername(authentication.getName());
        if(user != null && fileid > 0) {
            fileService.deleteFile(fileid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }

    @GetMapping(value = "/file/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<Object> downloadFile(@PathVariable("id") long fileid, Authentication authentication) {
        String message = "";
        Usuario user = userService.loadUserByUsername(authentication.getName());
        File file = fileService.getById(fileid, user.getUserId());
        try {

            if (file != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format(
                        "attachment; filename=%s", file.getFileName()));
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                ByteArrayResource resource = new ByteArrayResource(file.getFileData());
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(file.getContentType()))
                        .contentLength(file.getFileSize())
                        .body(resource);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        catch (Exception e){
            message = "FAIL to upload " + file.getFileName() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }
}
