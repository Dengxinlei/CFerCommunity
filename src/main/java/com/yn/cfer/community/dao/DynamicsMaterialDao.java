package com.yn.cfer.community.dao;

import java.util.List;

import com.yn.cfer.community.model.Dynamics;
import com.yn.cfer.community.model.DynamicsMaterial;

/**
 * @author user
 */
public interface DynamicsMaterialDao {
	public int add(Dynamics dynamics);
	public int addBatch(List<DynamicsMaterial> dynamicsMaterialList);
}
