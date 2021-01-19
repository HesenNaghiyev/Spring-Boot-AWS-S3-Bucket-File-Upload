package com.s3.bucket.UploadFileToS3.repository;

import com.s3.bucket.UploadFileToS3.entity.AWSObjectKey;
import org.springframework.data.repository.CrudRepository;

public interface AWSFileNameRepository extends CrudRepository<AWSObjectKey, Integer> {
}
