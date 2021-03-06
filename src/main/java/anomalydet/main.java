/*
 * Copyright (C) 2017 root
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
package anomalydet;

import anomalydet.utilities.filecontroler;
import anomalydet.utilities.serveranom;
import anomalydet.utilities.teacherMan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

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
@ComponentScan
@EnableAutoConfiguration
public class main {
/*  This is the main class of the Service of Anomaly Detection
    Here we initialise the Server of the Aplication.
    We define the port, the timeout,the name of the server and the Folder
    where thera are the train set for server lerning.
    */
    
    public static void main(String[] args){
    //Fisrt we initialise the properties of the Jubatus.
    //These are the ip of the server that we will need in to the client also, the port of the client that it must be the same with the server's port
    //the timeout and an optional name/id of the client that must be defined.
    String iphost="83.212.112.190";//this is the ip of the Jubatus Anomaly Server we would use to the Jubatus Anomaly Client too.
    String name="anomaly";//This is the name/id we will use in the Jubatus Anomaly Client.
    int port=9200;//this is the port of the Jubatus Anomaly Client
    int timeout=432000;//timeout of the Anomaly Detection Server | This value was selected after expiraments because of the stracture of Jubatus frmework
    //we would like to keep the channel active as many time as posible in order to be sure we get the answer or we will achieve the training in a correct way. 
    //This is set because the falmework uses an message library wihch might close the channell in an unexpected manner. So we need this value to be safe here.
    String folder="trainDat";//This is the folder we have all the files with the data we would like to use for training or testing.
    filecontroler manis=new filecontroler(folder);//we initialise an filecontroler Object to have acces to "trainDat" folder.
    serveranom server =new serveranom(iphost, port, name, timeout);//we initialise a serveranom Object in order to setup an Jubatus Anomaly Client.
    teacherMan teacher =new teacherMan(server,manis.getAllData());//we request the file that contains mixed data to make an Object to make the training procedure to the Jubatus Anomaly Server
    SpringApplication.run(main.class, args);//We start the Restful Service.
    }
}
