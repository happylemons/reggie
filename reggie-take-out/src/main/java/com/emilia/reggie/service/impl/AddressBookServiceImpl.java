package com.emilia.reggie.service.impl;

import com.emilia.reggie.common.R;
import com.emilia.reggie.dao.AddressBookDao;
import com.emilia.reggie.model.entity.AddressBook;
import com.emilia.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired(required = false)
    private AddressBookDao addressBookDao;

    @Override
    public void add(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setIsDeleted(0);

        addressBookDao.add(addressBook);
    }

    @Override
    public R<List<AddressBook>> list(Long userId) {
        List<AddressBook> addressBooks = addressBookDao.findAll(userId);
        return R.success(addressBooks);

    }

    @Override
    public void updateDefault(AddressBook addressBook) {
        addressBook.setUpdateTime(LocalDateTime.now());

        AddressBook findAddressBook = addressBookDao.findById(addressBook.getId());
        List<AddressBook> addressBooks = addressBookDao.findAll(findAddressBook.getUserId());
        for (AddressBook address : addressBooks) {
            address.setIsDefault(0);
            addressBookDao.updateZero(address);
        }
//        addressBookDao.updateZero(addressBook.getUserId());
        addressBookDao.updateDefault(addressBook.getId());
    }

    @Override
    public AddressBook findDefaultAddress(Long userId) {
        return addressBookDao.findDefaultAddress(userId);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookDao.findById(id);

    }
}
