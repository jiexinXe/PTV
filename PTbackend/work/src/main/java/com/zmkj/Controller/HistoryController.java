package com.zmkj.Controller;

import com.zmkj.entity.HistoryEntity;
import com.zmkj.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService; // 注入HistoryService实现

    // 获取所有历史记录
    @GetMapping
    public ResponseEntity<List<HistoryEntity>> getAllHistories() {
        List<HistoryEntity> histories = historyService.getAllHistories();
        return ResponseEntity.ok(histories);
    }

    // 根据ID获取历史记录
    @GetMapping("/{historyId}")
    public ResponseEntity<HistoryEntity> getHistoryById(@PathVariable Integer historyId) {
        HistoryEntity history = historyService.getHistoryById(historyId);
        return ResponseEntity.ok(history);
    }

    // 创建新的历史记录
    @PostMapping
    public ResponseEntity<HistoryEntity> createHistory(@RequestBody HistoryEntity historyEntity) {
        HistoryEntity createdHistory = historyService.createHistory(historyEntity);
        return ResponseEntity.ok(createdHistory);
    }

    // 更新历史记录
    @PutMapping("/{historyId}")
    public ResponseEntity<HistoryEntity> updateHistory(@PathVariable Integer historyId, @RequestBody HistoryEntity updatedHistory) {
        updatedHistory.setHistoryId(historyId);
        HistoryEntity updated = historyService.updateHistory(updatedHistory);
        return ResponseEntity.ok(updated);
    }

    // 删除历史记录
    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Integer historyId) {
        historyService.deleteHistory(historyId);
        return ResponseEntity.noContent().build();
    }
}