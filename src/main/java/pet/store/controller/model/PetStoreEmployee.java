package pet.store.controller.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor
public class PetStoreEmployee {

	Long employeeId; 
	String employeeName; 
	Long employeePhoneNumber; 
	String employeeJobTitle; 
	
    public PetStoreEmployee(Employee employee) {
	    employeeId = employee.getEmployeeId();
	    employeeName = employee.getEmployeeName();
	    employeeJobTitle = employee.getEmployeeJobTitle();
	    employeePhoneNumber = employee.getEmployeePhoneNumber();
	
}

	}


