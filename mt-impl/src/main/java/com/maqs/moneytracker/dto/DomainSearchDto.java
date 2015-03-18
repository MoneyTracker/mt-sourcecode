package com.maqs.moneytracker.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.maqs.moneytracker.common.paging.Page;

@JsonInclude(Include.NON_NULL)
public class DomainSearchDto {

	public static final String TRAN_TYPE = "tranType";

	public static final String ON_DATE = "onDate";

	public static final String ACCT_ID = "accountId";

	public static final String CAT_ID = "categoryId";

	private String type;

	private List<Long> categoryIds;

	private List<Long> accountIds;
	
	private Page page;

	public DomainSearchDto() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(List<Long> accountIds) {
		this.accountIds = accountIds;
	}

	public void addCategoryId(Long id) {
		if (categoryIds == null) {
			categoryIds = new ArrayList<Long>();
		}
		categoryIds.add(id);
	}
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
