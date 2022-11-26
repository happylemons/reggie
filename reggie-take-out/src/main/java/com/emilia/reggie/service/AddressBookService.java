package com.emilia.reggie.service;

import com.emilia.reggie.common.R;
import com.emilia.reggie.model.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void add(AddressBook addressBook);

    R<List<AddressBook>> list(Long userId);

    void updateDefault(AddressBook addressBook);

    AddressBook findDefaultAddress(Long userId);

    AddressBook getById(Long id);
}
