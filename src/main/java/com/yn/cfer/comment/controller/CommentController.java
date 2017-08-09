package com.yn.cfer.comment.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.comment.model.CommentRequest;
import com.yn.cfer.comment.service.CommentService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.protocol.ResponseMessage;
/**
 * @author user
 */
@Controller
@RequestMapping("comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@RequestMapping(value = "list")
    @ResponseBody
    public ResponseMessage<List<CommentForClient>> list(@RequestBody CommentRequest message) {
    	ResponseMessage<List<CommentForClient>> responseMessage = new ResponseMessage<List<CommentForClient>>();
    	Integer lastId = message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer dynamicsId = message.getDynamicsId();
    	if(lastId == null || dynamicsId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(commentService.getComments(dynamicsId, lastId, count));
    	return responseMessage;
    }
}
