package com.file.validation.service;

import com.file.validation.controller.FileUploadController;
import com.file.validation.model.RecordDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    public List<RecordDesc> manipulateCSV(MultipartFile file) throws IOException{
        LOGGER.info("FileUploadService:::manipulateCSV:::");
        List<RecordDesc> recordDescList = new ArrayList<>();
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
                LOGGER.info("FileUploadService:::recordDesc::{}", recordDesc);
                recordDescList.add(recordDesc);
            }
        }
        return recordDescList;
    }

    public String manipulateXML(MultipartFile file) throws IOException{
        try {
//            InputStream is = file.getInputStream();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

           /* int ch;
            StringBuilder sb = new StringBuilder();
            while((ch= is.read()) !=-1)
                sb.append((char)ch);

            LOGGER.info("manipulateXML:::sb::{}", sb.toString());*/

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
            Document doc = dBuilder.parse(bis);
            //Document doc = dBuilder.parse("C:\\Use\\Others\\archive\\assignment\\records.xml");
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("records");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    LOGGER.info("FileUploadService:::recordDesc::{}", eElement.getAttribute("record"));
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String validateNullCheck(String param){
        return param == null?"":param;
    }
}
