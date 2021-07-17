package com.layne.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.layne.pojo.Tag;
import com.layne.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 标签控制器
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 标签列表
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model){
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.orderByDesc("id");
        IPage<Tag> page = new Page<>(pageNum,4);
        page = tagService.listTag(page,tagWrapper);
        model.addAttribute("page",page);
        int maxPage = (int)(page.getTotal()%page.getSize() == 0 ? page.getTotal()/page.getSize() : page.getTotal()/page.getSize() + 1);
        model.addAttribute("maxPage",maxPage);

        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        if (tagService.getCountByName(tag.getName()) != 0){
            result.rejectValue("name","nameError","标签已存在");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        Integer i = tagService.saveTag(tag);
        if (i == null){
            redirectAttributes.addFlashAttribute("message","操作失败,请重新添加标签!");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功,标签已成功添加!");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改标签
     * @param tag
     * @param result
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost( @Valid Tag tag , BindingResult result,@PathVariable Long id,RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        if (tagService.getCountByName(tag.getName()) != 0) {
            redirectAttributes.addFlashAttribute("message", "您未进行修改或该标签已存在列表中");
            return "redirect:/admin/tags";
        }
        Integer i = tagService.updateTag(id,tag);
        if (i == null){
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else{
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable  Long id, RedirectAttributes redirectAttributes){

        Integer i = tagService.deleteTag(id);
        if (i != null){
           redirectAttributes.addFlashAttribute("message" ,"删除成功") ;
        }
        return "redirect:/admin/tags";
    }


}
