package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface CarDao extends BaseMapper<Car> {

    @Select("SELECT * FROM car WHERE status = #{status}")
    List<Car> selectCarsByStatus(@Param("status") int status);

    @Update("UPDATE car SET status = #{status}, current_task = #{currentTask} WHERE id = #{id}")
    void updateCarStatusAndTask(@Param("id") int id, @Param("status") int status, @Param("currentTask") String currentTask);
}
