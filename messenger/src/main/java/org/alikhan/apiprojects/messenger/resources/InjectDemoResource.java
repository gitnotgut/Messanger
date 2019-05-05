package org.alikhan.apiprojects.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	/**
	 * Incase of matrix params it would be separated by semicolon(;)
	 * Sample url "http://localhost:8080/messenger/webapi/injectdemo/annotations;param=mvalue"
	 * @param matrixParam
	 * @return
	 */
	@GET
	@Path("annotations")
	public String getAnnotations(@MatrixParam("param") String matrixParam,
								 @HeaderParam("customHeaderValue") String header
							//	 ,@CookieParam("cookieName") String cookieValue
								 ) {
		return "Matrix Param: " + matrixParam + ", customHeaderValue: " + header;
	}
	
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
		
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		
		return "Path: " + path + ", Cookies: " + cookies;
	}
	
	
}
