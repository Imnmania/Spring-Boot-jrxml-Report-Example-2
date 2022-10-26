package me.niloybiswas.jasper_demo_2.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.niloybiswas.jasper_demo_2.dto.StudentDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @GetMapping("/report")
    public ResponseEntity<byte[]> getReport() {
        try {
            String filePath = "classpath:first_report.jrxml";
            File file = ResourceUtils.getFile(filePath);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("myParameter", "Hi, This is my report!");
            System.out.println(parameters);

            StudentDTO student2 = new StudentDTO(2L, "Kon", "Snow", "St Street", "London");
            StudentDTO student1 = new StudentDTO(1L, "John", "Doe", "Moghbazar", "Dhaka");

            ArrayList<StudentDTO> studentsData = new ArrayList<>();
            studentsData.add(student1);
            studentsData.add(student2);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentsData);
            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource); 
            // JasperExportManager.exportReportToPdfFile(print, "reports" + File.separator + "first_Report.pdf");

            byte[] byteArray = JasperExportManager.exportReportToPdf(print);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "student.pdf");
            System.out.println("Report Created...");

            return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception while creating report");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
