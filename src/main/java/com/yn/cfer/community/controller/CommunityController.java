/**
 * 
 */
package com.yn.cfer.community.controller;

import java.util.List;

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
    public ResponseMessage<List<DynamicsForClient>> changeMobileByOldNumber(@RequestBody CommunityRequest message) {
    	ResponseMessage<List<DynamicsForClient>> responseMessage = new ResponseMessage<>();
    	Integer lastId = message.getLastId();
    	Integer count = message.getCount() == null ? 20 : message.getCount();
    	Integer orientation = message.getOrientation() == null ? 2 : message.getOrientation();
    	if(lastId == null) {
    		responseMessage.setCode(ErrorCode.ERROR_CODE_MISS_PARAM);
    		responseMessage.setMessage("miss required param");
    		return responseMessage;
    	}
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(dynamicsService.getHotList(lastId, orientation, count));
    	return responseMessage;
    }
}
