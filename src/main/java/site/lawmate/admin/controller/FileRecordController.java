package site.lawmate.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.FileRecord;
import site.lawmate.admin.service.FileRecordService;

import java.nio.file.Paths;


@RestController
@RequestMapping("/file-record")
@Slf4j
@RequiredArgsConstructor
public class FileRecordController {


    private final FileRecordService fileRecordService;
    @PostMapping("/upload-files")
    public Mono uploadFiles(@RequestPart("files") Flux<FilePart> filePartFlux){
        FileRecord fileRecord = new FileRecord();

        return filePartFlux.flatMap(filePart ->
            filePart.transferTo(Paths.get("admin-service/src/main/resources/uploads/" + filePart.filename()))
                    .then(Mono.just(filePart.filename())))
                    .collectList()
                .flatMap(fileNames -> {
                    fileRecord.setFileNames(fileNames);
                    return fileRecordService.save(fileRecord);
                })
                .onErrorResume(Mono::error);
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Flux<DataBuffer>> getFile(@PathVariable String filename) {
        Flux<DataBuffer> file = fileRecordService.load(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
    }
}
