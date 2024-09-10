package com.example.policy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Target {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy = "target")
	@JoinColumn(name = "policy_id", referencedColumnName = "id")
	@JsonIgnore
	private Policy policy;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "any_of_id", referencedColumnName = "id")
	private AnyOf anyOf; 
	
	
	
	public boolean evaluate(RequestContext context) throws Exception {
		return anyOf.evaluate(context);
	}
}
