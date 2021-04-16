/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvmfp8fxmlcpumonitors21;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author thads
 */
public class Tvmfp8FXMLCPUMonitorS21ViewController implements Initializable {
    
    @FXML
    private AnchorPane root;
    @FXML
    private Button startStop;
    @FXML
    private Button resetRecord;
    @FXML
    private ImageView hand;
    @FXML
    private Text cpuText;
    @FXML
    private Text peakText;
    @FXML
    private Text meanText;
    @FXML
    private LineChart lineChart;
    @FXML 
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private AreaChart areaChart;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private NumberAxis yAxis2;
    
    Tvmfp8FXMLCPUMonitorS21Model model;
    @FXML
    public void startStopButton(ActionEvent event){
       if(!model.isRunning()){
           model.start();
           startStop.setText("Stop");
           resetRecord.setText("Record");
          
        
       }else{
           model.stop();
           startStop.setText("Start");
           resetRecord.setText("Reset");
       }     
    }
    @FXML
    public void resetRecordButton(ActionEvent event){
        if(!model.isRunning()){
            model.reset();
        }else{
            model.record();
        }
            
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new Tvmfp8FXMLCPUMonitorS21Model(hand,cpuText,peakText,meanText,lineChart,xAxis,yAxis,areaChart,xAxis2,yAxis2);
    }    
    
}
