package cms.controller;

import cms.model.Customer;
import cms.model.Province;
import cms.repository.CustomerRepository;
import cms.service.CustomerService;
import cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProvinceController {
    @Autowired
    ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/provinces")
    public String list(Model model){
        model.addAttribute("provinces",provinceService.findAll());
        return "/province/list";
    }
    @GetMapping("/province/create")
    public String createForm(@ModelAttribute("province")Province province){
        return "/province/create";
    }
    @PostMapping("/province/create")
    public String create(@ModelAttribute Province province){
        provinceService.save(province);
        return "redirect:/provinces";
    }
    @GetMapping("/province/{id}/edit")
    public String editForm(@PathVariable Long id,Model model){
        model.addAttribute("province",provinceService.findById(id));
        return "/province/edit";
    }
    @PostMapping("/province/edit")
    public String edit(@ModelAttribute Province province){
        provinceService.save(province);
        return "redirect:/provinces";
    }
    @GetMapping("/province/{id}/delete")
    public String deleteForm(@PathVariable Long id, Model model){
        model.addAttribute("province",provinceService.findById(id));
        return "/province/delete";
    }
    @PostMapping("/province/delete")
    public String delete(@ModelAttribute Province province){
        provinceService.remove(province.getId());
        return "redirect:/provinces";
    }
//    @GetMapping("/province/{id}/view")
//    public String view(@PathVariable Long id,Model model){
//        model.addAttribute("province",provinceService.findById(id));
//        return "/province/view";
//    }
    @GetMapping("/province/{id}/view")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error.404");
        }


        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}

