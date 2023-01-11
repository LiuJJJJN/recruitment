package com.ibm.rms.pojo.dto;

/**
 * 部门查询 dto 类
 *
 * @author 宋月
 * @date 2023-01-01
 */
public class DeptValuePageConditionDto {
	private Integer pageNo;
	private Integer pageSize;
	private String company;
	private String deptName;

	public DeptValuePageConditionDto() {
		super();
	}

	public DeptValuePageConditionDto(Integer pageNo, Integer pageSize, String company, String deptName) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.company = company;
		this.deptName = deptName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "DeptValuePageConditionDto [pageNo=" + pageNo + ", pageSize=" + pageSize + ", company=" + company
				+ ", deptName=" + deptName + "]";
	}

}
