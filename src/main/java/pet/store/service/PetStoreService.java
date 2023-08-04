package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petstoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petstoreData) {
		Long petstoreId = petstoreData.getPetstoreId();
		PetStore petstore = findOrCreatePetStore(petstoreId);
		copyPetStoreFields(petstore, petstoreData); 
		return new PetStoreData(petstoreDao.save(petstore));
	}
	private void copyPetStoreFields(PetStore petstore, PetStoreData petstoreData) {
		petstore.setPetstoreId(petstoreData.getPetstoreId());
		petstore.setPetstoreName(petstoreData.getPetstoreName());
		petstore.setPetstoreAddress(petstoreData.getPetstoreAddress());
		petstore.setPetstoreCity(petstoreData.getPetstoreCity());
		petstore.setPetstoreState(petstoreData.getPetstoreState());
		petstore.setPetstoreZip(petstoreData.getPetstoreZip());
		petstore.setPetstorePhone(petstoreData.getPetstorePhone());		
	}
	
	private PetStore findOrCreatePetStore(Long petstoreId) {
		PetStore petstore = new PetStore();
		if(!Objects.isNull(petstoreId))	{	petstore = findPetstoreById(petstoreId);	}	
		return petstore;																}
	
//	private PetStore findOrCreatePetStore(Long petstoreId) {
//		PetStore petstore;
//		if(Objects.isNull(petstoreId))	{	petstore = new PetStore();					}
//		else 							{	petstore = findPetstoreById(petstoreId);	}	
//		return petstore;																}

	private PetStore findPetstoreById(Long petstoreId) {
//		return petstoreDao.findById((long) 1)
		return petstoreDao.findById(petstoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID=" + petstoreId + " was not found."));		}

}
