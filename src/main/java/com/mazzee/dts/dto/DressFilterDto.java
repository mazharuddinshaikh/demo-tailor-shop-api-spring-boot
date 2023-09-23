/**
 * 
 */
package com.mazzee.dts.dto;

import java.util.List;

import com.mazzee.dts.entity.DressType;

/**
 * @author Admin
 *
 */
public class DressFilterDto {

	private String filterType;
	private String filterMessage;
	private List<DressType> typeList;

	public DressFilterDto() {
		super();
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFilterMessage() {
		return filterMessage;
	}

	public void setFilterMessage(String filterMessage) {
		this.filterMessage = filterMessage;
	}

	public List<DressType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DressType> typeList) {
		this.typeList = typeList;
	}

}
