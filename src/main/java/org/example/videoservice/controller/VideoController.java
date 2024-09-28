package org.example.videoservice.controller;

import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private VideoService videoService;

    @PostMapping("/save")
    public VideoResponse saveVideo(@RequestBody VideoRequest videoRequest, @RequestParam MultipartFile multipartFile) {
        return videoService.saveVideo(multipartFile, videoRequest);
    }

    @GetMapping("/delete/{id}")
    public void deleteVideo(@PathVariable UUID id) {
        videoService.deleteVideo(id);
    }

    @GetMapping("/get-video/{id}")
    public VideoResponse getVideo(@PathVariable UUID id) {
        return videoService.getVideo(id);
    }

    @GetMapping("/get-all-videos")
    public List<VideoResponse> getAllVideos() {
        return videoService.getAllVideos();
    }

}


