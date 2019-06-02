package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "手机号")
    private String phone_num;
    @Excel(name = "密码")
    private String password;
    @ExcelIgnore
    private String salt;
    @Excel(name = "头像", type = 2, width = 40, height = 20)
    private String head_pic;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "法号")
    private String dharma;
    @Excel(name = "性格")
    private String sex;
    @Excel(name = "省")
    private String province;
    @Excel(name = "市")
    private String city;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "状态")
    private String status;
    @ExcelIgnore
    private String guru_id;
    @Excel(name = "创建时间", format = "yyyy-MM-dd")
    private Date create_date;
    @ExcelIgnore
    private Guru guru;

}
