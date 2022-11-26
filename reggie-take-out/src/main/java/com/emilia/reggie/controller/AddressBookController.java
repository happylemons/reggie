package com.emilia.reggie.controller;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.AddressBook;
import com.emilia.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addressBook")
public class AddressBookController {
    @Autowired(required = false)
    private AddressBookService addressBookService;

    /**
     * 添加地址
     */
    @PostMapping
    public R add(@RequestBody AddressBook addressBook, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        addressBook.setUserId(userId);
        addressBook.setCreateUser(userId);
        addressBook.setUpdateUser(userId);
        addressBookService.add(addressBook);
        return R.success("添加成功");
    }

    @GetMapping("list")
    public R<List<AddressBook>> list(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return addressBookService.list(userId);
    }

    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        }
        return R.error("没有此对象");

    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        AddressBook addressBook = addressBookService.findDefaultAddress(userId);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        }
        return R.success(addressBook);

    }


    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        addressBook.setUserId(userId);
        addressBookService.updateDefault(addressBook);
        return R.success(addressBook);
    }

//    /**
//     * 获取到用户的地址
//     */
//    @GetMapping("/list")
//    public R<List<AddressBook>> list(HttpSession session){
//        Long userId = (Long) session.getAttribute("user");
//List<AddressBook> addressBooks = addressBookService.list(userId)
//    }


}
