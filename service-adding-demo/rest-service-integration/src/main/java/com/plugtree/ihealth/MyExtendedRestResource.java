package com.plugtree.ihealth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.GZIP;
import org.jbpm.kie.services.api.FormProviderService;
import org.kie.workbench.common.services.rest.ProjectResource;

@Path("/")
@Named
@GZIP
@ApplicationScoped
public class MyExtendedRestResource extends ProjectResource {

	@Inject
	private FormProviderService formProviderService;
	
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/forms/task/{taskId}")
    public Response getFormByTaskId(@PathParam("taskId") Long taskId) {
        String result = formProviderService.getFormDisplayTask(taskId);
        if (result == null) {
        	return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/forms/process/{deployId}/{processId}")
    public Response getFormByTaskId(@PathParam("deployId") String deployId, @PathParam("processId") String processId) {
        String result = formProviderService.getFormDisplayProcess(deployId, processId);
        if (result == null) {
        	return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(result).build();
    }
}
