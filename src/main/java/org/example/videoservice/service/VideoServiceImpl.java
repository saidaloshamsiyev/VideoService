package org.example.videoservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.domain.entity.VideoEntity;
import org.example.videoservice.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    @Override
    @Transactional
    public VideoResponse saveVideo(VideoRequest videoRequest, MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String UPLOAD_DIR = "C:\\metube\\VideoService\\src\\main\\resources";
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save video file", e);
        }
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(videoRequest.getTitle());
        videoEntity.setDescription(videoRequest.getDescription());
        videoEntity.setVideoUrl(generateUniqueVideoUrl());
        videoEntity.setChannelId(videoRequest.getChannelId());
        videoEntity.setViews(0);
        VideoEntity savedVideo = videoRepository.save(videoEntity);
        return mapToVideoResponse(savedVideo);
    }

    @Override
    public VideoResponse getVideo(UUID videoId) {
        Optional<VideoEntity> videoEntityOptional = videoRepository.findById(videoId);
        if (videoEntityOptional.isPresent()) {
            return mapToVideoResponse(videoEntityOptional.get());
        } else {
            throw new RuntimeException("Video not found");
        }    }

    @Override
    public void deleteVideo(UUID videoId) {
        videoRepository.deleteById(videoId);
    }
    private String generateUniqueVideoUrl() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return "https://youtube.com/shorts/" + uniqueId;
    }
    private VideoResponse mapToVideoResponse(VideoEntity videoEntity) {
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setTitle(videoEntity.getTitle());
        videoResponse.setDescription(videoEntity.getDescription());
        videoResponse.setVideoUrl(videoEntity.getVideoUrl());
        videoResponse.setViews(videoEntity.getViews());
        videoResponse.setCommentId(videoEntity.getCommentId());
        videoResponse.setLikeId(videoEntity.getLikeId());
        videoResponse.setChannelId(videoEntity.getChannelId());

        return videoResponse;
    }





}
