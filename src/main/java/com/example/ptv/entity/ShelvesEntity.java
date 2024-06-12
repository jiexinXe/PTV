package com.example.ptv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-05-26 21:36:36
 */
@Data
@TableName("shelves")
public class ShelvesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String shelveId;
	/**
	 *
	 * */
	private String numColumn;
	/**
	 * 
	 */
	private String numRow;
	/**
	 *
	 * */
	private String cargoId;
	/**
	 * 
	 */
	private String warehouseId;
	/**
	 *
	 * */
	private String states;

}
