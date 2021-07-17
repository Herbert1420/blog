package com.layne.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.layne.pojo.Type;
import com.layne.service.TypeService;
import com.layne.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 分类控制器
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 分类列表
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model){
        QueryWrapper<Type> typeWrapper = new QueryWrapper<>();
        typeWrapper.orderByDesc("id");
        IPage<Type> page = new Page<>(pageNum,4);
        page = typeService.listType(page,typeWrapper);
        model.addAttribute("page",page);
        int maxPage = PageUtils.getMaxPage(page);
        model.addAttribute("maxPage",maxPage);

        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());//初始化
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 新增分类
     * @param type
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        if (typeService.getCountByName(type.getName()) != 0){
            result.rejectValue("name","nameError","分类已存在");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }

        Integer i = typeService.saveType(type);
        if (i == null){
            redirectAttributes.addFlashAttribute("message","操作失败,请重新添加分类!");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功,分类已成功添加!");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改分类
     * @param type
     * @param result
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editPost( @Valid Type type , BindingResult result,@PathVariable Long id,RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "admin/types-input";
        }

        if (typeService.getCountByName(type.getName()) != 0) {
            redirectAttributes.addFlashAttribute("message", "您未进行修改或该分类已存在列表中");
            return "redirect:/admin/types";
        }
        Integer i = typeService.updateType(id,type);
        if (i == null){
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else{
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable  Long id, RedirectAttributes redirectAttributes){

        Integer i = typeService.deleteType(id);
        if (i != null){
           redirectAttributes.addFlashAttribute("message" ,"删除成功") ;
        }
        return "redirect:/admin/types";
    }


}
