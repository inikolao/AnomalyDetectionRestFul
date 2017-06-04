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

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.jubat.anomaly.AnomalyClient;
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
//This is an object we define in order to simplify the initialisation and the conection with the Jubatus Anomaly Server.
//We should say here that in this object we refer to Jubatus Anomaly Client although it seems that we speak about Jubatus Anomaly Servers.
//This happens due to simplicity of the code and the main reason is that the Jubatus Anomaly Server is defined and initialised out of bounds of this deployment.
//We use this Object as structural tool in order to maintain the connections with the Jubatus Anomaly Server for training and abnormality testing 
//in a simple way.
public class serveranom {
//For the definition and the setup of a Jubatus Anomaly Client we should maintain the following variables
    String iphost;//the ip where the server runs in order to comunicate with.
    int port;// the port where the server runs for the same reason
    String name;// and a optional name to keep separate the client streams.(we must define it however.) This name acts as an id of the server/client communication.
    int timeout;//Moreover we need to define an a timeout(integer) to set when the connection times out.
    AnomalyClient client;//This Object is a Jubatus Anomaly CLient which is the object we need initialise in order to train or make an abnormality test.
    
//The serveranom object includes the method which responsible for the abnormality test.  This makes this procedure very easy because we have no worries about to which
//server we referred to.
    
    public serveranom(String iphost,int port,String name,int timeout)
    {//This is the constructor of serveranom Object.
        this.iphost=iphost;//the ip of the Object. This corresponds to the ip of the Jubatus Anomaly Client.
        this.port=port;//the port  of the Object. This corresponds to the port of the Jubatus Anomaly Client.
        this.name=name;//the name of the Object. This corresponds to the name of the Jubatus Anomaly Client.
        this.timeout=timeout;//the timeout of the Ojcet. This corresponds to the timeout of the Jubatus Anomaly Client.
        this.client=setupcl();//this is the Initialization of the Jubatus Anomaly client. This refers to a method which is defined below.
         
    
    }
    public String getip(){//this is A method we use to get the ip of the serveranom.
    return this.iphost;
    }
    public String getname(){//this is A method we use to get the name/id of the serveranom.
    return this.name;
    }
    public int timeout(){//this is A method we use to get the timeout of the serveranom.
    return this.timeout;
    }
    public int getport(){//this is A method we use to get the port of the serveranom.
    return this.port; 
    }
    private AnomalyClient setupcl()
    {//this is a method we use to initialise the Jubatus Anomaly Client and furthermore the serveranom Object.
     //Because we use this method in the constructor the method is PRIVATE.
        AnomalyClient client=null;//First we define a null Jubatus Anomaly Client.
        try{
            System.out.println("Server Client Setup. Type: "+name);//We sent an info message to console to inform about the initialisation.
            client = new AnomalyClient(iphost,port ,name,timeout);//Then we nitialise the Jubatus Anomaly Client with port,ip,name and timeout.
        
        }
        catch(UnknownHostException ex){//this is an exception that occurs when in the ip or port we set there is not exist an Jubatus Anomaly Server instance.
            
            System.out.println("Server Client Setup Failed");//We sent an info message to console that "Server Client Setup Failed".
            System.exit(0);//We terminate the execution.
        } 
        return client;//we return the initialized Jubatus Anomaly Client to variable client of the serveranom Object.
    }
    //The method getScores does the same work. They provide the procedure we do the abnormality test. 
    //The deference is in the data type they send the data to the server.
    //This exist because of the Restful service procedure. During the developnet we used both Methods.
    //So Both methods are listed.
    public List<Float> getScores(String type,double value)
    {//This is the method we define the procedure of abnormality test.
     //Here we sent the value and its key(type) and we get the score in a FLOAT list form.
        float Score;//this the score we get
        List<Float> scores=new ArrayList();//this is the list we return
        Score=client.calcScore(createdomeTosent(type, value));//we create a Jubatus comunication struct named DATUM with the type(key) and the value 
                                                              //for abnormality test, we sendt this pair via this struct and we get the abnormality score.
        scores.add(Score);//we put the result score in a ArrayList
        return scores;//and we return it.
    
    }
    public Float getScore(String type,double value)
    {//This is the method we define the procedure of abnormality test.
     //Here we sent the value and its key(type) and we get the score in a FLOAT list form.
        float Score;//this the score we get
        Score=client.calcScore(createdomeTosent(type, value));//we create a Jubatus comunication struct named DATUM with the type(key) and the value 
                                                              //for abnormality test, we sendt this pair via this struct and we get the abnormality score.
        return Score;//we return the score of the result.
    
    }
    private static Datum createdomeTosent(String key,double value)
    {//this is a method we can use in order to create the Jubatus comunication struct named DATUM.
         Datum datum = new Datum();//We define a new Jubatus comunication struct Datum          
         return datum.addNumber(key, value);//And we return it.
    }
    
    
}
