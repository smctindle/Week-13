package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;
	

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		
		return new PetStoreData(petStoreDao.save(petStore));
}	 
	
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZipCode(petStoreData.getPetStoreZipCode());
		petStore.setPetStorePhoneNumber(petStoreData.getPetStorePhoneNumber());

	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if(Objects.isNull(petStoreId)) {
			return new PetStore();
		}else {
			return findPetStoreById(petStoreId);
		}
		
	}

	private PetStore findPetStoreById(long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(
				() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found."));
	}
	
    @Transactional(readOnly= false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		copyEmployeeFields(employee, petStoreEmployee);
		
		return new PetStoreEmployee(employeeDao.save(employee));
		
		}
		
    private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeeName(petStoreEmployee.getEmployeeName());
		employee.setEmployeePhoneNumber(petStoreEmployee.getEmployeePhoneNumber());
				
	}
    
    
    private Employee findEmployeeById( Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(
				() -> new NoSuchElementException("No employee found"));
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException(" Emplpoyee with ID=" + employeeId + " was not found.");
			
		}else {
			
		return employee;
		}
			
    }
	
    private Employee findOrCreateEmployee( Long petStoreId, Long employeeId) {
    	if(Objects.isNull(employeeId)) {
    		return new Employee();
    		
    	}else {
    		return findEmployeeById(petStoreId, employeeId);
    			
    		}
    	
    	}
    
    @Transactional(readOnly= false)
    	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
    		PetStore petStore = findPetStoreById(petStoreId);
    		Long customerId = petStoreCustomer.getCustomerId();
    		Customer customer = findOrCreateCustomer(petStoreId, customerId);
    		copyCustomerFields(customer, petStoreCustomer);
    		customer.getPetStores().add(petStore);
    		petStore.getCustomers().add(customer);
    		return new PetStoreCustomer(customerDao.save(customer));		
    	
    }

		private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
			customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
			customer.setCustomerId(petStoreCustomer.getCustomerId());
			customer.setCustomerName(petStoreCustomer.getCustomerName());
		}

		private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
			if (Objects.isNull(customerId)) {
				
			return new Customer();	
		} else {
			return findCustomerById(petStoreId, customerId);
			
    	}
    	
	}

		private Customer findCustomerById(Long petStoreId, Long customerId) {
			Customer customer = customerDao.findById(customerId).orElseThrow(
					() -> new NoSuchElementException("No customer found"));
			boolean found = false;
			for(PetStore petStore : customer.getPetStores()) {
				if(petStore.getPetStoreId()==petStoreId) {
					found = true;
					break;
					
				}
			}
			if (!found) {
				throw new IllegalArgumentException(" Customer with ID=" + customerId + " was not found.");
			}
			return customer;
		}
        @Transactional(readOnly =  true)
		public List<PetStoreData> retrieveAllPetStores() {
        	List<PetStore> petStores = petStoreDao.findAll();
        	List<PetStoreData> result = new LinkedList<>();
        	
        	for(PetStore petStore : petStores) {
        		PetStoreData psd = new PetStoreData(petStore);
        		psd.getCustomers().clear();
        		psd.getEmployees().clear();
        		
        		result.add(psd);
        		
        	}
        	 
			return result;
			
		} 
        @Transactional(readOnly = true)
		public PetStoreData retrievePetStoreById(Long petStoreId) {
        	PetStore petStore = findPetStoreById(petStoreId);
        	
			return new PetStoreData(petStore);
		}
        
        @Transactional(readOnly = false)
		public void deletePetStoreById(Long petStoreId) {
			PetStore petStore = findPetStoreById(petStoreId);
			petStoreDao.delete(petStore);
			
		}

		
 }
		


		
    	
 
    		

			
    
	

	
		


	
	


