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

import java.util.ArrayList;
import java.util.List;

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
public class BlackBoxPacket {
//NOT USED. DEPRECATED
//This is a Odject we define in order to keep a list of a List of anomaly clients in order to define and initialise them in the main function and use them in 
//the spring core package in order to communicate whith the servers for abnormality test. This Object exist because in first design we assumed tha we would 
//be obligated to comnunicate with a list of servers plus we wanted to have only to one position the Anomaly Client logic and definition.
//However this method was unsafe and dangerous. So we dont use it any more plus the code is more simple.
    
    List <serveranom> activeservers=new ArrayList();//this is the list of the Anomaly Clinets we want to keep
    
    public BlackBoxPacket(List <serveranom> servers)
    {//this is the constructor of the Object BlackBoxPacket
     //The Object as we said above keeps a list of Anomaly Clients
        this.activeservers=servers;
    
    }
    public BlackBoxPacket()
    {
        //Optional null constructor. Not Used.
        //This constructos exist because in development in order to avoid 
        //confusions and errors we defined a null BlackBoxPacket packet.
    }
    public void addNewMember(serveranom anom)
    {//This is a Method that adds an Anomaly Client in the activeservers list which is the list we keep for the Anomaly Clients.
     //Finaly returns the new activeservers list after the add.
        activeservers.add(anom);
    }
    public List<serveranom> getAllMembers(serveranom anom)
    {//This is a Method tha returns theactiveservers list.
        return activeservers;
    }
    public serveranom getNamedserver(int intex)
    {//This is a Method that returns the i member of the activeservers list.
     //The i member is represented by the variable index.
    return activeservers.get(intex);
    }
    public serveranom getNamedserver(String name)
    {//This is a Method that returns the i member of the activeservers list with the id name of the client equalled by string variable "name".
        int i;
        serveranom rtr = null;//we define a null serveranom Object and we search all the activeservers list
        for(i=0;i<activeservers.size();i++)//with this for loop in order to find the member of the list who has the
        {                                  //given string name as an id-name.
            if(activeservers.get(i).name.equals(name))//we assume that there is only one serveranom Object with id=name.
            {
                rtr=activeservers.get(i);//we have found the mebmer with the id=name.
            
            }
        
        }
        return rtr;//we return the member we have found if it exists else we return a null serveranom Object.
    }
    
}
