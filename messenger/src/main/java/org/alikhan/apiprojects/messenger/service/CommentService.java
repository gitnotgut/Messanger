package org.alikhan.apiprojects.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.alikhan.apiprojects.messenger.database.DatabaseClass;
import org.alikhan.apiprojects.messenger.model.Comment;
import org.alikhan.apiprojects.messenger.model.ErrorMessage;
import org.alikhan.apiprojects.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getAllMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		
		ErrorMessage errorMessage = new ErrorMessage("Not Foundddd", 500, "http://javabrains.koushik.org");
		
		Response response = Response.status(Status.NOT_FOUND) 
									.entity(errorMessage)
									.build();
		
		Message message = messages.get(messageId);
		if(message == null) {
		//	throw new WebApplicationException(Status.NOT_FOUND); // If this is uncommented and below one is commented, then we would get status 404, but response wud not be customized.
			throw new WebApplicationException(response);
		}	
		Map<Long, Comment> comments = message.getComments();
		Comment comment = comments.get(commentId);
		if(comment == null) {
//			throw new WebApplicationException(Status.NOT_FOUND); // If this is uncommented,then we would get status 404, but response wud not be customized.
		//	throw new WebApplicationException(response); // For this we have to set the Status at the Response level itself.
			throw new NotFoundException(response); // This will directly set the status code i.e. no need to set the code at the response.
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
