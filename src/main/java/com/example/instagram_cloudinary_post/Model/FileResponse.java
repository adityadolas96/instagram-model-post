package com.example.instagram_cloudinary_post.Model;

import lombok.Data;
import java.util.Map;

@Data
public class FileResponse {
    private String type;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String fileId;
    private Object tags;
    private Object AITags;
    private VersionInfo versionInfo;
    private Map<String, Object> embeddedMetadata;
    private boolean isPublished;
    private Object customCoordinates;
    private Map<String, Object> customMetadata;
    private boolean isPrivateFile;
    private String url;
    private String thumbnail;
    private String fileType;
    private String filePath;
    private int height;
    private int width;
    private long size;
    private boolean hasAlpha;
    private String mime;
    private Integer bitRate;
    private String videoCodec;
    private Integer duration;
}

@Data
class VersionInfo {
    private String id;
    private String name;
}
