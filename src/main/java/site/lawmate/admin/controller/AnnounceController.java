package site.lawmate.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lawmate.admin.service.AnnounceService;
import site.lawmate.admin.service.FileRecordService;

@RestController
@RequestMapping("/announce")
@RequiredArgsConstructor
public class AnnounceController {

    private final FileRecordService fileRecordService;
    private final AnnounceService announceService;

}
