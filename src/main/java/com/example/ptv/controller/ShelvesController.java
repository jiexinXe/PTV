package com.example.ptv.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.R;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    /**
     * 添加
     * */
    @PostMapping("/add")
    public Rest addShelves(@RequestParam("num_column")String num_column, @RequestParam("num_row")String num_row, @RequestParam("warehouse_id")String warehouse_id){
        return shelvesService.addShelves(Integer.valueOf(num_column), Integer.valueOf(num_row), warehouse_id);
    }

    @GetMapping("/init")
    public Rest initShelves(){
        return shelvesService.add();
    }
    /**
     * 根据仓库信息获取该仓库所有货架的信息
     * */
    @GetMapping("/info")
    public Rest getShelvesOfWarehouse(@RequestParam("warehouse_id")String warehouse_id,@RequestParam("shelve_id")String shelve_id){
        return shelvesService.getInfo(warehouse_id, shelve_id);
    }

    @GetMapping("/cargo")
    public Rest getCargoOfShelve(@RequestParam("shelve_id")String shelve_id){
        return shelvesService.getCarogoOfShelve(shelve_id);
    }
}
