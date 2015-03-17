package com.example.wichtlr.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.wichtlr.domain.Participant;

@RestResource(path = "participants", rel = "participants")
public interface ParticipantsRepository extends
		CrudRepository<Participant, Long>,
		JpaSpecificationExecutor<Participant> {

}
