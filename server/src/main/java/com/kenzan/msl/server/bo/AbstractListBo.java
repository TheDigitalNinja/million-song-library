/*
 * Copyright 2015, Kenzan, All rights reserved.
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
    private List<T> boList = new ArrayList<T>();

    public UUID getPagingState() {
        return pagingState;
    }

    public void setPagingState(UUID pagingState) {
        this.pagingState = pagingState;
    }

    public void add(AbstractDao dao) {
        boList.add(convertDaoToBo(dao));
    }

    public List<T> getBoList() {
        return boList;
    }

    public abstract T convertDaoToBo(AbstractDao dao);
}
