package com.sonin.common.modules.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sonin.common.modules.report.entity.FReportItem;
import com.sonin.common.modules.report.mapper.FReportItemMapper;
import com.sonin.common.modules.report.service.IFReportItemService;
import org.springframework.stereotype.Service;


/**
 * 报表数据项管理
 */
@Service
public class FReportItemServiceImpl extends ServiceImpl<FReportItemMapper, FReportItem> implements IFReportItemService {

}
