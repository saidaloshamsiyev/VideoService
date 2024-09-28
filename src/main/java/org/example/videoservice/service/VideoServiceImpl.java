package org.example.videoservice.service;

import lombok.RequiredArgsConstructor;
import org.example.videoservice.clients.ChannelServiceClient;
import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.ChannelResponse;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.domain.entity.VideoEntity;
import org.example.videoservice.exception.BaseException;
import org.example.videoservice.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    @Value("${vimeo.acces.token}")
    private String vimeoAccessToken;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String VIMEO_UPLOAD_URL = "https://v1.nocodeapi.com/azizbekismailov/vimeo/hWGHxuzuMsvhvWAR";
    private final ChannelServiceClient client;

    @Override
    public VideoResponse saveVideo(MultipartFile videoFile, VideoRequest videoRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(vimeoAccessToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file_data", videoFile.getResource());
        body.add("name", videoRequest.getTitle());
        body.add("description", videoRequest.getDescription());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<VideoResponse> exchange = restTemplate.exchange(VIMEO_UPLOAD_URL, HttpMethod.POST, request, VideoResponse.class);
        save(videoRequest);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange.getBody();
        } else {
            throw new BaseException("Video upload failed with status: " + exchange.getStatusCode());
        }
    }

    private void save(VideoRequest videoRequest) {
        ChannelResponse channelById = client.getChannelById(videoRequest.getChannelId());
        if (channelById == null) {
            throw new BaseException("Channel not found");
        }
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(videoRequest.getTitle());
        videoEntity.setDescription(videoRequest.getDescription());
        videoEntity.setThumbnailUrl(videoRequest.getThumbnailUrl());
        videoEntity.setVideoUrl(videoRequest.getVideoUrl());
        videoEntity.setChannelId(videoRequest.getChannelId());
        videoRepository.save(videoEntity);
    }

    @Override
    public VideoResponse getVideo(UUID videoId) {
        String url = "https://api.vimeo.com/videos/" + videoId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + vimeoAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<VideoResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, VideoResponse.class);
        return response.getBody();
    }

    @Override
    public void deleteVideo(UUID videoId) {
        String url = "https://api.vimeo.com/videos/" + videoId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + vimeoAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new BaseException("Failed to delete video with status: " + response.getStatusCode());
        }
    }

    @Override
    public List<VideoResponse> getAllVideos() {
        String url = "https://api.vimeo.com/videos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + vimeoAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<VideoResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, VideoResponse[].class);

        return Arrays.asList(response.getBody());
    }
}
