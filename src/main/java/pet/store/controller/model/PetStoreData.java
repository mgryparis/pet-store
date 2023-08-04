package pet.store.controller.model;

import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashSet;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petstoreId;
	private String petstoreName;
	private String petstoreAddress;
	private String petstoreCity;
	private String petstoreState;
	private String petstoreZip;
	private String petstorePhone;
	private Set<PetStoreCustomer> customers = new HashSet<>(); 
	private Set<PetStoreEmployee> employees = new HashSet<>();
	
	public PetStoreData(PetStore petstore)	{
		petstoreId = petstore.getPetstoreId();
		petstoreName = petstore.getPetstoreName();
		petstoreAddress = petstore.getPetstoreAddress();
		petstoreCity = petstore.getPetstoreCity();
		petstoreState = petstore.getPetstoreState();
		petstoreZip = petstore.getPetstoreZip();
		petstorePhone = petstore.getPetstorePhone();
		for(Customer customer : petstore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));		}
		for(Employee employee : petstore.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));		}	}
	
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer	{
		private Long customerId;
		private String customerFirstname;
		private String customerLastname;
		private String customerEmail;
		public PetStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstname = customer.getCustomerFirstname();
			customerLastname = customer.getCustomerLastname();
			customerEmail = customer.getCustomerEmail();			}	}
	
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee	{
		private Long employeeId;
		private String employeeFirstname;
		private String employeeLastname;
		private String employeePhone;
		private String employeeJobtitle;
		public PetStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstname = employee.getEmployeeFirstname();
			employeeLastname = employee.getEmployeeLastname();
			employeePhone = employee.getEmployeePhone();
			employeeJobtitle = employee.getEmployeeJobtitle();		}	}

}
