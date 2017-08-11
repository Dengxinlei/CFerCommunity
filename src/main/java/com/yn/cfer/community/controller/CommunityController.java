/**
 * 
 */
package com.yn.cfer.community.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.cfer.community.model.CommunityRequest;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
import com.yn.cfer.web.protocol.ResponseMessage;

/**
 * @author user
 */
@Controller
@RequestMapping(value = "community", method = RequestMethod.POST)
public class CommunityController {
	@Autowired
	private DynamicsService dynamicsService;
	
	@RequestMapping(value = "hot_list")
    @ResponseBody
    public ResponseMessage<List<DynamicsForClient>> hotList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<DynamicsForClient>> responseMessage = new ResponseMessage<List<DynamicsForClient>>();
    	Integer userId = message.getUserId();
    	Integer lastId = message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	if(lastId == null || userId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(dynamicsService.getHotList(lastId, orientation, userId, count));
    	return responseMessage;
    }
	
	@RequestMapping(value = "dynamics_publish")
    @ResponseBody
    public ResponseMessage<List<DynamicsForClient>> dynamicsPublish(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<DynamicsForClient>> responseMessage = new ResponseMessage<List<DynamicsForClient>>();
    	String description = message.getDescription();
    	Integer userId = message.getUserId();
    	List<String> picUrls = message.getPicUrls();
    	if(StringUtils.isBlank(description) || userId == null || picUrls == null || picUrls.size() == 0) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	try{
	    	if(dynamicsService.publish(userId, description, picUrls)) {
	        	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
	    	} else {
	    		responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
	    		responseMessage.setMessage("动态发布失败");
	    	}
    	} catch(Exception e) {
    		e.printStackTrace();
    		responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
    		responseMessage.setMessage("动态发布失败");
    	}
    	return responseMessage;
    }
	@RequestMapping(value = "dynamics_detail")
    @ResponseBody
    public ResponseMessage<Map<String, Object>> dynamicsDetail(@RequestBody CommunityRequest message) {
    	ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<Map<String, Object>>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer userId = message.getUserId();
    	if(dynamicsId == null || userId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getDetail(dynamicsId, userId));
    	return responseMessage;
    }
	@RequestMapping(value = "praise")
    @ResponseBody
    public ResponseMessage<Boolean> praise(@RequestBody CommunityRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer userId = message.getUserId();
    	if(dynamicsId == null || userId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(dynamicsService.praise(dynamicsId, userId));
		} catch (BusinessException e) {
			e.printStackTrace();
			responseMessage.setCode(e.getCode());
			responseMessage.setMessage(e.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			responseMessage.setMessage("点赞失败");
		}
    	return responseMessage;
    }
	@RequestMapping(value = "report")
    @ResponseBody
    public ResponseMessage<Boolean> report(@RequestBody CommunityRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer userId = message.getUserId();
    	if(dynamicsId == null || userId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(dynamicsService.report(dynamicsId, userId));
		} catch (BusinessException e) {
			e.printStackTrace();
			responseMessage.setCode(e.getCode());
			responseMessage.setMessage(e.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			responseMessage.setMessage("举报失败");
		}
    	return responseMessage;
    }
}
