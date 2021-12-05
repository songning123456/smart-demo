package com.sonin.common.tool.query;

/**
 * @author sonin
 * @date 2021/12/5 8:16
 */
public class WrapperFactory {

    public static JoinWrapper joinWrapper() {
        return new JoinWrapper();
    }

    public static WhereWrapper whereWrapper() {
        return new WhereWrapper();
    }

    public static Result result() {
        return new Result();
    }

}
