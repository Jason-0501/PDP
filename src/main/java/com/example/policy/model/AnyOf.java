package com.example.policy.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyOf {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @OneToMany(mappedBy = "anyOf", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AllOf> allOf;
}
