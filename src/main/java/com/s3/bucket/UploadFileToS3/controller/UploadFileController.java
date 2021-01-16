package com.s3.bucket.UploadFileToS3.controller;


import com.s3.bucket.UploadFileToS3.service.UploadFileToS3Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadFileController {

    @Autowired
    private UploadFileToS3Bucket s3Bucket;

    @GetMapping("/")
    public String index() {
        return "upload";
    }


    @PostMapping("/file")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file, Model model){
      String fileName =  s3Bucket.uploadFile(file);
      model.addAttribute("fileName", fileName);
      return "upload";
        }


    }

