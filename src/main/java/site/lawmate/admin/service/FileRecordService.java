package site.lawmate.admin.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.FileRecord;
import site.lawmate.admin.repository.FileRecordRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileRecordService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;
    private final Path root = Paths.get("admin-service/src/main/resources/uploads");

    private final FileRecordRepository fileRecordRepository;
    public Mono<String> uploadFile(FilePart filePart) {
        String folderName = "admin/";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0L);
        objectMetadata.setContentType("application/x-directory");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata);

        try {
            s3Client.putObject(putObjectRequest);
            System.out.format("Folder %s has been created.\n", folderName);
        } catch(SdkClientException e) {
            e.printStackTrace();
        }

        // upload local file
        String objectName = filePart.filename();
        String filePath= root + "/" + objectName;

        try {
            s3Client.putObject(bucketName + "/admin", objectName, new File(filePath));
            System.out.format("Object %s has been created.\n", objectName);
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
        return filePart.transferTo(Paths.get(root +"/"+ filePart.filename()))
                .then(Mono.just(filePart.filename()));
    }



    public Mono<FileRecord> save(FileRecord fileRecord) {
        return fileRecordRepository.save(fileRecord);
    }

    public Flux<DataBuffer> load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return DataBufferUtils.read(resource, new DefaultDataBufferFactory(), 4096);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
