package com.file.validation.controller;

import com.file.validation.service.FileUploadService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/FileUpload")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException, FileUploadException {
        LOGGER.info("FileUploadController:handleFileUpload start");

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        LOGGER.info("getOriginalFilename:::{}",fileName);
        LOGGER.info("getContentType:::{}", fileType);

        Map<String, String> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("msg", "Empty File:{} " + fileName);
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        if (fileType.equalsIgnoreCase("text/xml")){
            LOGGER.info("xml file");
            fileUploadService.manipulateXML(file);
        }

        if (fileType.equalsIgnoreCase("application/vnd.ms-excel")){
                fileUploadService.manipulateCSV(file);
        }

        return null;
    }


}
