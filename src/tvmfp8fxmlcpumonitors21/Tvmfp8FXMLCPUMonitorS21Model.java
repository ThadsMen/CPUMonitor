/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tvmfp8fxmlcpumonitors21;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author thads
 */
public class Tvmfp8FXMLCPUMonitorS21Model {
    
    private Timeline timeline;
    private KeyFrame keyFrame;
    private ImageView hand;
    private Text cpuText;
    private Text peakText;
    private Text meanText;
    private LineChart lineChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private AreaChart areaChart;
    private CategoryAxis xAxis2;
    private NumberAxis yAxis2;
    
    private double peak = 0;
    private int counter = 0;
    private int recordCounter=0;
    private double total = 0;
    private double mean = 0;
   

    XYChart.Series series=new XYChart.Series();
    XYChart.Series series2=new XYChart.Series(); 
    DecimalFormat df = new DecimalFormat("##.##");
    
    public Tvmfp8FXMLCPUMonitorS21Model(ImageView hand, Text cpuText, 
            Text peakText,Text meanText,LineChart lineChart,CategoryAxis xAxis, 
            NumberAxis yAxis,AreaChart areaChart,CategoryAxis xAxis2, NumberAxis yAxis2){
        setUpTimer();
        this.hand=hand;
        this.cpuText=cpuText;
        this.peakText=peakText;
        this.meanText=meanText;
        this.lineChart=lineChart;
        this.xAxis=xAxis;
        this.yAxis=yAxis;
        this.areaChart=areaChart;
        this.xAxis2=xAxis2;
        this.yAxis2=yAxis2;
        lineChart.getData().add(series);
        areaChart.getData().add(series2);
       
    }
    
    public void setUpTimer(){
            keyFrame = new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            counter+=1;
            update(counter);
            setPeak();
            setMean(counter);
        });
        
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    public void update(int counter){
        double rotation = 195 + getCPUUsage() * 300;
        hand.setRotate(rotation);
        double cpu = getCPUUsage() * 100;
        cpuText.setText(df.format(cpu)+"%");
        System.out.println(df.format(cpu));
    }
    
    public void start(){
        timeline.play();
    }
    
    public void stop(){
        timeline.pause();
    }
    
    public void record(){
        recordCounter+=1;
        createLineChart(recordCounter,getCPUUsage()*100);
    }
    
    public void reset(){
        timeline.stop();
        hand.setRotate(195);
        cpuText.setText("0.00%");
        peakText.setText("Peak CPU usage: 0.00%");
        meanText.setText("Mean CPU usage: 0.00%");
        this.counter=0;
        this.mean=0;
        this.total=0;
        this.peak=0;
        this.recordCounter=0;
        series.getData().clear();
        series2.getData().clear();
    }
    
   public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;    
    }
   
   public void setPeak(){
       
       if(getCPUUsage()*100>peak){
           this.peak = getCPUUsage()*100;
           peakText.setText("Peak CPU usage: " + df.format(peak)+"%");
       }
   }
   
   public void setMean(int count){
       total = total + (getCPUUsage()*100);
       mean = total/count;
       meanText.setText("Mean CPU usage: " + df.format(mean)+"%");
       series2.getData().add(new XYChart.Data(Integer.toString(count),mean));
   }

   public void createLineChart(int counter, double cpu){
       
       series.getData().add(new XYChart.Data(Integer.toString(counter),cpu));
   }
   

    

    public double getCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        
        for(Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            
            if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) {
                try {
                    value = (double) method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = 0;
                }
                return value;
            }
        }
        return value;
    }
    
    
    
    
    
}
