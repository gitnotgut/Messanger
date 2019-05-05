package org.alikhan.apiprojects.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.alikhan.apiprojects.messenger.model.Message;
import org.alikhan.apiprojects.messenger.service.MessageService;

//@Path("/messages")
//@Consumes(MediaType.APPLICATION_JSON)// Instead of writing these 2 before every method, we're simply writing here.
//@Produces(MediaType.APPLICATION_JSON)
public class MessageResource_NonBean {
	
	MessageService messageService = new MessageService();
	
	/**
	 * Here if we put if(year > 0) is satisfied, then all messages will be returned. So only one of the if conditions will be executed.
	 * Hence we NEED TO CHANGE the implementation.
	 * @param year
	 * @param start
	 * @param size
	 * @return
	 */
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size) { // Note that year value would be zero if we dont pass any query parameter in the uri.
		
		if(year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if(start >= 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		
		return messageService.getAllMessages();
	}
	
//	@GET
//	@Path("/{id}")
	public Message getMessage(@PathParam("id") long id) {
		return messageService.getMessage(id);
	}
	
//	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
//	@PUT
//	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
//	@DELETE
//	@Path("/{messageId}")
	public Message removeMessage(@PathParam("messageId") long id) {
		return messageService.removeMessage(id);
	}
	
}
