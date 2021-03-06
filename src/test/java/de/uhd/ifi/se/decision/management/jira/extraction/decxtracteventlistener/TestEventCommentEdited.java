package de.uhd.ifi.se.decision.management.jira.extraction.decxtracteventlistener;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.comments.MutableComment;

import de.uhd.ifi.se.decision.management.jira.TestSetUpWithIssues;
import de.uhd.ifi.se.decision.management.jira.model.DecisionKnowledgeElement;
import de.uhd.ifi.se.decision.management.jira.model.KnowledgeType;
import net.java.ao.test.jdbc.Data;
import net.java.ao.test.jdbc.NonTransactional;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;

@RunWith(ActiveObjectsJUnitRunner.class)
@Data(TestSetUpWithIssues.AoSentenceTestDatabaseUpdater.class)
public class TestEventCommentEdited extends TestSetUpEventListener {

	private Comment createAndChangeComment(String inputBody, String changedBody, String outputBody) {
		Comment comment = createComment(inputBody);
		((MutableComment) comment).setBody(changedBody);

		IssueEvent issueEvent = createIssueEvent(comment, EventType.ISSUE_COMMENT_EDITED_ID);
		listener.onIssueEvent(issueEvent);

		assertTrue(isCommentExistent(outputBody));
		return comment;
	}

	private Comment createAndChangeComment(String commentBody, String changedBody) {
		return createAndChangeComment(commentBody, changedBody, changedBody);
	}

	@Test
	@NonTransactional
	public void testNoCommentContained() {
		Comment comment = createAndChangeComment("", "");
		DecisionKnowledgeElement element = getFirstElementInComment(comment);
		assertNull(element);
	}

	@Test
	@NonTransactional
	public void testRationaleTag() {
		Comment comment = createAndChangeComment("{issue}This is a very severe issue.{issue}",
				"{decision}This is the decision.{decision}");
		DecisionKnowledgeElement element = getFirstElementInComment(comment);
		assertTrue(element.getDescription().equals("This is the decision."));
		assertTrue(element.getType() == KnowledgeType.DECISION);
	}

	@Test
	@NonTransactional
	public void testExcludedTag() {
		Comment comment = createAndChangeComment("{code}public static class{code}",
				"{noformat}This is a logger output.{notformat}");
		DecisionKnowledgeElement element = getFirstElementInComment(comment);
		assertTrue(element.getDescription().equals("{noformat}This is a logger output.{notformat}"));
		assertTrue(element.getType() == KnowledgeType.OTHER);
	}

	@Test
	@NonTransactional
	public void testRationaleIcon() {
		Comment comment = createAndChangeComment("(!)This is a very severe issue.", "(/)This is the decision.",
				"{decision}This is the decision.{decision}");
		DecisionKnowledgeElement element = getFirstElementInComment(comment);
		assertTrue(element.getDescription().equals("This is the decision."));
		assertTrue(element.getType() == KnowledgeType.DECISION);
	}
}
