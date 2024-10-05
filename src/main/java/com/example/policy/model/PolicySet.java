package com.example.policy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class PolicySet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "policySet", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Policy> policies;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="resource_id", referencedColumnName = "id")
    @JsonManagedReference
	private Resource resource;
    
    @Override
    public String toString() {
        return "PolicySet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                // 不要包含 resource 完整信息，避免無限遞迴
                // 可以只打印 resource 的部分信息，比如 id
                ", resourceId=" + (resource != null ? resource.getId() : "null") +
                '}';
    }
	
}
