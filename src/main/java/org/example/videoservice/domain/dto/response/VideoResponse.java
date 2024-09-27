package org.example.videoservice.domain.dto.response;

import java.util.UUID;

public class VideoResponse {

    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer views;
    private UUID commentId;
    private UUID likeId;
    private UUID channelId;

}
