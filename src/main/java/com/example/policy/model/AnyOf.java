package com.example.policy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class AnyOf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(mappedBy = "anyOf")
	@JoinColumn(name = "target_id", referencedColumnName = "id")	@JsonIgnore
	private Target target;
	
	
    @OneToMany(mappedBy = "anyOf", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<AllOf> allOfs;
    
    public boolean evaluate(RequestContext context) throws Exception {
        for (AllOf allOf : allOfs) {
            if (allOf.evaluate(context)) {
                return true; // 只要有一個AllOf返回true
            }
        }
        return false; // 如果沒有任何一個AllOf返回true，返回false
    }
}
