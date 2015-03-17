package com.example.wichtlr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

@Entity
@Table(name = "WICHTEL_SESSION_PAIRS")
public class WichtelSessionPair {
	public static final Predicate<WichtelSessionPair> IS_VALID = new Predicate<WichtelSessionPair>() {

		@Override
		public boolean apply(WichtelSessionPair pair) {
			return pair.isValid();
		}
	};

	@Id
	@SequenceGenerator(name = "seq", sequenceName = "wsp_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "WSP_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "WSP_WS_ID")
	private WichtelSession session;

	@ManyToOne
	@JoinColumn(name = "WSP_PAR_ID")
	private Participant donor;

	@ManyToOne
	@JoinColumn(name = "WSP_OTHER_PAR_ID")
	private Participant presentee;

	public WichtelSessionPair() {
	}

	public WichtelSessionPair(Participant donor, Participant presentee) {
		this.donor = donor;
		this.presentee = presentee;
	}

	public Long getId() {
		return id;
	}

	public WichtelSession getSession() {
		return session;
	}

	public void setSession(WichtelSession session) {
		this.session = session;
	}

	public Participant getDonor() {
		return donor;
	}

	public void setDonor(Participant donor) {
		this.donor = donor;
	}

	public Participant getPresentee() {
		return presentee;
	}

	public void setPresentee(Participant presentee) {
		this.presentee = presentee;
	}

	public boolean isValid() {
		return donor.mayBePairedWith(presentee);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || getClass() != other.getClass())
			return false;

		WichtelSessionPair sessionPair = (WichtelSessionPair) other;

		return Objects.equal(session, sessionPair.session)
				&& Objects.equal(donor, sessionPair.donor)
				&& Objects.equal(presentee, sessionPair.presentee);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(session, donor, presentee);
	}

	@Override
	public String toString() {
		return donor.getName() + " gives a present to " + presentee.getName();
	}
}
