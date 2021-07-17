package com.layne.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 分页工具类
 */
public class PageUtils {

    /**
     * 传入分页对象IPage , 返回最大页码
     * @param page
     * @return
     */
    public static Integer getMaxPage(IPage page){
        return  (int)(page.getTotal()%page.getSize() == 0 ? page.getTotal()/page.getSize() : page.getTotal()/page.getSize() + 1);
    }


}
