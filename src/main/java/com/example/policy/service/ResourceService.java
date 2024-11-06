package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.model.Resource;
import com.example.policy.repository.ResourceRepository;


@Service
public class ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	
	public List<Resource> getAllResources(){
		return resourceRepository.findAll();
	}
	public Optional<Resource> getResourceById(Long id) {
	      return resourceRepository.findById(id);
	  }
	
	public Optional<Resource> getResourceByName(String name){
		return resourceRepository.findByName(name);
	}
	public Resource createResource(Resource resource){
		return resourceRepository.save(resource);
	}
	
	public Optional<Resource> updateResource(Long id, Resource resource) {
	    return resourceRepository.findById(id)
	        .map(existingResource -> {
	            if (resource.isAbacEnabled() != existingResource.isAbacEnabled()) {
	                existingResource.setAbacEnabled(resource.isAbacEnabled());
	            }
	            return resourceRepository.save(existingResource);
	        });
	}



	public void deleteResource(Long id) {
		 resourceRepository.deleteById(id);
	}
}
