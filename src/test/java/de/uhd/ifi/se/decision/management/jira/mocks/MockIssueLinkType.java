package de.uhd.ifi.se.decision.management.jira.mocks;

import java.sql.Timestamp;

import org.ofbiz.core.entity.GenericValue;

import com.atlassian.jira.issue.link.IssueLinkType;

public class MockIssueLinkType implements IssueLinkType {
	private Long id;
	private String name;

	public MockIssueLinkType(Long id) {
		this.id = id;
	}

	@Override
	public GenericValue getGenericValue() {
		return null;
	}

	@Override
	public Long getLong(String arg0) {
		return null;
	}

	@Override
	public String getString(String arg0) {
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) {
		return null;
	}

	@Override
	public void store() {
		// method empty since not used for testing
	}

	@Override
	public int compareTo(IssueLinkType issueLinkType) {
		if (name.equals(issueLinkType.getName())) {
			return 1;
		}
		return 0;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getInward() {
		return null;
	}

	@Override
	public String getName() {
		name = "Test";
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getOutward() {
		return null;
	}

	@Override
	public String getStyle() {
		return null;
	}

	@Override
	public boolean isSubTaskLinkType() {
		return false;
	}

	@Override
	public boolean isSystemLinkType() {
		return false;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MockIssueLinkType other = (MockIssueLinkType) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
            return other.name == null;
		} else return name.equals(other.name);
    }
}