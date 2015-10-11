package com.jsouptest.pingyin;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * Created by Administrator on 2015/10/10.
 */
public class JPinyin {
    public static void main(String[] args) {
        String str = "ada";
        System.out.println(PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE));; // ni,hao,shi,jie

    }
}
