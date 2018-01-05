package com.yn.cfer.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yn.cfer.community.model.Push;

@Repository
public interface PushDao {
    
    /**
     * 查询正在进行中的活动
     * @return
     */
    public List<Push> findDoing();
}
