package com.zmkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmkj.entity.HistoryEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface HistoryService extends IService<HistoryEntity> {
    public default List<HistoryEntity> getAllHistories() {
        return null;
    }

    public default  HistoryEntity getHistoryById(int historyId){
        return null;
    }

    public default HistoryEntity createHistory(HistoryEntity historyEntity){
        return null;
    };

    public default HistoryEntity updateHistory(HistoryEntity historyEntity){
        return null;
    };


    public default ResponseEntity<Void> deleteHistory(@PathVariable Integer historyId){
        return null;
    }
}
