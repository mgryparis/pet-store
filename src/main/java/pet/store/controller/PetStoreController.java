package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petstoreService;
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petstoreData)	{
		log.info("Creating new Pet Store {}", petstoreData);
		return petstoreService.savePetStore(petstoreData);						}

	@PutMapping("/pet_store/{petstoreId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PetStoreData updatePetStore(@PathVariable Long petstoreId, @RequestBody PetStoreData petstoreData)	{
		petstoreData.setPetstoreId(petstoreId);
		log.info("Updated Pet Store {}", petstoreId);
		return petstoreService.savePetStore(petstoreData);														}

	@PostMapping("/pet_store/{petstoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addEmployee(@PathVariable Long petstoreId, @RequestBody PetStoreEmployee petstoreEmployee)	{
		log.info("Added a new Employee to Pet Store ID={}", petstoreId);
		return petstoreService.saveEmployee(petstoreId, petstoreEmployee);												}	
	
	@PostMapping("/pet_store/{petstoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomer(@PathVariable Long petstoreId, @RequestBody PetStoreCustomer petstoreCustomer)	{
		log.info("Added a new Customer to Pet Store ID={}", petstoreId);
		return petstoreService.saveCustomer(petstoreId, petstoreCustomer);												}
	
	@GetMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PetStoreData> retrieveAllPetStores()	{
		log.info("Retrieving All Pet Stores");
		return petstoreService.retrieveAllPetStores();		}
	
	@GetMapping("/pet_store/{petstoreId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PetStoreData retrievePetStoreById(@PathVariable Long petstoreId)	{
		log.info("Retrieving Pet Store {}", petstoreId);
		return petstoreService.retrievePetStoreById(petstoreId);						}

	@DeleteMapping("/pet_store/{petstoreId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, String> deletePetStoreById(@PathVariable Long petstoreId)	{
		log.info("Deleting Pet Store {}", petstoreId);
		return petstoreService.deletePetStoreById(petstoreId);						}




}	//  [ End of PetStoreController Class ]