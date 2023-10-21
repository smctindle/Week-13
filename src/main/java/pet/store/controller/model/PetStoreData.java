package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor

public class PetStoreData {
	
	
	private Long petStoreId; 
	private String petStoreName; 
	private String petStoreAddress; 
	private String petStoreCity; 
	private String petStoreState; 
	private int petStoreZipCode; 
    private Long petStorePhoneNumber;
    private Set<PetStoreEmployee> employees = new HashSet<>();
	private Set<PetStoreCustomer> customers = new HashSet<>();
    
    public PetStoreData (PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZipCode = petStore.getPetStoreZipCode();
		petStorePhoneNumber = petStore.getPetStorePhoneNumber();
		
		for (Customer customer: petStore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));
		}
		
		for (Employee employee: petStore.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));
    
    }
		}
} 
    
    	
    
   
		



 
