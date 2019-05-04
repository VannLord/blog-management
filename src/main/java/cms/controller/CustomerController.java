package cms.controller;

import cms.model.Customer;
import cms.model.Province;
import cms.service.CustomerService;
import cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    ProvinceService provinceService;
    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }
    @GetMapping("/list")
    public String list(@RequestParam("s") Optional<String> s, Model model, Pageable pageable){
        Page<Customer> customers;
        if(s.isPresent()){
            customers = customerService.findAllByFirstNameContaining(s.get(),pageable);
        }else{
            customers = customerService.findAll(pageable);
        }
        model.addAttribute("customers", customerService.findAll(pageable));
        return "/customer/list";
    }
    @GetMapping("/create")
    public String createForm(@ModelAttribute("customer")Customer customer, Model model){
//        model.addAttribute("customer",customer);
        return "/customer/create";
    }
    @PostMapping("/create")
    public String creat(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        return "redirect:/list";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/customer/edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute Customer customer){
        customerService.save(customer);
        return "redirect:/list";
    }
    @GetMapping("/{id}/delete")
    public String deleteForm(@PathVariable Long id,Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/customer/delete";
    }
    @PostMapping("/delete")
    public String delete(@ModelAttribute Customer customer){
        customerService.remove(customer.getId());
        return "redirect:/list";
    }
    @GetMapping("/{id}/view")
    public String view (@PathVariable Long id, Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/customer/view";
    }

}
