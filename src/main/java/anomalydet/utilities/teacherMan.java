/*
 * Copyright (C) 2017 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package anomalydet.utilities;
//This Package incorporates Objects that help in the service construction as Tools or structural elements.
//In this package we define Objects and methods where we use for file handling and reading, Anomaly service 
//definition, server conection, Anomaly Service training and open server Record Keeping(obsolete)
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import us.jubat.common.Datum;

/**
 *
 * @author Ioannis Nikolaou
 * @version Final
 * emal: inikolao@hotmail.com
 * AM 4504
 * Diploma Project Part 2
 * Jubatus Anomaly Detection Service
 * 
 * This service has build with Spring Boot Libraries for the RestFul
 * including Jubatus framework as a Anomaly Detection Core
 * 
 * 
 */
public class teacherMan {
 //This is an Object which is responsible for the Anomaly Server Training
 //His mission is to define a Jubatus Anomaly Client and through this client 
//to connect to Jubatus Anomaly Server. Using this client he sends training data 
//to Jubatus Anomaly Server.
    
    serveranom server;//This is a stuctural Object we have create and contains all the logic about the definition and the logic of Jubatus Anomaly client
    //We must say here that the Anomaly server is a part of the service that is not included in this Jar and it is started separately.
    //However for clarity reasons in the entire project we assume that when we speak about the Jubatus Anomaly client we speak about the server and thats why
    //we use names that indicate to server meanwhile we refer to Client.
    String file;//This is the string variable where we keep the file name that contains the training data
    String name;//This is the string variable where we keep an optional string we sould put in the server as id string.
    
    
    public teacherMan(serveranom server,String file)
    {//this is the constructor of the object teachermMan
     //It has an serveranom Object, a string that refers to the file from we make the training and an id name about the server.
     //Once it is called sets up the Jubatus Anomaly client and start the training.
        this.server=server;//initialise the object
        this.file=file;
        this.name=server.name;
        teaching();//This is the method that performs the Jubatus Server Training.
    
    }
    public void getStatus()
    {
    //future method. The thought there can be to return the teaching status or the server status.
    }
    private void teaching()
    {//Here we define the method that performs the Jubatus Server Training.
        String line;//this is a varaiable we use in file reading. Represents one line of the file.
        String [] elemval;// this is a 2D array we use in file reading. As the form of the file we accept in each is "Type","Value"
        //this array is used to separate the type and the value for each line.
        ArrayList<String> typesK= new ArrayList();//Help lists in order to send the type in blocks
        ArrayList<Double> valuesK= new ArrayList();//Help lists in order to send the values in blocks
        final BufferedReader br;// This is a Buffer we use to read the file with the training data.
        Datum dataTotrain=null;// Here we initialise a struct named Datum that is defined by the Jubatus framework as a form of communication with the Jubatus Server.
        int help3=0;//This is a helping variable that we use for debugging purposes.
        try {
            System.out.println("Start teaching. Type: "+file);//We sent a info message to console to inform that the training process is starting with the particular file.
            br = new BufferedReader(new FileReader("trainDat/"+file));//This file must be in a folder named trainDat. Here we define a reader we use to read the file with the training data.
            while ((line = br.readLine()) != null) {//while we ane not in the end of file we keep reaging from file.
                elemval=line.split(",");//we separate the type and the value for each line of the training file.
                //We add the value and the type of the value to the struct named Datum. The form is (key,value)
               typesK.add(elemval[0]);//we add the value in of the line in a list
                valuesK.add(Double.parseDouble(elemval[1]));//we add the type of the line in list
             help3++;//we count the file line in order to create the send block
              if(help3==100)
                {
                    dataTotrain=createdomeTosent(typesK,valuesK);//we send the list of value for train to add it in the train model.
                    typesK.clear();// we clear the help list of types for reuse
                    valuesK.clear();// we clear the help list of values for reuse
                    server.client.add(dataTotrain);//we send the list of value for train to add it in the train model.
                    dataTotrain.numValues.clear();//we emty the struct Datum in order to send the next block of values.
                    help3=0;//we reset the help value for the next block
                    //break;
                }
              
            }
            br.close();//close the file reader.
         dataTotrain=createdomeTosent(typesK,valuesK);//we send the list of value for train to add it in the train model.
        typesK.clear();// we clear the help list of types to free memory.
        valuesK.clear();// we clear the help list of values to free memory.
        server.client.add(dataTotrain);//we send the final list of value for train to add it in the train model.
        dataTotrain.numValues.clear();//we emty the struct Datum in order to free memory.
        } catch (FileNotFoundException ex) {//handling the case we can not find the selected data file
            System.out.println("File Not Found Error.");//sending a relative error message to console.->"File Not Found Error."
            System.exit(0);//terminate the program.
        } catch (IOException ex) {//handling the case we have an input output error
            System.out.println("File Reader Error.");//sending a relative error message to console.->"File Reader Error."
            System.exit(0);//terminate the program.
        }
    }
    private static Datum createdomeTosent(String key,double value)
    {//this is a method we can use in order to create the Jubatus comunication struct named DATUM.
         Datum datum = new Datum();//We define a new Jubatus comunication struct Datum         
         return datum.addNumber(key, value);//And we return it.
    }   
 private static Datum createdomeTosent(ArrayList<String> key,ArrayList<Double> value)
    {//this is a method we can use in order to create the Jubatus comunication struct named DATUM of a list of values.
         Datum datum = new Datum();//We define a new Jubatus comunication struct Datum         
         try{
         for(int i=0;i<key.size();i++)
         {
             datum.addNumber(key.get(i), value.get(i));//we start to creating a DATUM LIST
         
         }
         key.clear();
         value.clear();
         }
         catch(Exception ex)
         {
             System.out.println("Error durring making dome to send.");//sending a relative error message to console in case of error.->"Error durring making dome to send."
             ex.printStackTrace();
             return null;
         }
         return datum;//return the List DATUM
    }
}
