package org.alikhan.apiprojects.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.alikhan.apiprojects.messenger.model.Message;
import org.alikhan.apiprojects.messenger.resources.beans.MessageFilterBean;
import org.alikhan.apiprojects.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)// Instead of writing these 2 before every method, we're simply writing here.
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	/**
	 * Here if we put if(year > 0) is satisfied, then all messages will be returned. So only one of the if conditions will be executed.
	 * Hence we NEED TO CHANGE the implementation.
	 * @param year
	 * @param start
	 * @param size
	 * @return
	 */
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) { 
		
		System.out.println("JSON Method called");
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) { 
		
		System.out.println("XML Method called");
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{id}")
	public Message getMessage(@PathParam("id") long id,
							  @Context UriInfo uriInfo
							) {
		
		Message message = messageService.getMessage(id);
		String uri = getUriForSelf(uriInfo, message);
		String profile = getUriForProfile(uriInfo, message);
		String comments = getUriForComments(uriInfo, message);
		message.addLink(uri, "self");
		message.addLink(profile, "profile");
		message.addLink(comments, "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		
		URI uri = uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)
						.path(MessageResource.class, "getCommentResource") //this will append the @Path value of the method(2nd arg) present in the resource class(1st arg)
						.path(CommentResource.class)
						.resolveTemplate("messageId", message.getId()) // This will replace the messageId value with message.getId()
						.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
						.path(ProfileResource.class) // path method here will basically take the @Path value(of the resource) and append(initially put '/') to the baseuri.
						.path(message.getAuthor())
						.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
			.path(MessageResource.class)
			.path(Long.toString(message.getId()))
			.build()
			.toString();
		return uri;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {	
	//	return messageService.addMessage(message); // Inorder set our own status code, we've to go by the below way.
		Message newMessage = messageService.addMessage(message);
	/*	
		Response response = Response.status(Status.CREATED)
									.entity(newMessage)
									.build();
	
		Response response = Response.created(new URI("/messenger/webapi/messages/" + newMessage.getId()))
									.entity(newMessage)
									.build();
	*/
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		Response response = Response.created(uri)
									.entity(newMessage)
									.build();
		
		return response;
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message removeMessage(@PathParam("messageId") long id) {
		return messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
