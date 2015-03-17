package com.example.wichtlr.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.wichtlr.domain.WichtelSessionPair;

@RestResource(path = "pairs", rel = "pairs")
public interface WichtelSessionPairsRepository extends
		CrudRepository<WichtelSessionPair, Long>,
		JpaSpecificationExecutor<WichtelSessionPair> {

}
