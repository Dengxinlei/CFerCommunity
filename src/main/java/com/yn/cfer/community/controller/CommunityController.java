/**
 * 
 */
package com.yn.cfer.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yn.cfer.community.model.CommunityRequest;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.FansForClient;
import com.yn.cfer.community.model.Picture;
import com.yn.cfer.community.model.Summary;
import com.yn.cfer.community.service.DynamicsService;
import com.yn.cfer.community.service.MemberAttentionService;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.exceptions.BusinessException;
import com.yn.cfer.web.protocol.ResponseMessage;
import com.yn.cfer.web.servlet.RequestExecuteTimesFilter;

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
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(dynamicsService.getHotList(lastId, orientation, RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), count));
    	return responseMessage;
    }
	
	@RequestMapping(value = "dynamics_publish")
    @ResponseBody
    public ResponseMessage<DynamicsForClient> dynamicsPublish(@RequestBody CommunityRequest message) {
    	ResponseMessage<DynamicsForClient> responseMessage = new ResponseMessage<DynamicsForClient>();
    	String description = message.getDescription();
    	List<String> picUrls = message.getPicUrls();
    	if(StringUtils.isBlank(description) || picUrls == null || picUrls.size() == 0) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	try{
    		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    		responseMessage.setData(dynamicsService.publish(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), description, picUrls));
    	} catch (BusinessException be) {
    		responseMessage.setCode(be.getCode());
    		responseMessage.setMessage(be.getMessage());
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
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	if(dynamicsId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getDetail(dynamicsId, RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), count));
    	return responseMessage;
    }
	@RequestMapping(value = "praise")
    @ResponseBody
    public ResponseMessage<Map<String, Object>> praise(@RequestBody CommunityRequest message) {
    	ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<Map<String, Object>>();
    	Integer dynamicsId = message.getDynamicsId();
    	if(dynamicsId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", dynamicsService.praise(dynamicsId, RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken())));
			responseMessage.setData(map);
		} catch (BusinessException e) {
			e.printStackTrace();
			responseMessage.setCode(e.getCode());
			responseMessage.setMessage(e.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
			responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			responseMessage.setMessage("失败");
		}
    	return responseMessage;
    }
	@RequestMapping(value = "report")
    @ResponseBody
    public ResponseMessage<Boolean> report(@RequestBody CommunityRequest message) {
    	ResponseMessage<Boolean> responseMessage = new ResponseMessage<Boolean>();
    	Integer dynamicsId = message.getDynamicsId();
    	if(dynamicsId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			if(dynamicsService.report(dynamicsId, RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()))) {
				responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			} else {
				responseMessage.setCode(ErrorCode.ERROR_CODE_FAILURE);
			}
			responseMessage.setData(null);
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
    public ResponseMessage<Map<String, Object>> attention(@RequestBody CommunityRequest message) {
    	ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<Map<String, Object>>();
    	Integer attentionMemberId = message.getAttentionMemberId();
    	Integer type = message.getType();
    	if(attentionMemberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		try {
			responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", memberAttentionService.attention(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), attentionMemberId, type));
			responseMessage.setData(map);
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
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getFansList(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), lastId, orientation, count));
    	return responseMessage;
    }
	@RequestMapping(value = "attented_list")
    @ResponseBody
    public ResponseMessage<List<FansForClient>> attented_list(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<FansForClient>> responseMessage = new ResponseMessage<List<FansForClient>>();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getAttentedList(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), lastId, orientation, count));
    	return responseMessage;
    }
	@RequestMapping(value = "search")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> search(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<List<Map<String, Object>>>();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	String name = message.getName();
    	if(StringUtils.isBlank(name)) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.searchLikeByName(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), name, lastId, count));
    	return responseMessage;
    }
	@RequestMapping(value = "attention_list")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> attentionList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<List<Map<String, Object>>>();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.attentionList(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), lastId, orientation, count));
    	return responseMessage;
    }
	@RequestMapping(value = "summary")
    @ResponseBody
    public ResponseMessage<Summary> summary(@RequestBody CommunityRequest message) {
    	ResponseMessage<Summary> responseMessage = new ResponseMessage<Summary>();
    	Integer attentionMemberId = message.getAttentionMemberId();
    	if(attentionMemberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getMemberSummary(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), attentionMemberId));
    	return responseMessage;
    }
	@RequestMapping(value = "personal_dynamics_list")
    @ResponseBody
    public ResponseMessage<List<Map<String, Object>>> getPersonalList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<List<Map<String, Object>>>();
    	Integer memberId = message.getDestMemberId();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
    	if(memberId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.getPersonalList(memberId, lastId, count));
    	return responseMessage;
    }
	@RequestMapping(value = "picture_list")
    @ResponseBody
    public ResponseMessage<List<Picture>> pictureList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<Picture>> responseMessage = new ResponseMessage<List<Picture>>();
    	Integer count = message.getCount() == null ? 10 : message.getCount();
    	Integer lastId = message.getLastId() == null ? -1 : message.getLastId();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.pictureList(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), lastId, count));
    	return responseMessage;
    }
	/**
	 * 推荐关注-列表
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "recommend_attention_list")
    @ResponseBody
    public ResponseMessage<List<FansForClient>> recommendAttentionList(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<FansForClient>> responseMessage = new ResponseMessage<List<FansForClient>>();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.recommendAttentionList(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken())));
    	return responseMessage;
    }
	/**
	 * 根据昵称或者账号搜索需要关注的列表
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "search_attention")
    @ResponseBody
    public ResponseMessage<List<FansForClient>> searchAttention(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<FansForClient>> responseMessage = new ResponseMessage<List<FansForClient>>();
    	String name = message.getName();
    	if(StringUtils.isBlank(name)) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param"); 
    		return responseMessage;
    	}
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setData(dynamicsService.searchAttention(RequestExecuteTimesFilter.getCurrentUserMemberId(message.getToken()), name));
    	return responseMessage;
    }
	
	@RequestMapping(value = "getMemberIdCache", method= RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<Map<String, JSONObject>> getMemberIdCache() {
    	ResponseMessage<Map<String, JSONObject>> responseMessage = new ResponseMessage<Map<String, JSONObject>>();
		responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
		responseMessage.setMessage("memberCount: ["+ RequestExecuteTimesFilter.memberIdCache.size() +"]");
		responseMessage.setData(RequestExecuteTimesFilter.memberIdCache);
    	return responseMessage;
    }
}
