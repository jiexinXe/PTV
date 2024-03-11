package com.zmkj..entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zjx
 * @email zjxlijo@gmail.com
 * @date 2024-03-11 23:35:28
 */
@Data
@TableName("warehouse")
public class WarehouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer whid;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private BigDecimal maxcontent;
	/**
	 * 
	 */
	private BigDecimal nowcontent;

}
