package org.example.videoservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.videoservice.domain.dto.requests.VideoRequest;
import org.example.videoservice.domain.dto.response.VideoResponse;
import org.example.videoservice.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@RequestPart ("jsonData")VideoRequest videoRequest,
                                                     @RequestPart("video") MultipartFile videoFile) {
        VideoResponse savedVideo = videoService.saveVideo(videoRequest,videoFile);
        return new ResponseEntity<>(savedVideo, HttpStatus.CREATED);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponse> getVideo(@PathVariable UUID videoId) {
        VideoResponse videoResponse = videoService.getVideo(videoId);
        return ResponseEntity.ok(videoResponse);
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID videoId) {
        videoService.deleteVideo(videoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
