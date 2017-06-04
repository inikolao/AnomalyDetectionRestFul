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
package anomalydet.springcore;
/*
This package is based in the example for Restfull service of spring Boot.
We keep in separate package the logic of the service in order to have more clear 
view of the code.
*/

import anomalydet.utilities.serveranom;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@RestController 
public class controler {
    /*
    Here is the RestFul Controler of the Service. The controler is a HTTP request handler.
    This is defined from the @RestController annotation.
    */
    private static final String TypeSent = "Type Sent:, %s ";//Here we define the data we would like to get
    private static final String ValueSent = "Value Sent:, %f ";//an answer in a posible HTTP request to Anomaly Detect a value.
    private static final String ScoreGet = "Score Get:, %f ";//There are three values wich are returned: The Type we of the value we sent, The Value we sent
    //, and Finally the score of abnormality or normality we get from the anomaly detection Service.
    //There was a thought of returning one more value which would saying if the resultant score is indicating normality or abnormality. However this value
    //was omitted in order to give the final user to option to choose what value could be useful as an abonormality threshold in his application.
    //We must say here that the higher score we get(away from 1) the higher abnormality we have.
    private final AtomicLong counter = new AtomicLong();//We define an up-counter who represents the id of the result and is also returned with the result.
    //Every instance of Service request initialise an client to the Anomaly Detection Server in order to get the answer.
    //He re we define the ip of the Anomaly Detection Server the port, the timeout and the name. The name is an optional string wich is an id of the client.
    String iphost="83.212.112.190";//ip of the Anomaly Detection Server
    String name="anomaly";//name/ID of the Anomaly Detection Server
    int port=9200;//the port of the Anomaly Detection Server
    int timeout=432000;//timeout of the Anomaly Detection Server | This value was selected after expiraments because of the stracture of Jubatus frmework
    //we would like to keep the channel active as many time as posible in order to be sure we get the answer or we will achieve the training in a correct way. 
    //This is set because the falmework uses an message library wihch might close the channell in an unexpected manner. So we need this value to be safe here.
    
    private serveranom server =new serveranom(iphost, port, name, timeout);//This is an utility object which designed to enclose all the Anomaly client logic
    //due to clarity reasons. This object includes the original initialisation of the server, the connection, the function of anomaly detection plus more functions for easiness.
    //More you can see in the Object Definition in Utility Package.
    float score;//this is the score we will get after the anomaly detection procedure. It is defined hera as floating point number.
    Datum dataTocheck=null;//This is a structure in which the Jubatus Framework communicates in order to send data either for training or anomany evaluation.
    //It is going to be used for anomaly detection procedure.
    //It's defined here and it's is initialised as null.

    @RequestMapping("/anomaly")//This is a Spring Boot annotation which ensures that HTTP requests to /anomaly are mapped to coreback method.
    //This  coreback method keeps the resulting information. For more see the object definition.
    public coreback anomaly(@RequestParam(value="type", defaultValue="Noise") String type,@RequestParam(value="value",defaultValue="845") double value)//throws UnknownHostException 
    {//Here we define the Method that keeps the main logic of the rest ful service. In other words we say tha every request for anomaly detection is been handled from this method.
     //Here we define also the format of the url request. This format can be discovered by the definition of the values inside parentheses.
     //The format is as follows. The first value represents the type of the value we want to test for abnormalities and the second represents the value we want to test. 
     //Both are defined with an @RequestParam anotation as all the rest.
     //This anotation binds the value of the query string parameter type into the type parameter of the anomaly method. 
     //Also binds the value of the query string parameter value into the value parameter of the anomaly method. 
     //These strings parameters of the query are explicitly marked as optional by default. If it is an absence the default value of each parameter is given by
     //the defaultValue varaiable.
     //Finally the url format is: /anomaly?type="TypeOfValueToCheck"&value="ValueToCheck" 
        try {
            score=server.getScore(type, value);// Here we start the procedure of Anomaly Detection. This is the key 
            //line for that procedure due to incorporates core logic about the server communication. Basically we call a method which is defined in the serveranom object
            // and it is responsible for the anomaly detection requests.
            
        } catch (NullPointerException ex) {// Here we care about Null Pointer exception because is posible the returned score might be Not a Number(NaN) or null.
            System.out.println("NullPointerException occurs... Change score to NaN...");//We sent an NUll pointer Error to console.
            //score=Float.NaN; deprecated line. Can be removed. It is used in order to view the Null pointer exception erron in the http output of the service.
            
        }
        System.out.println("Requesting param Score.....");//We sent an info message to console to give the status of the request. After see this we can expect to get an answer.
        return new coreback(counter.incrementAndGet(),String.format(TypeSent,type),String.format(ValueSent,value),String.format(ScoreGet,score));//Here we get the response 
        //of the service and thanks to the spring boot library this is encoded in JSON
    }    
    
}
