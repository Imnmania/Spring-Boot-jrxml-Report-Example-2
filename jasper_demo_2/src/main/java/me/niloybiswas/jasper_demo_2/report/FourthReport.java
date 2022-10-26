package me.niloybiswas.jasper_demo_2.report;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import me.niloybiswas.jasper_demo_2.dto.SubjectDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class FourthReport {
    public static void main(String[] args) {
        try {
            String filePath = "classpath:fourth_report_example.jrxml";
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
            JRBeanCollectionDataSource chartDataSource = new JRBeanCollectionDataSource(subjectData);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("studentName", "John");
            parameters.put("tableData", dataSource);

            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, chartDataSource); 

            ///? in pdf
            JasperExportManager.exportReportToPdfFile(print, "reports" + File.separator + "fourth_report_with_chart.pdf");

            ///? in html format
            JasperExportManager.exportReportToHtmlFile(print, "reports" + File.separator + "fourth_report_with_chart.html");

            ///? in Excel format
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(
                    new FileOutputStream(
                        new File("reports" + File.separator + "fourth_report_with_chart.xlsx"))));
            exporter.exportReport();

            System.out.println("Report Created...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception while creating report");
        }
    }
}
