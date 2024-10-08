package org.example.videoservice.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;
import metube.com.dto.BaseEntity;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "videos")
@Builder
public class VideoEntity extends BaseEntity {
      private String title;
      private String description;
      private String videoUrl;
      private String thumbnailUrl;
      private Integer views;
      private UUID commentId;
      private UUID likeId;
      private UUID channelId;
}



