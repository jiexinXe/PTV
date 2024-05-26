package com.example.ptv.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-26 21:36:36
 */
@RestController
@RequestMapping("authserver/shelves")
public class ShelvesController {
    @Autowired
    private ShelvesService shelvesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("authserver:shelves:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shelvesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("authserver:shelves:info")
    public R info(@PathVariable("id") Integer id){
		ShelvesEntity shelves = shelvesService.getById(id);

        return R.ok().put("shelves", shelves);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("authserver:shelves:save")
    public R save(@RequestBody ShelvesEntity shelves){
		shelvesService.save(shelves);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("authserver:shelves:update")
    public R update(@RequestBody ShelvesEntity shelves){
		shelvesService.updateById(shelves);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("authserver:shelves:delete")
    public R delete(@RequestBody Integer[] ids){
		shelvesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
