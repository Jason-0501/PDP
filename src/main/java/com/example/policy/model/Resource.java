package com.example.policy.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String type;
    private String risk_rank;
    
    @Column(unique = true)
    private String name;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="policySet_id", referencedColumnName = "id")
    @JsonBackReference
	private PolicySet policySet;
    
    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", type='" + type + '\'' +
                // 不要包含 policySet 完整信息，避免無限遞迴
                '}';
    }
}

