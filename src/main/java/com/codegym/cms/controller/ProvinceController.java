package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/provinces")
    public String listHome(Model model) {
        Iterable<Province> provinces = provinceService.findAll();
        model.addAttribute("provinces",provinces);
        return "/province/list";
    }
    @GetMapping("/province/create")
    public String createHome(Model model) {
        model.addAttribute("province", new Province());
        return "/province/create";
    }

    @PostMapping("/province/create/done")
    public String createDone(@ModelAttribute("province") Province province, Model model) {
        provinceService.save(province);
        model.addAttribute("message","Create successful!");
        return "/province/create";
    }
    @GetMapping("/province/{id}/edit")
    public String editHome(@PathVariable Long id , Model model) {
        Province province = provinceService.findById(id);
        model.addAttribute("province" , province);
        return "/province/edit";
    }
    @PostMapping("/province/edit/done")
    public String editDone(@ModelAttribute("province") Province province, RedirectAttributes redirect) {
        provinceService.save(province);
        redirect.addFlashAttribute("message","Edit successful!");
        return "redirect:/provinces";
    }
    @GetMapping("/province/{id}/delete")
    public String deleteDone(@PathVariable Long id , RedirectAttributes redirect) {
        Province province = provinceService.findById(id);
        Iterable<Customer> customers= customerService.findAllByProvince(province);
        for (Customer customer : customers){
            customerService.remove(customer.getId());
        }
        provinceService.remove(id);
        redirect.addFlashAttribute("message" , "Delete successful!");
        return "redirect:/provinces";
    }
}