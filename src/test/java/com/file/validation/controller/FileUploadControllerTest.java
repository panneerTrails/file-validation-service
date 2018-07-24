package com.file.validation.controller;

import com.file.validation.model.RecordDesc;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wContext;

    @MockBean
    private FileUploadController fileUploadController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void test() throws Exception {


    }


}
