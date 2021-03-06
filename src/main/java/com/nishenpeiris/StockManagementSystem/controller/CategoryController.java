
package com.nishenpeiris.StockManagementSystem.controller;

import com.nishenpeiris.StockManagementSystem.Category;
import com.nishenpeiris.StockManagementSystem.CategoryNameAlreadyInUseException;
import com.nishenpeiris.StockManagementSystem.repository.CategoryRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(path = "/category")
public class CategoryController {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<?> getCategories() {
        try {
            return new ResponseEntity<>(categoryRepository.query(null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> create(@RequestBody Category category) {
        try {
            categoryRepository.add(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CategoryNameAlreadyInUseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = PUT)
    public ResponseEntity<?> update(@RequestBody Category category) {
        try {
            categoryRepository.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<?> delete(@RequestBody Category category) {
        try {
            categoryRepository.remove(category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
