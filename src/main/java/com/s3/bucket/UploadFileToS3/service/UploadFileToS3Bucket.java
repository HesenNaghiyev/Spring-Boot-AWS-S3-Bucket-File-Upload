package com.s3.bucket.UploadFileToS3.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.s3.bucket.UploadFileToS3.entity.AWSObjectKey;
import com.s3.bucket.UploadFileToS3.repository.AWSFileNameRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class UploadFileToS3Bucket {


    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AWSFileNameRepository nameRepository;

    @Value("${application.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile multipartFile){
       File file =  multipartFileToFile(multipartFile);

       String fileName = multipartFile.getOriginalFilename();

       amazonS3.putObject(bucketName,fileName,file);

        AWSObjectKey objectKey = new AWSObjectKey(fileName);
        nameRepository.save(objectKey);

       file.delete();


       return fileName;

    }

    public File multipartFileToFile(MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename());

        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(multipartFile.getBytes());
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        return file;
    }


    public void getFile(String fileName, HttpServletResponse response) throws IOException {
       S3Object s3Object = amazonS3.getObject(bucketName, fileName);

      InputStream inputStream =  s3Object.getObjectContent();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


      int length;
      byte [] buffer = new byte[4096];
      while ((length=inputStream.read(buffer,0,buffer.length))!=-1){
          outputStream.write(buffer,0,length);
      }

      byte[] byteArray = new  byte[outputStream.size()];
      int i=0;
      for(Byte b : outputStream.toByteArray()){
          byteArray[i++]=b;
        }

      response.setContentType("image/jpeg");
      InputStream in = new ByteArrayInputStream(byteArray);
      IOUtils.copy(in,response.getOutputStream());

    }









}
