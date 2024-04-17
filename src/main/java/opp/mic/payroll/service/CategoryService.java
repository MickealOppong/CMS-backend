package opp.mic.payroll.service;

import opp.mic.payroll.exceptions.CategoryNotFoundException;
import opp.mic.payroll.model.Category;
import opp.mic.payroll.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getAllCategories(int page){
        PageRequest pageRequest = PageRequest.of(page, 5);
        return categoryRepository.findAll(pageRequest);
    }
    public Category save(Category category){
       Optional<Category> savedCategory = categoryRepository.findByName(category.getName());
        if(savedCategory.isPresent()){
            return null;
        }
       return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }
    public Category getById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category "+id +" deos not exist"));
    }

    public Category delete(Long id){
         categoryRepository.deleteById(id);
         return categoryRepository.findById(id).orElse(null);
    }
}
