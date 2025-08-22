package com.mycompany.myapp.service.filter;

import java.time.LocalTime;
import tech.jhipster.service.filter.Filter;

public class LocalTimeFilter extends Filter<LocalTime> {
    public LocalTimeFilter() {}
    public LocalTimeFilter(LocalTimeFilter filter) { super(filter); }
    @Override
    public LocalTimeFilter copy() { return new LocalTimeFilter(this); }
}