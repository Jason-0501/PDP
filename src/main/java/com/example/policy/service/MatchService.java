package com.example.policy.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.model.AllOf;
import com.example.policy.model.Match;
import com.example.policy.repository.AllOfRepository;
import com.example.policy.repository.MatchRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private AllOfRepository allOfRepository;
	
	public List<Match> getAllMatchs(){
		return matchRepository.findAll();
	}
	public Optional<Match> getMatchById(Long id) {
	      return matchRepository.findById(id);
	  }
	public Match createMatch(Map<String, Object> requestMap){
		Long allOfId = Long.parseLong(requestMap.get("allOf").toString());
		Optional<AllOf> allof = allOfRepository.findById(allOfId);
		if (allof.isEmpty()) {
			throw new EntityNotFoundException("AllOf not found");
	    }
		Match match = new Match();
        match.setDesignater(requestMap.get("designater").toString());
        match.setAttributeValue(requestMap.get("attributeValue").toString());
        match.setOp(requestMap.get("op").toString());
        match.setDataType(Integer.parseInt(requestMap.get("dataType").toString()));
        match.setAllOf(allof.get());	
        return matchRepository.save(match);
	}
	
	public Optional<Match> updateMatch(Long id, Map<String, Object> updates) {
		Optional<Match> optionalMatch = matchRepository.findById(id);
        if (!optionalMatch.isPresent()) {
            return Optional.empty();  // Match 不存在，返回空的 Optional
        }
        Match match = optionalMatch.get();	

        // 根據請求的數據更新 Match 的屬性
        updates.forEach((key, value) -> {
            switch (key) {
                case "designater":
                    match.setDesignater((String) value);
                    break;
                case "attributeValue":
                    match.setAttributeValue((String) value);
                    break;
                case "op":
                    match.setOp((String) value);
                    break;
                case "dataType":
                	match.setDataType(Integer.parseInt(updates.get("dataType").toString()));
                    break;
                case "allOf":
                    Long allOfId = ((Number) value).longValue();
                    AllOf allOf = allOfRepository.findById(allOfId)
                        .orElseThrow(() -> new EntityNotFoundException("AllOf not found"));
                    match.setAllOf(allOf);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " is not recognized");
            }
        });
        return Optional.of(matchRepository.save(match));
    }
	public void deleteMatch(Long id) {
		Optional<Match> matchOptional = matchRepository.findById(id);
		if(matchOptional.isPresent()) {
			Match match = matchOptional.get();
			AllOf allof = match.getAllOf();
			List<Match> matchList = allof.getMatches();
			matchList.remove(match);
			matchRepository.deleteById(id);
		}else {
	        throw new EntityNotFoundException("Match not found with id: " + id);
	    }
		
	}
	
}
