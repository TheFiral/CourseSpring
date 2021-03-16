package com.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class CreateFile {

    @Value("${taskFileName}")
    private String FileName;

    @Value("${taskFileDecision}")
    private String FileDecision;

    public String createNewTaskFile(MultipartFile file) throws IOException {
        String taskFileName = null;
        if (file != null) {
            File newFile = new File(FileName);
            if (!newFile.exists()) {
                newFile.mkdir();
            }
            taskFileName = "task." +
                    new Date().toInstant().toString().replace(':', '.') +
                    "." + file.getOriginalFilename();

            file.transferTo(new File(FileName + "/" + taskFileName));
            ElectronicPrinting.print(newFile);
        }
        return taskFileName;
    }

    public String createNewDecisionFile(MultipartFile file) throws IOException {
        String taskFileName = null;
        if (file != null) {
            File newFile = new File(FileDecision);
            if (!newFile.exists()) {
                newFile.mkdir();
            }
            taskFileName = "decision." +
                    new Date().toInstant().toString().replace(':', '.') +
                    "." + file.getOriginalFilename();
            file.transferTo(new File(FileDecision + "/" + taskFileName));
            ElectronicPrinting.print(newFile);
        }
        return taskFileName;
    }

    public ResponseEntity<ByteArrayResource> downloadTaskFile(String name) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + name + "\"")
                .body(new ByteArrayResource(Files.readAllBytes(Paths.get(FileName + "/" + name))));
    }

    public ResponseEntity<ByteArrayResource> downloadDecisionFile(String name) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + name + "\"")
                .body(new ByteArrayResource(Files.readAllBytes(Paths.get(FileDecision + "/" + name))));
    }
}
