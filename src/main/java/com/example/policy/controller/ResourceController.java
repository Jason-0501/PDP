package com.example.policy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.Resource;
import com.example.policy.service.ResourceService;

@RestController
@RequestMapping("resources")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	@GetMapping
	public List<Resource> getAllResources(){
		return resourceService.getAllResources();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Resource> getUserById(@PathVariable Long id) {
		Optional<Resource> ResourceOptional = resourceService.getResourceById(id);
		return ResourceOptional.map(Resource -> ResponseEntity.ok(Resource))
                .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	 @PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Resource createResource(@RequestBody Resource Resource) {
	      return resourceService.createResource(Resource);
	 }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource Resource) {
		 Optional<Resource> updatedResource = resourceService.updateResource(id, Resource);
		 return updatedResource.map(updated -> ResponseEntity.ok(updated))
                 .orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("/{id}")
	 public void deleteResource(@PathVariable Long id) {
		 resourceService.deleteResource(id);

	 }
	 
}
