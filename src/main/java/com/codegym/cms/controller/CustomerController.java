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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }
    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);

        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView listCustomers(){
        Iterable<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/customer/{id}/view")
    public  String viewCustomer(@PathVariable long id , Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer" , customer);
        return "/customer/view";
    }
    @PostMapping("/customer/view/done")
    public String viewDone(RedirectAttributes redirect) {
        redirect.addFlashAttribute("message" , "Welcome Back!");
        return "redirect:/";
    }
    @GetMapping("/customer/{id}/delete")
    public String deleteCustomer(@PathVariable long id,RedirectAttributes redirect) {
        customerService.remove(id);
        redirect.addFlashAttribute("message" , "Delete Successful");
        return "redirect:/";
    }
    @GetMapping("/customer/{id}/edit")
    public ModelAndView editCustomer(@PathVariable long id ) {
        ModelAndView model = new  ModelAndView("/customer/edit");
        Customer customer = customerService.findById(id);
        model.addObject("customer" , customer);
        return model;
    }
    @PostMapping("/customer/edit/done")
    public String editDone(@ModelAttribute("customer") Customer customer , RedirectAttributes redirect) {

        customerService.save(customer);
        redirect.addFlashAttribute("message", "Edit successful!");
        return "redirect:/";
    }
    @GetMapping("/customer/search")
    public String searchHome(Model model) {
        model.addAttribute("province" , new Province());
        return "/customer/search";


    }
    @PostMapping("/customer/search/done")
    public String searchDone(@ModelAttribute("province") Province province , Model model) {
        customerService.findAllByProvince(province);
        return "/";
    }


}