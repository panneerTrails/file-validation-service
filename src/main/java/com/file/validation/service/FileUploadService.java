package com.file.validation.service;

import com.file.validation.controller.FileUploadController;
import com.file.validation.model.RecordDesc;
import com.file.validation.model.Records;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    public List<RecordDesc> manipulateCSV(MultipartFile file) throws IOException{
        LOGGER.info("FileUploadService:::manipulateCSV:::");

        List<RecordDesc> recordDescCSVList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while((line = reader.readLine()) != null) {
                if(firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                String refNo = validateNullCheck(parts[0]);
                String accountNo = validateNullCheck(parts[1]);
                String desc = validateNullCheck(parts[2]);
                String startBalance = validateNullCheck(parts[3]);
                String mutation = validateNullCheck(parts[4]);
                String endBalance = validateNullCheck(parts[5]);

                RecordDesc recordDesc = new RecordDesc();
                recordDesc.setReference(Long.parseLong(refNo));
                recordDesc.setAccountNumber(accountNo);
                recordDesc.setDescription(desc);
                recordDesc.setStartBalance(Double.parseDouble(startBalance));
                recordDesc.setMutation(Double.parseDouble(mutation));
                recordDesc.setEndBalance(Double.parseDouble(endBalance));
                LOGGER.info("FileUploadService:::manipulateCSV:::recordDesc::{}", recordDesc);
                recordDescCSVList.add(recordDesc);
            }
        }
        return recordDescCSVList;
    }

    public List<Records> manipulateXML(MultipartFile file) throws IOException{
        LOGGER.info("FileUploadService:::manipulateXML:::");
        List<Records> recordDescXMLList = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Records records = (Records) unmarshaller.unmarshal(file.getInputStream());
            LOGGER.info("FileUploadService:::manipulateXML:::records::{}", records);
            recordDescXMLList.add(records);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return recordDescXMLList;
    }

    private String validateNullCheck(String param){
        return param == null?"":param;
    }
}
