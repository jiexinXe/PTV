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

import com.zmkj..entity.WarehouseEntity;
import com.zmkj..service.WarehouseService;
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
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    /**
     * warehouse列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions(":warehouse:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = warehouseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * warehouse信息
     */
    @RequestMapping("/info/{whid}")
    //@RequiresPermissions(":warehouse:info")
    public R info(@PathVariable("whid") Integer whid){
		WarehouseEntity warehouse = warehouseService.getById(whid);

        return R.ok().put("warehouse", warehouse);
    }

    /**
     * warehouse保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions(":warehouse:save")
    public R save(@RequestBody WarehouseEntity warehouse){
		warehouseService.save(warehouse);

        return R.ok();
    }

    /**
     * warehouse修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions(":warehouse:update")
    public R update(@RequestBody WarehouseEntity warehouse){
		warehouseService.updateById(warehouse);

        return R.ok();
    }

    /**
     * warehouse删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions(":warehouse:delete")
    public R delete(@RequestBody Integer[] whids){
		warehouseService.removeByIds(Arrays.asList(whids));

        return R.ok();
    }

}
