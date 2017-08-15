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
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.community.service.MemberAttentionService;
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
	@Autowired
	private MemberAttentionService memberAttentionService;
	
	@RequestMapping(value = "hot_list")
    @ResponseBody
    public ResponseMessage<List<DynamicsForClient>> hotList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<DynamicsForClient>> responseMessage = new ResponseMessage<List<DynamicsForClient>>();
    	Integer memberId = message.getMemberId();
    	Integer lastId = message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	if(lastId == null || memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(dynamicsService.getHotList(lastId, orientation, memberId, count));
    	return responseMessage;
    }
	
	@RequestMapping(value = "dynamics_publish")
    @ResponseBody
    public ResponseMessage<DynamicsForClient> dynamicsPublish(@RequestBody CommunityRequest message) {
    	ResponseMessage<DynamicsForClient> responseMessage = new ResponseMessage<DynamicsForClient>();
    	String description = message.getDescription();
    	Integer memberId = message.getMemberId();
    	List<String> picUrls = message.getPicUrls();
    	if(StringUtils.isBlank(description) || memberId == null || picUrls == null || picUrls.size() == 0) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	try{
    		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    		responseMessage.setData(dynamicsService.publish(memberId, description, picUrls));
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
    	Integer memberId = message.getMemberId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	if(dynamicsId == null || memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getDetail(dynamicsId, memberId, count));
    	return responseMessage;
    }
	@RequestMapping(value = "praise")
    @ResponseBody
    public ResponseMessage<Boolean> praise(@RequestBody CommunityRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer dynamicsId = message.getDynamicsId();
    	Integer memberId = message.getMemberId();
    	if(dynamicsId == null || memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(dynamicsService.praise(dynamicsId, memberId));
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
    	Integer memberId = message.getMemberId();
    	if(dynamicsId == null || memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(dynamicsService.report(dynamicsId, memberId));
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
	
	@RequestMapping(value = "attention")
    @ResponseBody
    public ResponseMessage<Boolean> attention(@RequestBody CommunityRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer memberId = message.getMemberId();
    	Integer attentionMemberId = message.getAttentionMemberId();
    	if(memberId == null || attentionMemberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			responseMessage.setData(memberAttentionService.attention(memberId, attentionMemberId));
		} catch (BusinessException e) {
			e.printStackTrace();
			responseMessage.setCode(e.getCode());
			responseMessage.setMessage(e.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			responseMessage.setMessage("关注失败");
		}
    	return responseMessage;
    }
	@RequestMapping(value = "fans_list")
    @ResponseBody
    public ResponseMessage<List<FansForClient>> fans_list(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<FansForClient>> responseMessage = new ResponseMessage<List<FansForClient>>();
    	Integer memberId = message.getMemberId();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	if(memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getFansList(memberId, lastId, orientation, count));
    	return responseMessage;
    }
	@RequestMapping(value = "attented_list")
    @ResponseBody
    public ResponseMessage<List<FansForClient>> attented_list(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<FansForClient>> responseMessage = new ResponseMessage<List<FansForClient>>();
    	Integer memberId = message.getMemberId();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	if(memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getAttentedList(memberId, lastId, orientation, count));
    	return responseMessage;
    }
	@RequestMapping(value = "search")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> search(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<List<Map<String, Object>>>();
    	Integer memberId = message.getMemberId();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	String name = message.getName();
    	if(memberId == null || StringUtils.isBlank(name)) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.searchLikeByName(memberId, name, lastId, count));
    	return responseMessage;
    }
	@RequestMapping(value = "attention_list")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> attentionList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<List<Map<String, Object>>>();
    	Integer memberId = message.getMemberId();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	if(memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.attentionList(memberId, lastId, orientation, count));
    	return responseMessage;
    }
}
