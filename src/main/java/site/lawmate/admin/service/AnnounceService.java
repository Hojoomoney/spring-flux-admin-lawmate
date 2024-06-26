package site.lawmate.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.lawmate.admin.repository.AnnounceRepository;
import site.lawmate.admin.repository.FileRecordRepository;

@Service
@RequiredArgsConstructor
public class AnnounceService {

    private final FileRecordRepository fileRecordRepository;
    private final AnnounceRepository announceRepository;
}
