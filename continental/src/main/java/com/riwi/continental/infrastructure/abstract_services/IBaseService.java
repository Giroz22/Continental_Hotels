package com.riwi.continental.infrastructure.abstract_services;

import org.springframework.data.domain.Page;

public interface IBaseService<RQ, RS, ID> {
    public Page<RS> getAll(int page, int size);
    public RS findById(ID id);
    public RS create(RQ entity);
    public RS update(RQ entity);
    public void delete(ID id);
}
