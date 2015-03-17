package com.example.wichtlr.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

@Entity
@Table(name = "WICHTEL_SESSIONS")
public class WichtelSession {
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "ws_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "WS_ID")
	private Long id;

	@Column(name = "WS_NAME")
	private String name;

	@OneToMany(mappedBy = "session", cascade = { CascadeType.ALL })
	private Set<WichtelSessionPair> wichtelPairs = Sets.newHashSet();

	public WichtelSession() {
	}

	public WichtelSession(String name) {
		Preconditions.checkNotNull(name);
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WichtelSessionPair> getWichtelPairs() {
		return wichtelPairs;
	}

	public void setWichtelPairs(Set<WichtelSessionPair> wichtelPairs) {
		this.wichtelPairs = wichtelPairs;
	}

	public void addWichtelPair(final WichtelSessionPair pair) {
		wichtelPairs.add(pair);
		pair.setSession(this);
	}

	public boolean isValid(int expectedSize) {
		return !wichtelPairs.isEmpty()
				&& Iterables.all(wichtelPairs, WichtelSessionPair.IS_VALID)
				&& wichtelPairs.size() == expectedSize;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;

		WichtelSession session = (WichtelSession) other;

		return Objects.equal(name, session.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return name;
	}
}
