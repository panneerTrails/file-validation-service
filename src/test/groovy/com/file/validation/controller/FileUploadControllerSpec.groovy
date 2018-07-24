package com.file.validation.controller

import com.file.validation.service.FileUploadService
import groovyx.net.http.RESTClient
import org.mockito.internal.util.MockUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.MultiValueMap
import org.springframework.web.multipart.MultipartFile
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadControllerSpec extends Specification {

    @LocalServerPort
    int port

    @Autowired
    FileUploadService fileUploadService;

    @Shared
    MockUtil mockUtil = new MockUtil()

    @Shared
    RESTClient restClient

    def setup() {
        restClient = new RESTClient("http://localhost:${port}")
        mockUtil.attachMock(fileUploadService, this)
    }

    def teardown() {
        mockUtil.detachMock(fileUploadService)
    }

    def "handleFileUpload_CSV_FILE"() {
        given:
        String file = this.getClass().getResource('/records.csv').getFile()
        MockMultipartFile multipartFile = new MockMultipartFile("file", file,"application/vnd.ms-excel", new FileInputStream(new File(file)));
        //MultiValueMap multiValueMap = Mock MultiValueMap() {}
        //def multipartFile = new MockMultipartFile('file', 'records.csv', 'application/vnd.ms-excel', new byte[0])

        when:
        def response = mockMvc.perform(get("/FileUpload/upload").accept(MediaType.MULTIPART_FORM_DATA_VALUE).params("file", multipartFile)).andReturn().response



        then:
        response
    }

    def "handleFileUpload_XML_FILE"() {


    }

    def "handleFileUpload_UNSUPPORTED_FILE"() {

    }

    def "handleFileUpload_EMPTY_FILE"() {

    }
}
