package pet.store.controller.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;


@Data
@NoArgsConstructor
public class PetStoreCustomer {

		private Long customerId;
		private String customerName;
		private String customerEmail;
		
		public PetStoreCustomer (Customer customer) {
			customerId = customer.getCustomerId();
			customerName = customer.getCustomerName();
			customerEmail = customer.getCustomerEmail();	

	}
}

