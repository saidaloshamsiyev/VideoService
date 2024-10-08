package org.example.videoservice.domain.dto.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
