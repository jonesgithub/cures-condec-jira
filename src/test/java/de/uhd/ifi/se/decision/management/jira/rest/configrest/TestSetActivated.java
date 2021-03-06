package de.uhd.ifi.se.decision.management.jira.rest.configrest;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.atlassian.jira.mock.servlet.MockHttpServletRequest;

import net.java.ao.test.junit.ActiveObjectsJUnitRunner;

@RunWith(ActiveObjectsJUnitRunner.class)
public class TestSetActivated extends TestConfigSuper {
	@Test
	public void testSetActivatedRequestNullProjectKeyNullIsActivatedNull() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, null, null).getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyNullIsActivatedTrue() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, null, "true").getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyNullIsActivatedFalse() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, null, "false").getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyExistsIsActivatedNull() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "TEST", null).getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyExistsIsActivatedTrue() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "TEST", "true").getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyExistsIsActivatedFalse() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "TEST", "false").getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyDoesNotExistIsActivatedNull() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "NotTEST", null).getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyDoesNotExistIsActivatedTrue() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "NotTEST", "true").getEntity());
	}

	@Test
	public void testSetActivatedRequestNullProjectKeyDoesNotExistIsActivatedFalse() {
		assertEquals(getBadRequestResponse(INVALID_REQUEST).getEntity(),
				configRest.setActivated(null, "NotTEST", "false").getEntity());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyNullIsActivatedNull() {
		assertEquals(getBadRequestResponse(INVALID_PROJECTKEY).getEntity(),
				configRest.setActivated(request, null, null).getEntity());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyNullIsActivatedTrue() {
		assertEquals(getBadRequestResponse(INVALID_PROJECTKEY).getEntity(),
				configRest.setActivated(request, null, "true").getEntity());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyNullIsActivatedFalse() {
		assertEquals(getBadRequestResponse(INVALID_PROJECTKEY).getEntity(),
				configRest.setActivated(request, null, "false").getEntity());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyExistsIsActivatedNull() {
		assertEquals(getBadRequestResponse(INVALID_ACTIVATION).getEntity(),
				configRest.setActivated(request, "TEST", null).getEntity());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyExistsIsActivatedTrue() {
		assertEquals(Response.ok().build().getClass(), configRest.setActivated(request, "TEST", "true").getClass());
	}

	@Test
	public void testSetActivatedRequestExistsProjectKeyExistsIsActivatedFalse() {
		assertEquals(Response.ok().build().getClass(), configRest.setActivated(request, "TEST", "false").getClass());
	}

	@Test
	public void testSetActivatedUserUnauthorized() {
		HttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("WithFails", true);
		request.setAttribute("NoFails", false);
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(),
				configRest.setActivated(request, "NotTEST", "false").getStatus());
	}

	@Test
	public void testSetActivatedUserNull() {
		HttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("WithFails", false);
		request.setAttribute("NoFails", false);
		request.setAttribute("SysAdmin", false);
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(),
				configRest.setActivated(request, "NotTEST", "false").getStatus());
	}
}
