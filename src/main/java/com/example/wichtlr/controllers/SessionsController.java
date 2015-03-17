package com.example.wichtlr.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.wichtlr.domain.Participant;
import com.example.wichtlr.domain.WichtelSession;
import com.example.wichtlr.domain.WichtelSessionPair;
import com.example.wichtlr.repositories.ParticipantsRepository;
import com.example.wichtlr.repositories.WichtelSessionsRepository;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Sets;

@Controller
@RequestMapping(value = "/sessions")
public class SessionsController {

	@Autowired
	private WichtelSessionsRepository sessionsRepository;

	@Autowired
	private ParticipantsRepository participantsRepository;
	
	@Autowired
	private MailSender mailSender;

	public SessionsController() {
		super();
	}
	
	@RequestMapping(value = "/{id}/send", method = { RequestMethod.POST }, produces = { MediaType.TEXT_HTML_VALUE })
	public String sendMails(@PathVariable final Long id) {		
		return "redirect:/sessions/";
	}

	@RequestMapping(value = "/", method = { RequestMethod.GET }, produces = { MediaType.TEXT_HTML_VALUE })
	public String all(final Locale locale, final Model model) {
		model.addAttribute("sessions", sessionsRepository.findAll());
		return "sessions";
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String delete(@PathVariable final Long id) {
		sessionsRepository.delete(id);
		return "redirect:/sessions/";
	}

	@RequestMapping(value = "/", method = { RequestMethod.POST }, consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.TEXT_HTML_VALUE })
	public String add(final WichtelSessionDTO newWichtelSession,
			final BindingResult bindingResult) {
		WichtelSession newSession = newWichtelSession.toWichtelSession();
		List<Participant> participants = Lists
				.newArrayList(participantsRepository.findAll());
		while (!newSession.isValid(participants.size())) {
			shuffleSession(newSession, participants);
		}
		sessionsRepository.save(newSession);
		return "redirect:/sessions/";
	}

	private void shuffleSession(WichtelSession session,
			List<Participant> participants) {
		session.setWichtelPairs(Sets.<WichtelSessionPair> newHashSet());
		List<Participant> shuffled = Lists.newArrayList(participants);
		Collections.shuffle(shuffled, new Random(DateTime.now().getMillis()));
		final PeekingIterator<Participant> it = Iterators
				.peekingIterator(Iterators.cycle(shuffled));
		Participant first = it.next();
		Participant current = first;
		while (current.mayBePairedWith(it.peek())) {
			WichtelSessionPair onePair = new WichtelSessionPair(current,
					it.peek());
			session.addWichtelPair(onePair);
			current = it.next();
			if (current.equals(first)) {
				break;
			}
		}
	}

	@RequestMapping(value = "/{id}/pairs", method = { RequestMethod.GET }, produces = { MediaType.TEXT_HTML_VALUE })
	public String getSessionPairs(@PathVariable final Long id,
			final Locale locale, final Model model) {
		WichtelSession session = sessionsRepository.findOne(id);
		model.addAttribute("pairs", session.getWichtelPairs());
		model.addAttribute("sessionName", session.getName());
		return "session";
	}

	@ModelAttribute(value = "newWichtelSession")
	public WichtelSessionDTO newFormObject() {
		return new WichtelSessionDTO();
	}

	public static class WichtelSessionDTO {
		private String name;

		public WichtelSessionDTO() {
		}

		public WichtelSessionDTO(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public WichtelSession toWichtelSession() {
			return new WichtelSession(name);
		}
	}
}
