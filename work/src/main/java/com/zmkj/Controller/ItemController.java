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

import com.zmkj..entity.ItemEntity;
import com.zmkj..service.ItemService;
import com.zmkj.common.utils.PageUtils;
import com.zmkj.common.utils.R;utils



/**
 * 
 *
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-03-11 23:35:28
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * item列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions(":item:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = itemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * item信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions(":item:info")
    public R info(@PathVariable("id") Integer id){
		ItemEntity item = itemService.getById(id);

        return R.ok().put("item", item);
    }

    /**
     * item保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions(":item:save")
    public R save(@RequestBody ItemEntity item){
		itemService.save(item);

        return R.ok();
    }

    /**
     * item修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions(":item:update")
    public R update(@RequestBody ItemEntity item){
		itemService.updateById(item);

        return R.ok();
    }

    /**
     * item删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions(":item:delete")
    public R delete(@RequestBody Integer[] ids){
		itemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
