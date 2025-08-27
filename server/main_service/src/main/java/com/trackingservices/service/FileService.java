package com.trackingservices.service;

import com.trackingservices.model.FileEntity;
import com.trackingservices.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    String folderPath="D:\\ATS\\backend\\FileStorage\\";

    public String uploadFile(MultipartFile requestfile,Long id)throws IOException{

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = requestfile.getOriginalFilename();
        String nfilePath = folderPath + fileName;
        requestfile.transferTo(new File(nfilePath));
        String fileType=requestfile.getContentType();

        FileEntity fileData = FileEntity.builder()
                .userId(id)
                .filePath(nfilePath)
                .fileType(fileType)
                .build();
        fileRepository.save(fileData);
        if(fileData !=null){
            return "file uploaded successfully"+nfilePath;
        }
        return "File is not uploaded successfully";
    }

    public byte[] downloadFile(Long userId) throws IOException {

        Optional<FileEntity> fileEntityOptional = fileRepository.findByUserId(userId);
        if (fileEntityOptional.isPresent()) {
            String filePath=fileEntityOptional.get().getFilePath();
            byte[] data= Files.readAllBytes(new File(filePath).toPath());
            return data;
        } else {
            throw new EntityNotFoundException("File entity not found for user ID: " + userId);
        }

    }

    public String getFileType(Long id) throws IOException {
        Optional<FileEntity> fileEn = fileRepository.findByUserId(id);
        if (fileEn.isPresent()) {
            return fileEn.get().getFileType();

        }
        else{
            throw new EntityNotFoundException("File entity not found for user ID: " + id);
        }
    }
    public String getPath(Long id) {
        Optional<FileEntity> file=fileRepository.findByUserId(id);
        if (file.isPresent()) {
            String path = file.get().getFilePath();
            return path;

        }
        else{
            throw new EntityNotFoundException("File entity not found for user ID: " + id);
        }


    }
}