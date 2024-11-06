package com.example.policy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
    private boolean effect;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="target_id", referencedColumnName = "id")
	private Target target;
    
    
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "policySet_id", referencedColumnName = "id",nullable = true)
    @JsonBackReference
    private PolicySet policySet;
    
    public boolean evaluate(RequestContext context,boolean abacEnabled) throws Exception{
    	return target.evaluate(context,abacEnabled);
    }
}
