package com.sonin.common.module.common.entity;

/**
 * @author sonin
 * @date 2021/10/17 12:59
 */
public interface CommonConstant {

    Integer STATUS_NORMAL = 0;
    Integer STATUS_DISABLE = -1;
    Integer DEL_FLAG_1 = 1;
    Integer DEL_FLAG_0 = 0;
    int LOG_TYPE_1 = 1;
    int LOG_TYPE_2 = 2;
    int OPERATE_TYPE_1 = 1;
    int OPERATE_TYPE_2 = 2;
    int OPERATE_TYPE_3 = 3;
    int OPERATE_TYPE_4 = 4;
    int OPERATE_TYPE_5 = 5;
    int OPERATE_TYPE_6 = 6;
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    Integer SC_OK_200 = 200;
    Integer SC_JEECG_NO_AUTHZ = 510;
    String LOGIN_USER_CACHERULES_ROLE = "loginUser_cacheRules::Roles_";
    String LOGIN_USER_CACHERULES_PERMISSION = "loginUser_cacheRules::Permissions_";
    int TOKEN_EXPIRE_TIME = 3600;
    String PREFIX_USER_TOKEN = "PREFIX_USER_TOKEN_";
    Integer MENU_TYPE_0 = 0;
    Integer MENU_TYPE_1 = 1;
    Integer MENU_TYPE_2 = 2;
    String MSG_TYPE_UESR = "USER";
    String MSG_TYPE_ALL = "ALL";
    String NO_SEND = "0";
    String HAS_SEND = "1";
    String HAS_CANCLE = "2";
    String HAS_READ_FLAG = "1";
    String NO_READ_FLAG = "0";
    String PRIORITY_L = "L";
    String PRIORITY_M = "M";
    String PRIORITY_H = "H";
    String SMS_TPL_TYPE_0 = "0";
    String SMS_TPL_TYPE_1 = "1";
    String SMS_TPL_TYPE_2 = "2";
    String STATUS_0 = "0";
    String STATUS_1 = "1";
    String ACT_SYNC_0 = "0";
    String ACT_SYNC_1 = "1";
    String MSG_CATEGORY_1 = "1";
    String MSG_CATEGORY_2 = "2";
    Integer RULE_FLAG_0 = 0;
    Integer RULE_FLAG_1 = 1;
    Integer USER_UNFREEZE = 1;
    Integer USER_FREEZE = 2;
    String DICT_TEXT_SUFFIX = "_dictText";
    String INPUT_TYPE = "1";
    String NUMBER_TYPE = "2";
    String COMMON_DICT_TYPE = "3";
    String BUSINESS_DICT_TYPE = "7";
    String DATE_TYPE = "4";
    String REQUIRED = "1";
    String REQUIRED_PROMPBOX_TITLE = "";
    String REQUIRED_PROMPBOX_CONTEXT = "必填项";
    String REQUIRED_ERRORBOX_TITLE = "";
    String REQUIRED_ERRORBOX_CONTEXT = "必填项";
    String NUMBER_PROMPBOX_TITLE = "";
    String NUMBER_PROMPBOX_CONTEXT = "数值型";
    String NUMBER_ERRORBOX_TITLE = "";
    String NUMBER_ERRORBOX_CONTEXT = "必须为数值型";
    String DATE_PROMPBOX_TITLE = "";
    String DATE_PROMPBOX_CONTEXT = "日期型【YYYY-MM-DD】";
    String DATE_ERRORBOX_TITLE = "";
    String DATE_ERRORBOX_CONTEXT = "必须正确填写日期";

}
