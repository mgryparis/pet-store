package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petstoreService;
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petstoreData) {
		log.info("Creating new Pet Store {}", petstoreData);
		return petstoreService.savePetStore(petstoreData);						}

	@PutMapping("/pet_store/{petstoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petstoreId, @RequestBody PetStoreData petstoreData)	{
		petstoreData.setPetstoreId(petstoreId);
		log.info("Updated Pet Store {}", petstoreId);
		return petstoreService.savePetStore(petstoreData);
	}
}