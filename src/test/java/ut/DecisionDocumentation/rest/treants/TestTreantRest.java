package ut.DecisionDocumentation.rest.treants;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.DecisionDocumentation.rest.treants.TreantRest;
import com.atlassian.DecisionDocumentation.util.ComponentGetter;
import com.atlassian.activeobjects.test.TestActiveObjects;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.issue.issuetype.MockIssueType;
import com.atlassian.jira.issue.link.IssueLinkManager;
import com.atlassian.jira.mock.MockProjectManager;
import com.atlassian.jira.mock.component.MockComponentWorker;
import com.atlassian.jira.mock.issue.MockIssue;
import com.atlassian.jira.project.MockProject;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.google.common.collect.ImmutableMap;

import net.java.ao.EntityManager;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import ut.mocks.MockIssueLinkManager;
import ut.mocks.MockIssueManager;
import ut.testsetup.TestSetUp;

@RunWith(ActiveObjectsJUnitRunner.class)
public class TestTreantRest extends TestSetUp {
	private EntityManager entityManager;  
	
	private TreantRest treantRest;
	
	@Before	
	public void setUp() {
		treantRest=new TreantRest();
		initialisation();		
		new ComponentGetter().init(new TestActiveObjects(entityManager));
		
	}
	
	@Test
	public void testProjectNullIssueKeyNullDepthNull() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameter 'projectKey' has been omitted, please add a valid projectKey")).build().getEntity(),treantRest.getMessage(null, null, null).getEntity());
	}
	
	@Test
	public void testProjectNullIssueKeyFilledDepthNull() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameter 'projectKey' has been omitted, please add a valid projectKey")).build().getEntity(),treantRest.getMessage(null, "3", null).getEntity());
	}
	
	@Test
	public void testProjectNullIssueKeyNullDepthFilled() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameter 'projectKey' has been omitted, please add a valid projectKey")).build().getEntity(),treantRest.getMessage(null, null, "3").getEntity());
	}
	
	@Test
	public void testProjectNullIssueKeyFilledDepthFilled() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameter 'projectKey' has been omitted, please add a valid projectKey")).build().getEntity(),treantRest.getMessage(null, "3", "1").getEntity());
	}
	
	@Test
	public void testProjectExistsIssueKeyNullDepthNull() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameters 'projectKey' and 'issueKey' do not lead to a valid result")).build().getEntity(),treantRest.getMessage("TEST", null, null).getEntity());
	}
	
	@Test
	public void testProjectExistsIssueKeyFilledDepthNull() throws GenericEntityException {
		assertEquals(200,treantRest.getMessage("TEST", "3", null).getStatus());
	}
		
	@Test
	public void testProjectExistsIssueKeyNullDepthFilled() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Query Parameters 'projectKey' and 'issueKey' do not lead to a valid result")).build().getEntity(),treantRest.getMessage("TEST", null, "1").getEntity());
	}
	
	@Test
	public void testProjectExistsIssueKeyFilledDepthFilled() throws GenericEntityException {
		assertEquals(200,treantRest.getMessage("TEST", "3", "1").getStatus());
	}
	
		
	@Test
	public void testProjectNotExistsIssueKeyNullDepthNull() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Can not find Project corresponding to given Query Parameter 'projectKey'")).build().getEntity(),treantRest.getMessage("NotTEST", null, null).getEntity());
	}
	
	@Test
	public void testProjectNotExistsIssueKeyFilledDepthNull() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Can not find Project corresponding to given Query Parameter 'projectKey'")).build().getEntity(),treantRest.getMessage("NotTEST", "3", null).getEntity());
	}
	
	@Test
	public void testProjectNotExistsIssueKeyNullDepthFilled() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Can not find Project corresponding to given Query Parameter 'projectKey'")).build().getEntity(),treantRest.getMessage("NotTEST", null, "1").getEntity());
	}
	
	@Test
	public void testProjectNotExistsIssueKeyFilledDepthFilled() throws GenericEntityException {
		assertEquals(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ImmutableMap.of("error", "Can not find Project corresponding to given Query Parameter 'projectKey'")).build().getEntity(),treantRest.getMessage("NotTEST", "3", "1").getEntity());
	}
	
	@Test
	public void testProjectExistsIssueKeyFilledDepthNoInt() throws GenericEntityException {
		assertEquals(200,treantRest.getMessage("TEST", "3", "Test").getStatus());
	}
}