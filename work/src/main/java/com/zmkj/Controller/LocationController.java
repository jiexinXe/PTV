package com.zmkj..controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zmkj..entity.LocationEntity;
import com.zmkj..service.LocationService;
import com.zmkj.common.utils.PageUtils;
import com.zmkj.common.utils.R;



/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-03-11 23:35:28
 */
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * location列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions(":location:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = locationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * location信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions(":location:info")
    public R info(@PathVariable("id") Integer id){
		LocationEntity location = locationService.getById(id);

        return R.ok().put("location", location);
    }

    /**
     * location保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions(":location:save")
    public R save(@RequestBody LocationEntity location){
		locationService.save(location);

        return R.ok();
    }

    /**
     * location修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions(":location:update")
    public R update(@RequestBody LocationEntity location){
		locationService.updateById(location);

        return R.ok();
    }

    /**
     * location删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions(":location:delete")
    public R delete(@RequestBody Integer[] ids){
		locationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
