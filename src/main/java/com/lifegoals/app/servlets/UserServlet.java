package com.lifegoals.app.servlets;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lifegoals.app.entities.LoginInfo;
import com.lifegoals.app.entities.LoginResult;
import com.lifegoals.app.entities.RegisterResponse;
import com.lifegoals.app.entities.User;
import com.lifegoals.app.service.ServiceLocator;
import com.lifegoals.app.service.helper.EntityValidationHelper;

@Path("/users")
public class UserServlet {

	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@HeaderParam("Token") String token) {
		if (!EntityValidationHelper.tokenValid(token)) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("You don't have access").build();
		}

		/* the token is valid */
		List<User> users = ServiceLocator.get().getUserManagement()
				.getAllUsers();
		return Response.ok(users, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/get/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("param") int id) {
		return ServiceLocator.get().getUserManagement().getUserById(id);
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResult getUserById(LoginInfo info) {
		return ServiceLocator.get().getUserManagement()
				.getLoginResult(info.getName(), info.getPassword());
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public RegisterResponse addUser(User user) {

		RegisterResponse registerResponse = EntityValidationHelper
				.getUserRegisterResponse(user);
		if (registerResponse.isSuccess()) {
			registerResponse.setUser(ServiceLocator.get().getUserManagement()
					.addUser(user));
		}
		return registerResponse;
	}

	@POST
	@Path("/getname")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsernameById(User user) {
		return ServiceLocator.get().getUserManagement()
				.getUsernameByUserId(user);
	}

}
