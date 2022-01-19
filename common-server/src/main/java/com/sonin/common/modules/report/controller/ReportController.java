package com.sonin.common.modules.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sonin.common.constant.Result;
import com.sonin.common.modules.report.entity.FReportItem;
import com.sonin.common.modules.report.entity.FReportItemv;
import com.sonin.common.modules.report.entity.ReportHeader;
import com.sonin.common.modules.report.service.IFReportItemService;
import com.sonin.common.modules.report.service.IFReportItemvService;
import com.sonin.common.modules.report.service.IReportHeaderService;
import com.sonin.common.tool.query.WrapperFactory;
import com.sonin.common.tool.util.CustomApplicationContext;
import com.sonin.common.tool.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表数据项管理
 */
@Slf4j
@RestController
@RequestMapping("/report/fReportItem")
public class ReportController {

    @Autowired
    private IFReportItemService ifReportItemService;
    @Autowired
    private IFReportItemvService ifReportItemvService;
    @Autowired
    private IReportHeaderService iReportHeaderService;

/*    private static Map<Integer, String> col2NameMap = new LinkedHashMap<>();
    private static Map<String, String> name2TitleMap = new LinkedHashMap<>();

    static {
        String name;

        // 工艺运行报表
        name = "流量:粗格栅流量计（m³）";
        col2NameMap.put(1, name);
        name2TitleMap.put(name, "");

        name = "流量:粗格栅进水量（m³）";
        col2NameMap.put(2, name);
        name2TitleMap.put(name, "");

        name = "流量:调节池液位（m)";
        col2NameMap.put(3, name);
        name2TitleMap.put(name, "");

        name = "流量:生化进水量（m³）";
        col2NameMap.put(4, name);
        name2TitleMap.put(name, "");

        name = "流量:外回流流量计（m³）";
        col2NameMap.put(5, name);
        name2TitleMap.put(name, "");

        name = "流量:外回流流量（m³）";
        col2NameMap.put(6, name);
        name2TitleMap.put(name, "");

        name = "流量:中间水池流量计（m³）";
        col2NameMap.put(7, name);
        name2TitleMap.put(name, "");

        name = "流量:中间水池水量（m³）";
        col2NameMap.put(8, name);
        name2TitleMap.put(name, "");

        name = "流量:排水流量计（m³）";
        col2NameMap.put(9, name);
        name2TitleMap.put(name, "");

        name = "流量:排水量（m³）";
        col2NameMap.put(10, name);
        name2TitleMap.put(name, "");

        name = "流量:东侧内回流量（m³）";
        col2NameMap.put(11, name);
        name2TitleMap.put(name, "");

        name = "流量:西侧内回流量（m³）";
        col2NameMap.put(12, name);
        name2TitleMap.put(name, "");

        name = "生化进水指标:COD(mg/L）";
        col2NameMap.put(13, name);
        name2TitleMap.put(name, "");

        name = "生化进水指标:NH3-N(mg/L）";
        col2NameMap.put(14, name);
        name2TitleMap.put(name, "");

        name = "生化进水指标:TN(mg/L)";
        col2NameMap.put(15, name);
        name2TitleMap.put(name, "");

        name = "生化进水指标:TP(mg/L)";
        col2NameMap.put(16, name);
        name2TitleMap.put(name, "");

        name = "出水指标:COD(mg/L）";
        col2NameMap.put(17, name);
        name2TitleMap.put(name, "");

        name = "出水指标:NH3-N(mg/L）";
        col2NameMap.put(18, name);
        name2TitleMap.put(name, "");

        name = "出水指标:TN(mg/L)";
        col2NameMap.put(19, name);
        name2TitleMap.put(name, "");

        name = "出水指标:TP(mg/L)";
        col2NameMap.put(20, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:厌氧段ORP";
        col2NameMap.put(21, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:缺氧段ORP";
        col2NameMap.put(22, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:好氧DO1";
        col2NameMap.put(23, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:好氧DO2";
        col2NameMap.put(24, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:SV30";
        col2NameMap.put(25, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:东侧内回流比";
        col2NameMap.put(26, name);
        name2TitleMap.put(name, "");

        name = "东侧生化段工艺参数:MLSS";
        col2NameMap.put(27, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:厌氧段ORP";
        col2NameMap.put(28, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:缺氧段ORP";
        col2NameMap.put(29, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:好氧DO1";
        col2NameMap.put(30, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:好氧DO2";
        col2NameMap.put(31, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:SV30";
        col2NameMap.put(32, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:西侧内回流比";
        col2NameMap.put(33, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:MLSS";
        col2NameMap.put(34, name);
        name2TitleMap.put(name, "");

        name = "西侧生化段工艺参数:MLVSS";
        col2NameMap.put(35, name);
        name2TitleMap.put(name, "");

        name = "生化其它参数:气水比";
        col2NameMap.put(36, name);
        name2TitleMap.put(name, "");

        name = "生化其它参数:外回流比";
        col2NameMap.put(37, name);
        name2TitleMap.put(name, "");

        name = "生化其它参数:生化曝气总量（m3）";
        col2NameMap.put(38, name);
        name2TitleMap.put(name, "");

        name = "生化其它参数:日曝气量（m3）";
        col2NameMap.put(39, name);
        name2TitleMap.put(name, "");

        name = "生化其它参数:系统排泥量（m³）";
        col2NameMap.put(40, name);
        name2TitleMap.put(name, "");

        name = "DC曝气总量（m3）";
        col2NameMap.put(41, name);
        name2TitleMap.put(name, "");

        name = "DC日曝气量（m3）";
        col2NameMap.put(42, name);
        name2TitleMap.put(name, "");

        name = "物化排泥m³";
        col2NameMap.put(43, name);
        name2TitleMap.put(name, "");

        name = "水温";
        col2NameMap.put(44, name);
        name2TitleMap.put(name, "");
    }*/

    @PostMapping("/importExcel")
    public Result<Object> importExcelCtrl(HttpServletRequest request) throws Exception {
        Result<Object> result = new Result<>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> multipartFileList = multipartRequest.getFiles("files");
        for (MultipartFile multipartFile : multipartFileList) {
            String filename = multipartFile.getOriginalFilename();
            if (StringUtils.isEmpty(filename)) {
                continue;
            }
            InputStream inputStream = multipartFile.getInputStream();
            Workbook workbook;
            try {
                workbook = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                workbook = new HSSFWorkbook(inputStream);
            }
            analysisReportOgMysql(workbook);
            // 关闭流
            inputStream.close();
        }
        return result;
    }

/*    private void analysisReportOgPg(Workbook workbook) throws ParseException {
        // 获取第0个sheet
        Sheet sheet0 = workbook.getSheetAt(0);
        // report_display: 工艺运行参数报表
        String reportId = "6c5a52ffac43cdf0459c71052f750e26";
        String isFirst = "0";
        // 查询nm指标
        QueryWrapper<ReportHeader> reportHeaderQueryWrapper = new QueryWrapper<>();
        reportHeaderQueryWrapper.eq("report_id", reportId)
                .eq("is_first", isFirst);
        List<ReportHeader> reportHeaderList = iReportHeaderService.list(reportHeaderQueryWrapper);
        Map<String, String> title2NmMap = reportHeaderList.stream().collect(Collectors.toMap(ReportHeader::getTitle, ReportHeader::getKeyIndex));
        boolean isBreak = false;
        for (int row = 3; row < sheet0.getLastRowNum(); row++) {
            if (isBreak) {
                break;
            }
            String dateStr, nextDateStr;
            Row curRow = sheet0.getRow(row);
            Row nextRow = sheet0.getRow(row + 1);
            dateStr = DateUtils.formatDate(curRow.getCell(0).getDateCellValue());
            nextDateStr = DateUtils.formatDate(nextRow.getCell(0).getDateCellValue());
            if (dateStr.split("-").length == 3) {
                if ("".equals(nextDateStr)) {
                    isBreak = true;
                }
                String year = "2021";
                String month = dateStr.split("月")[0];
                String day = dateStr.split("月")[1].split("日")[0];
                long ts = DateUtils.parseTimestamp(year + "-" + month + "-" + day + " 24:00:00", "yyyy-MM-dd HH:mm:ss").getTime() / 1000;
                for (int col = 1; col < curRow.getLastCellNum(); col++) {
                    String nm = title2NmMap.getOrDefault(name2TitleMap.getOrDefault(col2NameMap.getOrDefault(col, ""), ""), "");
                    if (!"".equals(nm)) {
                        curRow.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                        String vStr = curRow.getCell(col).getStringCellValue().replaceFirst("%", "");
                        if (!"".equals(vStr)) {
                            try {
                                double v = Double.parseDouble(vStr);
                                String sql = "update sdcxhgyq_count set v = ${var0} where nm = ${var1} and ts = ${var2}";
                                sql = sql.replaceFirst("\\$\\{var0}", "" + v).replaceFirst("\\$\\{var1}", nm).replaceFirst("\\$\\{var2}", "" + ts);
                                JdbcTemplate jdbcTemplate = (JdbcTemplate) CustomApplicationContext.getBean("pg-db");
                                // jdbcTemplate.execute(sql);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }*/

    /**
     * 运日报与excel报表数据转换填入
     *
     * @param workbook
     */
    private void analysisReportOgMysql(Workbook workbook) {
        // 曹县厂区
        String departId = "f2df9193c8bc4e7a9cef0e4b98dd9e95";
        // 运营日报
        String reportId = "7675958885b94b8df41b7cade8b12e23";

        Map<String, Map<String, String>> date2Code2ValMap = new LinkedHashMap<>();
        // 获取第0个sheet
        Sheet sheet0 = workbook.getSheetAt(0);
        int startRow = 3;
        // 第一个报表转换映射
        Map<Integer, String> firstMap = new LinkedHashMap<Integer, String>() {{
            put(2, "jsl12e8"); // 粗格栅进水量（m³）=> 进水量
            put(9, "clsle111"); // 排水流量计（m³） => 处理水量
            put(43, "whpnaae4"); // 物化排泥m³ => 物化排泥
        }};
        // 第二个报表转换映射
        Map<Integer, String> secondMap = new LinkedHashMap<Integer, String>() {{
            // 1-5 葡萄糖
            put(1, "pttkcbs7c03"); // 库存包数 => 葡萄糖库存包数
            put(2, "pttjryld2b4"); // 今日用量 => 葡萄糖今日用量
            put(3, ""); // 剩余量 =>
            put(4, "pttyjdj6111"); // 单价 => 葡萄糖药剂单价
            put(5, ""); // 吨水成本 =>
            // 6-7 乙酸钠
            put(6, "ysnjryl7dfa"); // 今日用量 => 乙酸钠今日用量
            put(7, ""); // 剩余量 =>
            // 8-13 次氯酸钠
            put(8, ""); // 剩余液位 =>
            put(9, "clsnmmzlfd8f"); // 每米重量 => 次氯酸钠每米重量
            put(10, "clsnjryl1f81"); // 今日用量 => 次氯酸钠今日用量
            put(11, ""); // 剩余量 =>
            put(12, "clsnyjdj5a15"); // 单价 => 次氯酸钠药剂单价
            put(13, ""); // 吨水成本 =>
            // 14-19 液氧
            put(14, "yyjryld530"); // 今日用量 => 液氧今日用量
            put(15, ""); // 剩余液位 =>
            put(16, ""); // 剩余体积 =>
            put(17, ""); // 剩余量 =>
            put(18, "yyyjdj04ca"); // 单价 => 液氧药剂单价
            put(19, ""); // 吨水成本 =>
            // 20-24 PAC
            put(20, ""); // 剩余包数 =>
            put(21, "pacjryl2de2"); // 今日用量 => PAC今日用量
            put(22, ""); // 剩余量 =>
            put(23, "pacyjdja7c8"); // 单价 => PAC药剂单价
            put(24, ""); // 吨水成本 =>
            // 25-26 阴离子PAM
            put(25, "pamyjryl56d3"); // 今日用量	=> PAM阴今日用量
            put(26, ""); // 剩余量 =>
            // 27-28 阳离子PAM
            put(27, "pamyjryl9961"); // 今日用量 => PAM阳今日用量
            put(28, ""); // 剩余量 =>
            // 29-30 药剂成本
            put(29, ""); // 费用
            put(30, ""); // 单耗
            // 31-35 用电统计
            put(31, ""); // 电表读数 =>
            put(32, "hdldb1f"); // 当日用电量 =>
            put(33, ""); // 估算电费 =>
            put(34, ""); // 电单耗 =>
            put(35, ""); // 药剂、电费成本 =>
        }};
        // 遍历第一个报表
        for (int row = startRow; row < sheet0.getLastRowNum(); row++) {
            Row curRow = sheet0.getRow(row);
            if (curRow.getCell(0) == null || curRow.getCell(0).getDateCellValue() == null) {
                break;
            }
            String dateStr = DateUtils.formatDate(curRow.getCell(0).getDateCellValue());
            // 创建内部的Map
            date2Code2ValMap.computeIfAbsent(dateStr, k -> new LinkedHashMap<>());
            for (Map.Entry<Integer, String> entry : firstMap.entrySet()) {
                int col = entry.getKey();
                String code = entry.getValue();
                if (curRow.getCell(col) != null) {
                    curRow.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                    String cellValue = curRow.getCell(col).getStringCellValue();
                    if (!"".equals(code) && !"".equals(cellValue)) {
                        date2Code2ValMap.get(dateStr).put(code, cellValue);
                    }
                }
            }
        }
        // 获取第二个报表的开始行
        for (int row = 0; row < sheet0.getLastRowNum(); row++) {
            Row curRow = sheet0.getRow(row);
            if (curRow.getCell(0) != null) {
                curRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String curCell = curRow.getCell(0).getStringCellValue();
                if ("日期".equals(curCell)) {
                    startRow = row + 2;
                    break;
                }
            }
        }
        // 遍历第二个报表
        for (int row = startRow; row < sheet0.getLastRowNum(); row++) {
            Row curRow = sheet0.getRow(row);
            if (curRow.getCell(0) == null || curRow.getCell(0).getDateCellValue() == null) {
                break;
            }
            String dateStr = DateUtils.formatDate(curRow.getCell(0).getDateCellValue());
            // 创建内部的Map
            date2Code2ValMap.computeIfAbsent(dateStr, k -> new LinkedHashMap<>());
            for (Map.Entry<Integer, String> entry : secondMap.entrySet()) {
                int col = entry.getKey();
                String code = entry.getValue();
                if (curRow.getCell(col) != null) {
                    curRow.getCell(col).setCellType(Cell.CELL_TYPE_STRING);
                    String cellValue = curRow.getCell(col).getStringCellValue();
                    if (!"".equals(code) && !"".equals(cellValue)) {
                        date2Code2ValMap.get(dateStr).put(code, cellValue);
                    }
                }
            }
        }
        for (Map.Entry<String, Map<String, String>> outEntry : date2Code2ValMap.entrySet()) {
            String dateStr = outEntry.getKey();
            Map<String, String> dataMap = outEntry.getValue();
            insertOrUpdateReportData(departId, dateStr, dataMap, reportId);
        }
    }

    private void insertOrUpdateReportData(String departId, String dataTime, Map<String, String> dataMap, String reportId) {
        // 保存itemCode的值,用户后面查询f_report_item的Id
        List<String> itemCodeList = new ArrayList<>();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            itemCodeList.add(entry.getKey());
        }
        QueryWrapper<FReportItem> fReportItemQueryWrapper = new QueryWrapper<>();
        fReportItemQueryWrapper.select("id", "item_code");
        fReportItemQueryWrapper.in("item_code", itemCodeList);
        fReportItemQueryWrapper.eq("report_id", reportId);
        List<FReportItem> fReportItemList = ifReportItemService.list(fReportItemQueryWrapper);
        //保存itemCode和id的对应关系
        Map<String, String> itemCodeIdMap = new HashMap<>();
        fReportItemList.forEach(fReportItem -> {
            itemCodeIdMap.put(fReportItem.getItemCode(), fReportItem.getId());
        });
        //根据日期和厂ID查询数据是否有录入
        QueryWrapper<FReportItemv> fReportItemvQueryWrapper = new QueryWrapper<>();
        fReportItemvQueryWrapper.eq("factory_id", departId);
        fReportItemvQueryWrapper.eq("data_time", dataTime);
        List<FReportItemv> fReportItemvList = ifReportItemvService.list(fReportItemvQueryWrapper);
        String dataId = UUID.randomUUID().toString().replaceAll("-", "");
        if (fReportItemvList.size() > 0) {
            dataId = fReportItemvList.get(0).getDataId();
        }
        //存放itemCodeId的值和FReportItemv对应关系图
        Map<String, FReportItemv> itemCodeFReportItemvMap = new HashMap<>();
        fReportItemvList.forEach(fReportItemv -> {
            itemCodeFReportItemvMap.put(fReportItemv.getReitId(), fReportItemv);
        });
        List<FReportItemv> insertOrupdateFReportItemvList = new ArrayList<>();
        FReportItemv fReportItemv;
        Date createTime = new Date();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            fReportItemv = new FReportItemv();
            if (itemCodeFReportItemvMap.get(itemCodeIdMap.get(entry.getKey())) != null) {
                fReportItemv = itemCodeFReportItemvMap.get(itemCodeIdMap.get(entry.getKey()));
            }
            fReportItemv.setItemValue(entry.getValue());
            fReportItemv.setDataId(dataId);
            fReportItemv.setDataTime(dataTime);
            fReportItemv.setFactoryId(departId);
            fReportItemv.setDelFlag(1);
            fReportItemv.setReitId(itemCodeIdMap.get(entry.getKey()));
            fReportItemv.setCreateTime(createTime);
            insertOrupdateFReportItemvList.add(fReportItemv);
        }
        if (insertOrupdateFReportItemvList.size() > 0) {
            ifReportItemvService.saveOrUpdateBatch(insertOrupdateFReportItemvList);
        }
    }

}
