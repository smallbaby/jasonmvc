package com.jason.render;

import java.io.Writer;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public interface Render {
    /**
     * 渲染到视图
     * @param view
     * @param writer
     */
    public void render(String view, Writer writer);
}
