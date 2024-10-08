package org.example.videoservice.service;

import lombok.RequiredArgsConstructor;
import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public VideoResponse saveVideo(VideoRequest videoRequest) {
        return null;
    }

    @Override
    public VideoResponse getVideo(String videoId) {
        return null;
    }

    @Override
    public void deleteVideo(String videoId) {

    }
}
