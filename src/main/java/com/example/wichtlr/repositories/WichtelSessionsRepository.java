package com.example.wichtlr.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.wichtlr.domain.WichtelSession;

@RestResource(path = "sessions", rel = "sessions")
public interface WichtelSessionsRepository extends
		CrudRepository<WichtelSession, Long>,
		JpaSpecificationExecutor<WichtelSession> {

}
