package com.emilia.reggie.dao;

import com.emilia.reggie.model.entity.AddressBook;

import java.util.List;

public interface AddressBookDao {


    void add(AddressBook addressBook);

    List<AddressBook> findAll(Long userId);

    void updateZero(AddressBook address);

    void updateDefault(Long id);

    AddressBook findDefaultAddress(Long userId);

    AddressBook findById(Long id);

}
