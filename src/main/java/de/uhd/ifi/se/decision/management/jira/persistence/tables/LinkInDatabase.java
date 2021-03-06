package de.uhd.ifi.se.decision.management.jira.persistence.tables;

import java.sql.SQLException;

import de.uhd.ifi.se.decision.management.jira.model.Link;
import net.java.ao.RawEntity;
import net.java.ao.schema.AutoIncrement;
import net.java.ao.schema.PrimaryKey;
import net.java.ao.schema.Table;

/**
 * Interface for links used to link decision knowledge elements of various
 * documentation locations, e.g. JIRA issue comments and commit messages.
 * Determines which table columns are used for object relational mapping of link
 * objects to the database.
 * 
 * @see Link
 */
@Table("CondecLink")
public interface LinkInDatabase extends RawEntity<Integer> {
	@AutoIncrement
	@PrimaryKey("ID")
	long getId();

	void setId(long id);

	String getType();

	void setType(String type);

	long getSourceId();

	void setSourceId(long id);

	long getDestinationId();

	void setDestinationId(long id);

	String getSourceDocumentationLocation();

	void setSourceDocumentationLocation(String documentationLocation);

	String getDestDocumentationLocation();

	void setDestDocumentationLocation(String documentationLocation);
	
	static boolean deleteLink(LinkInDatabase linkToDelete) {
		try {
			linkToDelete.getEntityManager().delete(linkToDelete);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
