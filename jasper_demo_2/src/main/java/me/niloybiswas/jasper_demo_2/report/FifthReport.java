package me.niloybiswas.jasper_demo_2.report;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;
import me.niloybiswas.jasper_demo_2.dto.StudentDTO;
import me.niloybiswas.jasper_demo_2.dto.SubjectDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/* import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput; */

@Slf4j
public class FifthReport {
    public static void main(String[] args) {
        ///* We will implement sub report with this report */
        try {
            String masterReportFilePath = "classpath:fifth_report_with_sub_report.jrxml";
            File file = ResourceUtils.getFile(masterReportFilePath);

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
            
            ///* Adding the subreport to parameter
            parameters.put("subReport", getSubReport());
            parameters.put("subDataSource", getSubDataResource());
            parameters.put("subParameters", getSubParameters());


            JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, chartDataSource); 

            ///? in pdf
            JasperExportManager.exportReportToPdfFile(print, "reports" + File.separator + "fifth_report_with_sub_report.pdf");

           /*  ///? in html format
            JasperExportManager.exportReportToHtmlFile(print, "reports" + File.separator + "fourth_report_with_chart.html");

            ///? in Excel format
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(
                    new FileOutputStream(
                        new File("reports" + File.separator + "fourth_report_with_chart.xlsx"))));
            exporter.exportReport(); */

            System.out.println("Report Created...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exception while creating report");
        }
    }

    public static JasperReport getSubReport() {
        JasperReport report = null;

        try {
            String filePath = "classpath:first_report.jrxml";
            File file = ResourceUtils.getFile(filePath);
            report = JasperCompileManager.compileReport(file.getAbsolutePath());
        } catch (Exception e) {
            log.error("Sub-Report compile error!!!");
            e.printStackTrace();
        }
        return report;
    }

    public static JRBeanCollectionDataSource getSubDataResource() {
        Map<String, Object> parameters = new HashMap<>();
        // parameters.put("myParameter", "Hi, This is my report!");
        System.out.println(parameters);

        StudentDTO student2 = new StudentDTO(2L, "Kon", "Snow", "St Street", "London");
        StudentDTO student1 = new StudentDTO(1L, "John", "Doe", "Moghbazar", "Dhaka");

        ArrayList<StudentDTO> studentsData = new ArrayList<>();
        studentsData.add(student1);
        studentsData.add(student2);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentsData);

        return dataSource;
    }

    public static Map<String, Object> getSubParameters() {
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("myParameter", "This is Sub-Report Parameter");
        
        return parameters;
    }
}
