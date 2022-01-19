package com.sonin.common.modules.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sonin.common.modules.report.entity.ReportHeader;
import com.sonin.common.modules.report.mapper.ReportHeaderMapper;
import com.sonin.common.modules.report.service.IReportHeaderService;
import org.springframework.stereotype.Service;

/**
 * 报表表头
 */
@Service
public class ReportHeaderServiceImpl extends ServiceImpl<ReportHeaderMapper, ReportHeader> implements IReportHeaderService {

}
