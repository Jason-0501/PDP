package com.example.policy.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    private boolean effect;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id", referencedColumnName = "id")
	private Target target;
    
    public boolean evaluate(RequestContext context) throws Exception{
    	return target.evaluate(context);
    }
}
