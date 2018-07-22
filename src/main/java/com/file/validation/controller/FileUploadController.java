package com.file.validation.controller;

import com.file.validation.exception.UnsupportedFormatException;
import com.file.validation.model.RecordDesc;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/FileUpload")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private static final String xmlFormat="text/xml";
    private static final String csvFormat="application/vnd.ms-excel";

    private static final String xmlValue = "xml";
    private static final String csvValue = "csv";

    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<RecordDesc>> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException, FileUploadException {

        List<RecordDesc> reportList = null;
        LOGGER.info("FileUploadController::handleFileUpload");

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        Map<String, String> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("msg", "Empty File:{} " + fileName);
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

        if (fileType.equalsIgnoreCase(xmlFormat)){
            reportList = fileUploadService.validateInput(file, xmlValue);
        }else if (fileType.equalsIgnoreCase(csvFormat)){
            reportList = fileUploadService.validateInput(file, csvValue);
        }else{
            throw new UnsupportedFormatException("UnsupportedFormatException");
        }

        return new ResponseEntity(reportList, HttpStatus.OK);
    }


}
