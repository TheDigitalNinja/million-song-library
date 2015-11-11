/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.bo;

import com.kenzan.msl.server.dao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author billschwanitz
 */
public abstract class AbstractListBo<T extends AbstractBo> {
	private String pagingState;
	private List<AbstractDao> daoList =  new ArrayList<AbstractDao>();
	private List<T> boList =  new ArrayList<T>();
	private boolean isDaoToBoConversionComplete = false;
	
	public String getPagingState() {
		return pagingState;
	}
	
	public void setPagingState(String pagingState) {
		this.pagingState = pagingState;
	}
	
	protected List<AbstractDao> getDaoList() {
		return daoList;
	}
	
	public void addDao(AbstractDao dao) {
		daoList.add(dao);
	}
	
	public List<T> getBoList() {
		if (!isDaoToBoConversionComplete) {
			convertDaosToBos();
			isDaoToBoConversionComplete = true;
		}
		return boList;
	}
	
	public void addBo(T bo) {
		boList.add(bo);
	}
	
	public abstract void convertDaosToBos();
}
