/**
 * 
 */
package com.yn.cfer.common.sts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.cfer.common.sts.model.StsInfo;
import com.yn.cfer.common.sts.utils.StsUtil;
import com.yn.cfer.community.model.CommunityRequest;
import com.yn.cfer.web.common.constant.ErrorCode;
import com.yn.cfer.web.protocol.ResponseMessage;

/**
 * @author user
 *
 */
@Controller
@RequestMapping("sts")
public class StsController {
	@RequestMapping(value = "info")
    @ResponseBody
    public ResponseMessage<StsInfo> getInfo(@RequestBody CommunityRequest message) {
    	ResponseMessage<StsInfo> responseMessage = new ResponseMessage<StsInfo>();
    	responseMessage.setCode(ErrorCode.ERROR_CODE_SUCCESS);
    	responseMessage.setData(StsUtil.getInfo()); //
    	return responseMessage;
    }
}
