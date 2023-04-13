import java.lang.Math;
import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
public class powerCoefficient
{
int diameter = 132;
int velocity;
int surfaceArea = 13685;
double density = 1.2;
int power;
String[] array = new String[5000];
double[] windSpeeds = new double[5000]; 

public static void main(String[] args){
    powerCoefficient pc = new powerCoefficient();
    try{
        System.out.println(pc.results());   
    } catch (Exception e) {
        System.out.println("error");
    }
}
    
public double[] velocityReader(String filename) {
    double[] velocities = new double[5000];
    try {
    File file = new File(filename);
    
    Scanner scan = new Scanner(file);
    
    int i = 0;
    boolean v_line = false;
        
    while(scan.hasNext()) {
        String line = scan.nextLine();
        line = line.trim();
        if (line.equalsIgnoreCase("Velocity")) {
            v_line = true;
            continue;
        }
        else if (line.equalsIgnoreCase("Power")) {
            v_line= false;
            break;
        }
        else if(v_line){
            velocities[i++] = Double.parseDouble(line);
                
        }
            
    }
    scan.close();
} catch (IOException ioe){
    System.out.println("error happens in velocityReader(): " + ioe.toString());
} catch (Exception e){
    System.out.println("error happens in velocityReader()" + e.toString());
}
    return velocities;   
}
    
public double[] powerReader(String filename) throws IOException{
    double[] power = new double[5000];
    
    try{
    File file = new File(filename);
    Scanner scan = new Scanner(file);
    
    int i = 0;
    boolean v_line = false;
    
    
    while(scan.hasNext()){
        String line = scan.nextLine();
        line = line.trim();
        if(line.equalsIgnoreCase("Power")){
            v_line = true;
            continue;
        }
        else if(line.equalsIgnoreCase("end")){
            v_line = false;
            break;
        }
        else if(v_line){
            line = line.replaceAll("," , "");
            power[i++] = Double.parseDouble(line); 
        }
    }
    scan.close();
}
catch(Exception e){
    System.out.println("Error happens in powerReader() " + e.toString());
}
return power;
}

public double[] calculateWindPow() {
    
    double[] velocity = velocityReader("UMC1Week.txt");
    double[] windPower = new double[5000];
    double density = 1.22;
    double area = 3.14*Math.pow(132,2)/4.0;
    
    try{
    for(int i = 0; i < velocity.length; i++){
        windPower[i] = 0.5*density*(Math.pow(velocity[i], 3))*area/1000.0;    
    }
    } catch (Exception e){
        System.out.println("Error happens in calculateWindPow() " + e.toString());
    }
    return windPower;
    
}

public double[] getPowerCoe() throws IOException{
    double[] PowerCoe = new double[5000];
    double[] windPower = calculateWindPow();
    double[] power = powerReader("UMC1Week.txt");
    for(int i = 0; i < 5000; i++){
        if(windPower[i] == 0) {
            continue;   
        }
        else if(power[i]/windPower[i] < .05){
            continue;
        }
        else {
            PowerCoe[i] = power[i]/windPower[i];
        }
    }
    
    return PowerCoe;
}

public String results() throws IOException{
    double count = 0.0;
    double sum = 0.0;
    double[] powerCoe = getPowerCoe();
    
    for(int i = 0; i<5000; i++){
        if(powerCoe[i] == 0.0){
            continue;
        }
        else{
            sum += powerCoe[i];
            count++;
        }
    }
    
    double average = sum/count;
    String realAverage = Double.toString(average);
    String results;
    
    if((average) >= .4 && (average) <= .5){
        results = "Your wind turbine has a power coefficient of " + realAverage + ". Meaning your wind turbine is performing optimally.";       
    }
    else {
        results = "Your wind turbine has a power coefficient of " + realAverage + ". Meaning your wind turbine is not performing optimally and needs to be checked on.";
    }
    
    return results;
}
}

