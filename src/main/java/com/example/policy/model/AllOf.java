package com.example.policy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllOf {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anyOf", referencedColumnName = "id")
    private AnyOf anyOf;
	
	@OneToMany(mappedBy = "allOf", cascade = CascadeType.ALL)
	private List<Match> matches;
	
	public boolean evaluate(RequestContext context) throws Exception {
        for (Match match : matches) {
            if (!match.evaluate(context)) {
                return false; // 如果有一个 Match 不满足条件，返回 false
            }
        }
        return true; // 如果所有 Match 都满足条件，返回 true
    }
}
