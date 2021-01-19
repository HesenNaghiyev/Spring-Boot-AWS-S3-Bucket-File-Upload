package com.s3.bucket.UploadFileToS3.controller;


import com.s3.bucket.UploadFileToS3.entity.AWSObjectKey;
import com.s3.bucket.UploadFileToS3.repository.AWSFileNameRepository;
import com.s3.bucket.UploadFileToS3.service.UploadFileToS3Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/upload")
public class UploadFileController {

    @Autowired
    private UploadFileToS3Bucket s3Bucket;

    @Autowired
    private AWSFileNameRepository repository;

    @GetMapping("/")
    public String index() {
        return "upload";
    }


    @PostMapping("/file")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file, Model model){
      String fileName =  s3Bucket.uploadFile(file);
     Iterable<AWSObjectKey> keys = repository.findAll();
      model.addAttribute("fileName", fileName);
      model.addAttribute("keys", keys);
      return "upload";
        }



        @GetMapping("/get/{keyName}")
       public void getObjectKey(@PathVariable String keyName, HttpServletResponse response) throws IOException {
        s3Bucket.getFile(keyName,response);
        }

    }

