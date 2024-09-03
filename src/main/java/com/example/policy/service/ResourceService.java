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
	public Resource createResource(Resource resource){
		return resourceRepository.save(resource);
	}
	
	public Optional<Resource> updateResource(Long id, Resource resource) {
		 return resourceRepository.findById(id)
	                .map(existingResource -> {
	                    existingResource.setType(resource.getType());
	                    existingResource.setRisk_rank(resource.getRisk_rank());
	                    return resourceRepository.save(existingResource);
	                });
	}
	public void deleteResource(Long id) {
		 resourceRepository.deleteById(id);
	}
}
