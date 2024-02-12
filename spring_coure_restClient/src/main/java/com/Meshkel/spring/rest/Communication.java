package com.Meshkel.spring.rest;

import com.Meshkel.spring.rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    @Autowired
    private RestTemplate restTemplate;
    private final String URl="http://localhost:8080/spring_course_rest_war_exploded/api/employees";
    public List<Employee> getAllEmployees(){

        ResponseEntity<List<Employee>> responseEntity=restTemplate.exchange(
                URl, HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>(){} );
        List<Employee> allEmployees=responseEntity.getBody();
        return allEmployees;
    }
    public Employee getEmployee(int id){

        Employee employee=restTemplate.getForObject(URl +"/"+ id, Employee.class);

        return employee;
    }
    public void saveEmployee(Employee employee){

        int id=employee.getId();
        if(id==0){
            ResponseEntity<String> responseEntity=restTemplate.postForEntity(URl, employee, String.class);
            System.out.println("New Employee was added in Database");
            System.out.println(responseEntity.getBody());

        }else{

            restTemplate.put(URl, employee);
            System.out.println("Employee with ID = "+id+" was updated");
        }
    }

    public void deleteEmployee(int id){

        restTemplate.delete(URl+"/"+id);
        System.out.println("Employee with ID = "+id+" was deleted from Database");
    }
}
