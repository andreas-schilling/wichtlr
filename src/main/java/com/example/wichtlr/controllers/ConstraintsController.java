package com.example.wichtlr.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.wichtlr.domain.Participant;
import com.example.wichtlr.repositories.ParticipantsRepository;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/constraints")
public class ConstraintsController {

	@Autowired
	private ParticipantsRepository participantsRepository;

	public ConstraintsController() {
		super();
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET }, produces = { MediaType.TEXT_HTML_VALUE })
	public String all(final Locale locale, final Model model) {
		Iterable<Participant> allParticipants = participantsRepository
				.findAll();
		Iterable<Participant> withExclusions = Iterables.filter(
				allParticipants, hasExclusions());
		List<Participant> unidirectional = Lists.newArrayList();
		List<Participant> all = Lists.newArrayList();
		for (final Participant oneParticipant : withExclusions)
		{
			if (!all.contains(oneParticipant))
			{
				unidirectional.add(oneParticipant);
				all.add(oneParticipant);
				all.addAll(oneParticipant.getExclusions());
			}
		}
		model.addAttribute("participants", unidirectional);
		model.addAttribute("allParticipants", allParticipants);
		return "constraints";
	}

	private Predicate<Participant> hasExclusions() {
		return new Predicate<Participant>() {

			@Override
			public boolean apply(Participant input) {
				return input.hasExclusions();
			}
		};
	}

	@RequestMapping(value = "/{id}/{exclusionId}", method = { RequestMethod.DELETE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String delete(@PathVariable final Long id,
			@PathVariable final Long exclusionId) {
		Participant participant = participantsRepository.findOne(id);
		Participant excluded = participantsRepository.findOne(exclusionId);
		participant.removeMutualExclusion(excluded);
		participantsRepository.save(ImmutableList.of(participant, excluded));
		return "redirect:/constraints/";
	}

	@RequestMapping(value = "/", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String add(final ExclusionDTO newConstraint,
			final BindingResult bindingResult) {
		Participant participant = participantsRepository.findOne(newConstraint
				.getParticipant());
		Participant excluded = participantsRepository.findOne(newConstraint
				.getExcluded());
		participant.addMutualExclusion(excluded);
		participantsRepository.save(ImmutableList.of(participant, excluded));
		return "redirect:/constraints/";
	}

	@ModelAttribute(value = "newConstraint")
	public ExclusionDTO newFormObject() {
		return new ExclusionDTO();
	}

	public static class ExclusionDTO {
		private Long participant;

		private Long excluded;

		public ExclusionDTO() {
		}

		public ExclusionDTO(Long participant, Long excluded) {
			super();
			this.participant = participant;
			this.excluded = excluded;
		}

		public Long getParticipant() {
			return participant;
		}

		public void setParticipant(Long participant) {
			this.participant = participant;
		}

		public Long getExcluded() {
			return excluded;
		}

		public void setExcluded(Long excluded) {
			this.excluded = excluded;
		}
	}
}
