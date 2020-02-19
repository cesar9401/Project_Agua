/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import object.DTO.GenericoDTO;

/**
 *
 * @author julio
 */
public class GenerarReporte {
    private JasperReport report;
    private JasperPrint reportFill;
    private JasperViewer view;
    
    public void reportWithParameter(String path, HashMap<String,Object> parametros,List<GenericoDTO> listData){
        InputStream inputStream = null;
        
        try {
            
            JRBeanCollectionDataSource list =  new JRBeanCollectionDataSource(listData);
        inputStream = this.getClass().getResourceAsStream(path);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, list);
         //JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test_jasper.pdf");
  //       JasperViewer viewer = new JasperViewer(jasperPrint, false);
    //     viewer.setVisible(true);
         
         
//            report = (JasperReport) JRLoader.loadObject(path);
//            
//            //JasperFillManager.fillReport(report, parametros,dataFields);
//            
//            
            reportFill = JasperFillManager.fillReport(jasperReport, parametros,list);
//            
            view = new JasperViewer(reportFill);
            view.setVisible(true);
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(GenerarReporte.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }finally{
        }
    }
}
