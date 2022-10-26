package me.niloybiswas.jasper_demo_2.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import me.niloybiswas.jasper_demo_2.dto.StudentDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FirstReport {
    public static void main(String[] args) {
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
            JasperExportManager.exportReportToPdfFile(print, "reports" + File.separator + "first_Report.pdf");

            System.out.println("Report Created...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception while creating report");
        }
    }
}
