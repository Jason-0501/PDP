package com.example.policy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "match_table")
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String designater;
	private String attributeValue;
	private String op;
	private String dataType; 
	
	@ManyToOne
    @JoinColumn(name = "all_of_id", nullable = false)
    private AllOf allOf;

}
