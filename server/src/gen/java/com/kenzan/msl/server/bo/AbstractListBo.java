/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.bo;

import com.kenzan.msl.server.dao.AbstractDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
public abstract class AbstractListBo<T extends AbstractBo> {
	private UUID pagingState;
	private List<AbstractDao> daoList =  new ArrayList<AbstractDao>();
	private List<T> boList =  new ArrayList<T>();
	private boolean isDaoToBoConversionComplete = false;
	
	public UUID getPagingState() {
		return pagingState;
	}
	
	public void setPagingState(UUID pagingState) {
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
