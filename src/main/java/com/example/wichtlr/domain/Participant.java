package com.example.wichtlr.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Entity
@Table(name = "PARTICIPANTS")
public class Participant {
	@Id
	@SequenceGenerator(name = "seq", sequenceName = "par_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "PAR_ID")
	private Long id;

	@Column(name = "PAR_NAME")
	private String name;

	@Column(name = "PAR_EMAIL")
	private String email;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "EXCLUSIONS", joinColumns = { @JoinColumn(name = "EX_PAR_ID") }, inverseJoinColumns = { @JoinColumn(name = "EX_OTHER_PAR_ID") })
	private Collection<Participant> exclusions = Lists.newArrayList();

	public Participant() {
	}

	public Participant(String name, String email) {
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(email);
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean hasExclusions() {
		return exclusions != null && !exclusions.isEmpty();
	}

	public void addExclusion(final Participant excluded) {
		exclusions.add(excluded);
	}

	public void removeExclusion(final Participant wasExcluded) {
		exclusions.remove(wasExcluded);
	}

	public void addMutualExclusion(final Participant excluded) {
		exclusions.add(excluded);
		excluded.addExclusion(this);
	}

	public void removeMutualExclusion(final Participant wasExcluded) {
		exclusions.remove(wasExcluded);
		wasExcluded.removeExclusion(this);
	}

	public boolean mayBePairedWith(final Participant participant) {
		return !exclusions.contains(participant);
	}

	public Collection<Participant> getExclusions() {
		return exclusions;
	}

	public void setExclusions(Collection<Participant> exclusions) {
		this.exclusions = exclusions;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;

		Participant participant = (Participant) other;

		return Objects.equal(email, participant.email)
				&& Objects.equal(name, participant.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email, name);
	}

	@Override
	public String toString() {
		return name;
	}
}
