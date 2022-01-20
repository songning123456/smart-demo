package com.sonin.common.modules.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sonin.common.constant.Result;
import com.sonin.common.modules.report.entity.FReportItem;
import com.sonin.common.modules.report.entity.FReportItemv;
import com.sonin.common.modules.report.service.IFReportItemService;
import com.sonin.common.modules.report.service.IFReportItemvService;
import com.sonin.common.tool.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

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
            put(2, "jsl12e8"); // C: 粗格栅进水量（m³）=> 进水量
            put(10, "clsle111"); // K: 排水量（m³） => 处理水量
            put(49, "whpnaae4"); // AX: 物化排泥m³ => 物化排泥
            // put(43, "whpnaae4"); // AR: 物化排泥m³ => 物化排泥
        }};
        // 第二个报表转换映射
        Map<Integer, String> secondMap = new LinkedHashMap<Integer, String>() {{
            // 葡萄糖
            put(1, "pttkcbs7c03"); // B: 库存包数 => 葡萄糖库存包数
            put(2, "pttjryld2b4"); // 今日用量 => 葡萄糖今日用量
            put(3, ""); // 剩余量 =>
            put(4, "pttyjdj6111"); // 单价 => 葡萄糖药剂单价
            put(5, ""); // 吨水成本 =>
            // 乙酸钠
            put(6, "ysnsybs5711"); // 剩余包数 (new)
            put(7, "ysnjryl7dfa"); // 今日用量 => 乙酸钠今日用量
            put(8, ""); // 剩余量 =>
            put(9, "ysnyjdj529d"); // 单价 =>
            put(10, ""); // 吨水成本 =>
            // 次氯酸钠
            put(11, "clsnsyywfb10"); // 剩余液位 => (new)
            put(12, "clsnmmzlfd8f"); // 每米重量 => 次氯酸钠每米重量
            put(13, "clsnjryl1f81"); // 今日用量 => 次氯酸钠今日用量
            put(14, ""); // 剩余量 =>
            put(15, "clsnyjdj5a15"); // 单价 => 次氯酸钠药剂单价
            put(16, ""); // 吨水成本 =>
            // 除氟剂
            put(17, "qfjsyyw35f9"); // 剩余液位
            put(18, "qfjmmzl4f92"); // 每米重量
            put(19, "qfjjryl8d51"); // 今日用量
            put(20, ""); // 剩余量
            put(21, "qfjyjdj75bd"); // 单价
            put(22, ""); // 吨水成本
            // 液氧
            put(23, "yyjryld530"); // 今日用量 => 液氧今日用量
            put(24, "yysyyw9bdc"); // 剩余液位 => (new)
            put(25, ""); // 剩余体积 =>
            put(26, ""); // 剩余量 =>
            put(27, "yyyjdj04ca"); // 单价 => 液氧药剂单价
            put(28, ""); // 吨水成本 =>
            // PAC
            put(29, "pacsybs24ae"); // 剩余包数 =>
            put(30, "pacjryl2de2"); // 今日用量 => PAC今日用量
            put(31, ""); // 剩余量 =>
            put(32, "pacyjdja7c8"); // 单价 => PAC药剂单价
            put(33, ""); // 吨水成本 =>
            // 阴离子PAM
            put(34, "pamyjryl56d3"); // 今日用量	=> PAM阴今日用量
            put(35, "pamysyl2517"); // 剩余量 =>
            // 阳离子PAM
            put(36, "pamyjryl9961"); // 今日用量 => PAM阳今日用量
            put(37, "pamysyl7442"); // 剩余量 =>
            // 药剂成本
            put(38, ""); // 费用
            put(39, ""); // 单耗
            // 用电统计
            put(40, "ydtjdbds2bbc"); // 电表读数 => (new)
            put(41, "hdldb1f"); // 当日用电量 =>
            put(42, ""); // 估算电费 =>
            put(43, ""); // 电单耗 =>
            // 药剂、电费成本
            put(44, ""); // AS: 药剂、电费成本 =>
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
