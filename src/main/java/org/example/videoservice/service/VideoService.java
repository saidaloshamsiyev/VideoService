package org.example.videoservice.service;

import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;

public interface VideoService {

        VideoResponse saveVideo(VideoRequest videoRequest);
        VideoResponse getVideo(String videoId);
        void deleteVideo(String videoId);

}
