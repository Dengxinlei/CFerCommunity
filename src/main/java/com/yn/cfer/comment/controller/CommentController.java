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
	@RequestMapping(value = "create")
    @ResponseBody
    public ResponseMessage<Boolean> create(@RequestBody CommentRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer memberId = message.getMemberId();
    	Integer replyMemberId = message.getReplyMemberId();
    	String content = message.getContent();
    	if(dynamicsId == null || memberId == null || StringUtils.isBlank(content)) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	try {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(commentService.create(dynamicsId, memberId, content, replyMemberId));
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
