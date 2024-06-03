package com.example.ptv.service.Imp;

import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.warehouseDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.entity.warehouse;
import com.example.ptv.service.ShelvesService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Query;
import com.example.ptv.utils.Rest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("shelvesService")
public class ShelvesServiceImpl extends ServiceImpl<ShelvesDao, ShelvesEntity> implements ShelvesService {

    @Autowired
    ShelvesDao shelvesdao;
    @Autowired
    CargoDao cargodao;
    @Autowired
    warehouseDao warehousedao;

    // 只有该方法测试一直失败 不懂
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShelvesEntity> page = this.page(
                new Query<ShelvesEntity>().getPage(params),
                new QueryWrapper<ShelvesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Rest addShelves(Integer num_of_row, Integer num_of_column, String warehouse_id) {
        return null;
    }

    @Override
    public Rest add() {
        for (int n=1;n<=4;n++)
            for(int i = 1;i<=10;i++)
                for(int j = 1;j<=10;j++) {
                    ShelvesEntity shelves = new ShelvesEntity();
                    shelves.setWarehouseId("1");
                    shelves.setNumRow(String.valueOf(j));
                    shelves.setNumColumn(String.valueOf(i));
                    shelves.setShelveId(String.valueOf(n));
                    shelvesdao.insert(shelves);
                    }
        return new Rest(Code.rc200.getCode(), "货架添加成功");
    }

    @Override
    public Rest getInfo(String warehouse_id, String shelve_id) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("warehouse_id", warehouse_id)
                .eq("shelve_id", shelve_id);
        List<ShelvesEntity> shelves = shelvesdao.selectList(shelveswrapper);

        return new Rest(Code.rc200.getCode(), shelves, "货架信息");
    }

    @Override
    public Rest getCarogoOfShelve(String warehouse_id, String shelve_id, String row, String column) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("warehouse_id", warehouse_id)
                .eq("shelve_id", shelve_id)
                .eq("num_row", row)
                .eq("num_column", column);
        ShelvesEntity shelves = shelvesdao.selectOne(shelveswrapper);
        String cargo_id = shelves.getCargoId();
        Cargo cargo = cargodao.selectById(cargo_id);

        if(Objects.isNull(cargo))
            return new Rest(Code.rc400.getCode(), "该位置没有货物或货物信息异常");
        return new Rest(Code.rc200.getCode(), cargo, "该货架的货物信息");
    }
}