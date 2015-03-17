package com.example.wichtlr.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.wichtlr.domain.Participant;
import com.example.wichtlr.repositories.ParticipantsRepository;

@Controller
@RequestMapping(value = "/participants")
public class ParticipantsController {

	@Autowired
	private ParticipantsRepository participantsRepository;

	public ParticipantsController() {
		super();
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET }, produces = { MediaType.TEXT_HTML_VALUE })
	public String all(final Locale locale, final Model model) {
		model.addAttribute("participants", participantsRepository.findAll());
		return "participants";
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String delete(@PathVariable final Long id) {
		participantsRepository.delete(id);
		return "redirect:/participants/";
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET }, produces = { MediaType.TEXT_HTML_VALUE })
	public ModelAndView edit(@PathVariable final Long id) {
		ModelAndView modelAndView = new ModelAndView("participants");
		modelAndView.addObject("editedParticipant", participantsRepository.findOne(id));
		return modelAndView;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String save(@PathVariable final Long id,
			@ModelAttribute final Participant editedParticipant,
			final BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action) {
		if ("save".equalsIgnoreCase(action)) {
			Participant participant = participantsRepository.findOne(id);
			participant.setName(editedParticipant.getName());
			participant.setEmail(editedParticipant.getEmail());
			participantsRepository.save(participant);
		}
		return "redirect:/participants/";
	}

	@RequestMapping(value = "/", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String add(final ParticipantDTO newParticipant,
			final BindingResult bindingResult) {
		participantsRepository.save(newParticipant.toParticipant());
		return "redirect:/participants/";
	}

	@ModelAttribute(value = "newParticipant")
	public ParticipantDTO newFormObject() {
		return new ParticipantDTO();
	}

	public static class ParticipantDTO {
		private String name;

		private String email;

		public ParticipantDTO() {
		}

		public ParticipantDTO(String name, String email) {
			super();
			this.name = name;
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Participant toParticipant() {
			return new Participant(name, email);
		}
	}
}
