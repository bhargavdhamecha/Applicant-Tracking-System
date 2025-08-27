package com.trackingservices.service;

import com.trackingservices.model.ImageEntity;
import com.trackingservices.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    String folderPath = "D:\\ATS\\backend\\FileStorage\\";

    public String uploadImage(MultipartFile requestfile, Long id) throws IOException {

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = requestfile.getOriginalFilename();
        String nfilePath = folderPath + fileName;
        System.out.println(nfilePath);
        requestfile.transferTo(new File(nfilePath));
        String filetype = requestfile.getContentType();

        ImageEntity imageData = ImageEntity.builder()
                .imageId(id)
                .filepath(nfilePath)
                .fileType(filetype)
                .userId(id)
                .build();
        imageRepository.save(imageData);
        if (imageData != null) {
            return "file uploaded successfully" + requestfile.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(Long userId) throws IOException {

        Optional<ImageEntity> imageEntityOptional = imageRepository.findByUserId(userId);
        if (imageEntityOptional.isPresent()) {
            String filePath = imageEntityOptional.get().getFilepath();
            byte[] data = Files.readAllBytes(new File(filePath).toPath());
            return data;
        } else {
            throw new EntityNotFoundException("File entity not found for user ID: " + userId);
        }

    }

    public String getFileType(Long id) throws IOException {
        Optional<ImageEntity> imageEn = imageRepository.findByUserId(id);
        if (imageEn.isPresent()) {
            String type = imageEn.get().getFileType();
            return type;

        }
        else{
            throw new EntityNotFoundException("File entity not found for user ID: " + id);
        }
    }

    public String getPath(Long id) throws IOException {
        Optional<ImageEntity> file=imageRepository.findByUserId(id);
        if (file.isPresent()) {
            String path = file.get().getFilepath();
            return path;

        }
        else{
            throw new EntityNotFoundException("File entity not found for user ID: " + id);
        }


    }
}