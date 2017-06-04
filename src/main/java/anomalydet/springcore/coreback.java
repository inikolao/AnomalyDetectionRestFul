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
public class coreback {
     /*
    Here is the RestFul "Core" of the Service. This core is an information keeper.
    This object keeps the resulting information from the anomaly detection requests.
    */
    private final long id;//This is the result id. A counter who is increasing by one in every result.
    private final String str1;//This is the variable that keeps the string "Type we send: type of the value" we want to evaluate.
    private final String str2;//This is the variable that keeps the string "value we send: value" we want to evaluate.
    private final String str3;//This is the variable that keeps the string "score we get: score" we get as an answer from the anomaly detection procedure.

    public coreback(long id, String str1,String str2,String str3) { //This is the constractor of the object Coreback
        //Here we create an object Coreback with the given parameters as described above.
        this.id = id;
        this.str1 = str1;
        this.str2 = str2;
        this.str3= str3;
    }
    public long getId() { //A Method which returns the value of the id. As we said the id is the result's id.
        return id;
    }

    public List<String> getContent() {//A Method which returns a list of the values we keep inside this Coreback object.
        List<String> content= new ArrayList();
        content.add(str1);
        content.add(str2);
        content.add(str3);
        return content;
    }    
}
