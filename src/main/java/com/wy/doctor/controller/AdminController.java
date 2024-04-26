package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Admin;
import com.wy.doctor.service.AdminService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 127.0.0.1/admin/login ---- post
     * {"account":"admin", "password":"admin"}
     */
    @PostMapping("/login")
    @CrossOrigin("*")
    public Result<Admin> loginAdmin(@RequestBody Admin admin){
        System.out.println(admin);
        return adminService.getAdminByPassword(admin.getAccount(), admin.getPassword());
    }
    /**
     * 127.0.0.1/admin/get
     * */
    @GetMapping("/get")
    public Result<Admin> getAdminById(@RequestBody int id){
        System.out.println(id);
        return adminService.getAdminById(id);
    }

    /**
     * 127.0.0.1/admin/add ---- post
     * {"account":"600001", "password":"600001", "adminName":"aaa", "department":"运维部", "power":1}
     */
    @PostMapping("/add")
    public Result<Admin> addAdmin(@RequestBody Admin admin){
        System.out.println(admin);
        return adminService.insertAdmin(admin);
    }
    /**
     * 127.0.0.1/admin/update ---- put
     *  {"aid":2, "password":"666662", "adminName":"bbb", "department":"开发部", "power":2}
     * */
    @PutMapping("/update")
    public  Result<Admin> updateAdmin(@RequestBody Admin admin){
        System.out.println(admin);
        return adminService.updateAdmin(admin);
    }
    /**
     *  127.0.0.1/admin/delete/2 ---- delete
     */
    @DeleteMapping("/delete")
    public Result<Object> deleteAdminById(@RequestBody int id){
        System.out.println(id);
        return adminService.deleteAdmin(id);
    }

    /**
     * 127.0.0.1/admin/list ---- post
     * {"currentPage":1, "pageSize":5, "sort":"aid", "direction":"desc", "keyword":""}
     */
    @PostMapping("/list")
    public PageInfo<Admin> getAdminBySearch(@RequestBody Search search){
        return adminService.getAdminBySearch(search);
    }
}
