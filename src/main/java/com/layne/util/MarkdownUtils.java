package com.layne.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * markdown插件,转HTML等...
 */
public class MarkdownUtils {
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = null;
        if (markdown != null){
             document = parser.parse(markdown);
             HtmlRenderer renderer = HtmlRenderer.builder()
                    .extensions(headingAnchorExtensions)
                    .extensions(tableExtension)
                    .attributeProviderFactory(new AttributeProviderFactory() {
                        public AttributeProvider create(AttributeProviderContext context) {
                            return new CustomAttributeProvider();
                        }
                    })
                    .build();
            return renderer.render(document);
        }
        return "";

    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }


    public static void main(String[] args) {
        String table = "开始\n" +
                "\n" +
                "- 文件输入与输出：参照物为内存，硬盘的读和写。\n" +
                "  　　　　硬盘->内存（输入Input，读Read）\n" +
                "  　　　　内存->硬盘（输出Output，写Write）\n" +
                "- 按读取数据方式分类：\n" +
                "  　　　　字节流（Stream结尾）：按字节方式读取，每次读取一个字节，任何类型文件都可以读取：文本、图片..\n" +
                "  　　　　字符流（Reader/Writer结尾）：按字符方式读取，每次读取一个字符，只能读取纯文本文件TXT。\n" +
                "- 所有的流都实现了Closeable接口（close（）），都是可关闭的。所有的输出流都是可刷新的（实现了Flushable接口（flush（）：强行输出完成后，清空管道，防止丢数据））。\n" +
                "\n" +
                "结束";

        String a = "开始\n" +
                "\n" +
                "- 文件输入与输出：参照物为内存，硬盘的读和写。\n" +
                "\n" +
                "  \u200B\t\t\t\t硬盘->内存（输入Input，读Read）\n" +
                "\n" +
                "  \u200B\t\t\t\t内存->硬盘（输出Output，写Write）\n" +
                "\n" +
                "- 按读取数据方式分类：\n" +
                "\n" +
                "  \u200B\t\t\t\t字节流（Stream结尾）：按字节方式读取，每次读取一个字节，任何类型文件都可以读取：文本、图片..\n" +
                "\n" +
                "  \u200B\t\t\t\t字符流（Reader/Writer结尾）：按字符方式读取，每次读取一个字符，只能读取纯文本文件TXT。\n" +
                "\n" +
                "- 所有的流都实现了Closeable接口（close（）），都是可关闭的。所有的输出流都是可刷新的（实现了Flushable接口（flush（）：强行输出完成后，清空管道，防止丢数据））。\n" +
                "\n" +
                "结束";
        System.out.println(markdownToHtmlExtensions(table));
        System.out.println(markdownToHtmlExtensions(a));
    }
}
