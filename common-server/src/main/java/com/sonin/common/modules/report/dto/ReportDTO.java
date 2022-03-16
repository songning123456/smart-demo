package com.sonin.common.modules.report.dto;

import lombok.Data;

/**
 * @author sonin
 * @date 2022/1/24 14:43
 */
@Data
public class ReportDTO {

    private String reitId;

    // yyyy-MM-dd
    private String startTime = "2021-01-01";

    // yyyy-MM-dd
    private String endTime = "2021-12-31";

    private String itemValue = "25";

    private String factoryId = "f2df9193c8bc4e7a9cef0e4b98dd9e95";

}
