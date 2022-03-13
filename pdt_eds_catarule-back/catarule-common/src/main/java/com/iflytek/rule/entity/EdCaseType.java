package com.iflytek.rule.entity;

import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;

@Table(name = "ed_case_type")
public class EdCaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型编码（如‘00’ 两位表示一个级别）
     */
    @ColumnType(column = "code",jdbcType = JdbcType.VARCHAR)
    private String code;

    /**
     * 案件类型名
     */
    @ColumnType(column = "name",jdbcType = JdbcType.VARCHAR)
    private String name;

    /**
     * 三方案件类型
     */
//    @Column(name = "third_code")
    @ColumnType(column = "third_code",jdbcType = JdbcType.VARCHAR)
    private String thirdCode;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取类型编码（如‘00’ 两位表示一个级别）
     *
     * @return code - 类型编码（如‘00’ 两位表示一个级别）
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置类型编码（如‘00’ 两位表示一个级别）
     *
     * @param code 类型编码（如‘00’ 两位表示一个级别）
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取案件类型名
     *
     * @return name - 案件类型名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置案件类型名
     *
     * @param name 案件类型名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

	public EdCaseType(String code, String name, String thirdCode) {
		super();
		this.code = code;
		this.name = name;
		this.thirdCode = thirdCode;
	}

	public EdCaseType(Integer id, String code, String name, String thirdCode) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.thirdCode = thirdCode;
	}
}
