package me.niloybiswas.jasper_demo_2.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import me.niloybiswas.jasper_demo_2.dto.SubjectDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ThirdReport {
    public static void main(String[] args) {
        try {
            String filePath = "classpath:second_report.jrxml";
            File file = ResourceUtils.getFile(filePath);

            SubjectDTO sub1 = new SubjectDTO("Java", 80L);
            SubjectDTO sub2 = new SubjectDTO("MySQL", 70L);
            SubjectDTO sub3 = new SubjectDTO("PHP", 60L);
            SubjectDTO sub4 = new SubjectDTO("MongoDB", 40L);
            SubjectDTO sub5 = new SubjectDTO("Python", 60L);

            ArrayList<SubjectDTO> subjectData = new ArrayList<>();
            subjectData.add(sub1);
            subjectData.add(sub2);
            subjectData.add(sub3);
            subjectData.add(sub4);
            subjectData.add(sub5);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(subjectData);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentName", "John");
            parameters.put("tableData", dataSource);

            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource()); 
            JasperExportManager.exportReportToPdfFile(print, "reports" + File.separator + "third_Report.pdf");

            System.out.println("Report Created...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception while creating report");
        }
    }
}
