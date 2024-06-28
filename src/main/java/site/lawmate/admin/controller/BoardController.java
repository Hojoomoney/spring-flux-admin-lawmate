package site.lawmate.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.dto.BoardDto;
import site.lawmate.admin.domain.model.Board;
import site.lawmate.admin.domain.model.File;
import site.lawmate.admin.service.BoardService;

import java.io.IOException;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 목록 조회
    @GetMapping("/all")
    public Flux<BoardDto> findAll() {
        return boardService.findAll();
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Board>> save(@RequestPart("boardDto") String boardDtoString,
                                            @RequestPart("files") Flux<FilePart> fileParts) {
        BoardDto boardDto = convertJsonToBoardDto(boardDtoString);
        return boardService.save(boardDto, fileParts)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Board>> findById(@PathVariable("id") String id) {
        return boardService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/download")
    public Mono<ResponseEntity<ByteArrayResource>> downloadFile(@RequestParam("id") String id, @RequestParam("fileName") String fileName) {
        return boardService.downloadFile(id, fileName);
    }



    private BoardDto convertJsonToBoardDto(String boardDtoString) {
        // JSON 문자열을 BoardDto 객체로 변환하는 로직 (ObjectMapper 사용)
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(boardDtoString, BoardDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to BoardDto", e);
        }
    }

}
