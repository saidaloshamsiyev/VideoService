package org.example.videoservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.domain.entity.VideoEntity;
import org.example.videoservice.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final Cloudinary cloudinary;

    @Override
    public VideoResponse saveVideo(MultipartFile multipartFile, VideoRequest videoRequest) {
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));

            String videoUrl = (String) uploadResult.get("secure_url");
            createVideo(videoUrl, videoRequest);

            return getResponse(videoUrl, videoRequest);
        } catch (IOException e) {
            throw new RuntimeException("Video yuklashda xatolik yuz berdi", e);
        }
    }
    @Override
    public VideoResponse getVideo(UUID videoId) {
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        return VideoResponse.builder()
                .title(videoEntity.getTitle())
                .description(videoEntity.getDescription())
                .videoUrl(videoEntity.getVideoUrl())
                .thumbnailUrl(videoEntity.getThumbnailUrl())
                .channelId(videoEntity.getChannelId())
                .build();
    }
    @Override
    public void deleteVideo(UUID videoId) {
        videoRepository.deleteById(videoId);
    }
    @Override
    public List<VideoResponse> getAllVideos() {
        List<VideoEntity> videos = videoRepository.findAll();
        List<VideoResponse> videoResponses = new ArrayList<>();
        for (VideoEntity video : videos) {
            videoResponses.add(
                    getVideoResponse(video)
            );
        }
        return videoResponses;
    }
    private static VideoResponse getVideoResponse(VideoEntity video) {
        return VideoResponse.builder()
                .title(video.getTitle())
                .description(video.getDescription())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .channelId(video.getChannelId())
                .build();
    }
    private static VideoResponse getResponse(String videoUrl, VideoRequest videoRequest) {
        return VideoResponse.builder()
                .title(videoRequest.getTitle())
                .description(videoRequest.getDescription())
                .videoUrl(videoUrl)
                .thumbnailUrl(videoRequest.getThumbnailUrl())
                .channelId(videoRequest.getChannelId())
                .build();
    }
    private void createVideo(String videoUrl, VideoRequest videoRequest) {
        VideoEntity videoEntity = VideoEntity.builder()
                .title(videoRequest.getTitle())
                .description(videoRequest.getDescription())
                .videoUrl(videoUrl)
                .channelId(videoRequest.getChannelId())
                .thumbnailUrl(videoRequest.getThumbnailUrl())
                .build();
        videoRepository.save(videoEntity);
    }
}


