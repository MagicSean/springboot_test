package com.iemij.test.mapper;

import com.iemij.test.domain.Test;
import tk.mybatis.mapper.common.Mapper;

public interface TestMapper extends Mapper<Test> {
    int updateNum();
}
