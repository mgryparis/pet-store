package pet.store.service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petstoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;

	
//  PetStore Service Section ==========================================================	
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petstoreData) {
		Long petstoreId = petstoreData.getPetstoreId();
		PetStore petstore = findOrCreatePetStore(petstoreId);
		copyPetStoreFields(petstore, petstoreData); 
		return new PetStoreData(petstoreDao.save(petstore));	}

	private void copyPetStoreFields(PetStore petstore, PetStoreData petstoreData)	{
		petstore.setPetstoreId(petstoreData.getPetstoreId());
		petstore.setPetstoreName(petstoreData.getPetstoreName());
		petstore.setPetstoreAddress(petstoreData.getPetstoreAddress());
		petstore.setPetstoreCity(petstoreData.getPetstoreCity());
		petstore.setPetstoreState(petstoreData.getPetstoreState());
		petstore.setPetstoreZip(petstoreData.getPetstoreZip());
		petstore.setPetstorePhone(petstoreData.getPetstorePhone());					}
	
	private PetStore findOrCreatePetStore(Long petstoreId) {
		PetStore petstore = new PetStore();
		if(!Objects.isNull(petstoreId))	{	petstore = findPetstoreById(petstoreId);	}	
		return petstore;																}

	private PetStore findPetstoreById(Long petstoreId) {
		return petstoreDao.findById(petstoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID=" + petstoreId + " was not found."));		}
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> listOfAllPetStores = petstoreDao.findAll();
		List<PetStoreData> listOfAllPSDsWithoutCustomersOrEmployees = new ArrayList<PetStoreData>();
		for (PetStore petstore : listOfAllPetStores)	{
			PetStoreData psd = new PetStoreData(petstore);
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			listOfAllPSDsWithoutCustomersOrEmployees.add(psd);							}
		return listOfAllPSDsWithoutCustomersOrEmployees;								}
	
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petstoreId) {
		PetStore petstore = findPetstoreById(petstoreId);
		return new PetStoreData(petstore);												}

	public Map<String, String> deletePetStoreById(Long petstoreId) {
		PetStore petstore = findPetstoreById(petstoreId);
		petstoreDao.delete(petstore);
		Map<String, String> DeletionMessage = new HashMap<String, String>();
		DeletionMessage.put("Message: ", "Deletion Successful!");
		return DeletionMessage;															}
	
//  Employee Service Section ==========================================================	
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petstoreId, PetStoreEmployee petstoreEmployee) {
		PetStore petstore = findPetstoreById(petstoreId);
		Long employeeId = petstoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeId, petstoreId);
		copyEmployeeFields(employee, petstoreEmployee);
		employee.setPetStore(petstore);
		petstore.getEmployees().add(employee);
		Employee dbEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(dbEmployee);											}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petstoreEmployee)	{
		employee.setEmployeeId(petstoreEmployee.getEmployeeId());
		employee.setEmployeeFirstname(petstoreEmployee.getEmployeeFirstname());
		employee.setEmployeeLastname(petstoreEmployee.getEmployeeLastname());
		employee.setEmployeePhone(petstoreEmployee.getEmployeePhone());
		employee.setEmployeeJobtitle(petstoreEmployee.getEmployeeJobtitle());				}
	
	private Employee findOrCreateEmployee(Long employeeId, Long petstoreId)	{
		Employee employee = new Employee();
		if(Objects.isNull(employeeId))	{	return employee;								}
		else	{	employee = findEmployeeById(employeeId, petstoreId);	
					return employee;														}	}
		
	private Employee findEmployeeById(Long employeeId, Long petstoreId)	{
		Employee employee = new Employee();
		employee = employeeDao.findById(employeeId).orElseThrow(
			() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));			
		if(employee.getPetStore().getPetstoreId() != petstoreId)	{	
			throw new IllegalArgumentException("Employee with ID=" + employeeId + 
				" already works at at a different Pet Store: Pet Store ID=" + petstoreId);	}
		else	{	return employee;														}	}		
	
//  Customer Services Section ==========================================================	
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petstoreId, PetStoreCustomer petstoreCustomer) {
		PetStore petstore = findPetstoreById(petstoreId);
		Long customerId = petstoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(customerId, petstoreId);
		copyCustomerFields(customer, petstoreCustomer);
		customer.getPetStores().add(petstore);		//   <<----     Possible cleanup required later?
		petstore.getCustomers().add(customer);
		Customer dbCustomer = customerDao.save(customer);
		return new PetStoreCustomer(dbCustomer);											}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petstoreCustomer)	{
		customer.setCustomerId(petstoreCustomer.getCustomerId());
		customer.setCustomerFirstname(petstoreCustomer.getCustomerFirstname());
		customer.setCustomerLastname(petstoreCustomer.getCustomerLastname());
		customer.setCustomerEmail(petstoreCustomer.getCustomerEmail());						}
	
	private Customer findOrCreateCustomer(Long customerId, Long petstoreId)	{
		Customer customer = new Customer();
		if(Objects.isNull(customerId))	{	return customer;	}
		else	{	customer = findCustomerById(customerId, petstoreId);	
					return customer;														}	}
		
	private Customer findCustomerById(Long customerId, Long petstoreId)	{
		Customer customer = new Customer();
		customer = customerDao.findById(customerId).orElseThrow(
			() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found."));				
		Set<PetStore> ThisCustomersListOfStores = customer.getPetStores();	
		boolean bingo = false;
		for(PetStore PetstoreToCheck : ThisCustomersListOfStores)	{
			if (PetstoreToCheck.getPetstoreId() == petstoreId)	{	bingo = true;			}	}
		if (bingo)	{	return customer;													}
		else		{	throw new IllegalArgumentException("Customer with ID=" + customerId + 
						" is not a Customer of Pet Store ID=" + petstoreId);				}	}



	
}  //  [ End of PetStoreService Class ]
