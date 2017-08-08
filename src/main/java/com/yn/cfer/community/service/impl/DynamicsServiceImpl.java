/**
 * 
 */
package com.yn.cfer.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.cfer.community.dao.DynamicsDao;
import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsForClient;
import com.yn.cfer.community.model.DynamicsMaterial;
import com.yn.cfer.community.service.DynamicsService;

/**
 * @author user
 */
@Service
public class DynamicsServiceImpl implements DynamicsService {
	@Autowired
	private DynamicsDao dynamicsDao;
	@Override
	public List<DynamicsForClient> getHotList(Integer lastId, Integer orientation, Integer count) {
		// TODO Auto-generated method stub
		List<Dynamics> dynamicsList = null;
		if(lastId.intValue() == -1) {
			dynamicsList = dynamicsDao.findDefault(count);
		} else {
			if(orientation.intValue() == 1) {
				dynamicsList = dynamicsDao.findLatest(lastId, count);
			} else if(orientation.intValue() == 2) {
				dynamicsList = dynamicsDao.findHistory(lastId, count);
			}
		}
		return buildDynamicsForClient(dynamicsList);
	}
	private List<DynamicsForClient> buildDynamicsForClient(List<Dynamics> dynamicsList) {
		if(dynamicsList != null && dynamicsList.size() >= 1) {
			List<DynamicsForClient> destination = new ArrayList<>();
			DynamicsForClient dyClient = null;
			for(Dynamics dy : dynamicsList) {
				dyClient = new DynamicsForClient();
				dyClient.setId(dy.getId());
				dyClient.setLocation(dy.getLocation());
				dyClient.setHeadUrl(dy.getHeadUrl());
				dyClient.setUserId(dy.getUserId());
				dyClient.setOwner(dy.getOwner());
				dyClient.setDescription(dy.getDescription());
				dyClient.setCoverUrl(dy.getMaterials().get(0).getUrl());
				List<String> urls = new ArrayList<>();
				for(DynamicsMaterial dm : dy.getMaterials()) {
					urls.add(dm.getUrl());
				}
				dyClient.setPicList(urls);
				destination.add(dyClient);
			}
			return destination;
		}
		return null;
	}
}
