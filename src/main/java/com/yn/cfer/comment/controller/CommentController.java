package com.yn.cfer.comment.controller;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.cfer.comment.model.CommentForClient;
import com.yn.cfer.comment.model.CommentRequest;
import com.yn.cfer.comment.service.CommentService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
import com.yn.cfer.web.protocol.ResponseMessage;
import com.yn.cfer.web.servlet.RequestExecuteTimesFilter;
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
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	if(dynamicsId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(commentService.getComments(dynamicsId, lastId, count, orientation));
    	return responseMessage;
    }
	@RequestMapping(value = "create")
    @ResponseBody
    public ResponseMessage<CommentForClient> create(@RequestBody CommentRequest message) {
    	ResponseMessage<CommentForClient> responseMessage = new ResponseMessage<CommentForClient>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer replyUserId = message.getReplyUserId();
    	String content = message.getContent();
    	if(dynamicsId == null || StringUtils.isBlank(content)) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	try {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(commentService.create(dynamicsId, RequestExecuteTimesFilter.getCurrentUserId(message.getToken()), content, replyUserId));
		} catch (BusinessException e) {
			e.printStackTrace();
			responseMessage.setCode(e.getCode());
			responseMessage.setMessage(e.getMessage());
		} catch(Exception ex) {
			responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			responseMessage.setMessage("发表评论失败");
		}
    	return responseMessage;
    }
}
