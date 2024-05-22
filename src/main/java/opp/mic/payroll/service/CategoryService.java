package opp.mic.payroll.service;

import opp.mic.payroll.controller.AppImageController;
import opp.mic.payroll.exceptions.CategoryNotFoundException;
import opp.mic.payroll.interfaces.PhotoStorageService;
import opp.mic.payroll.model.Category;
import opp.mic.payroll.model.IconData;
import opp.mic.payroll.model.Product;
import opp.mic.payroll.repository.CategoryRepository;
import opp.mic.payroll.repository.IconRepository;
import opp.mic.payroll.util.PhotoStorageLocation;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private IconRepository iconRepository;
    private PhotoStorageService photoStorageService;
    private PhotoStorageLocation storageLocation;

    public CategoryService(CategoryRepository categoryRepository,IconRepository iconRepository,
                           PhotoStorageService photoStorageService, PhotoStorageLocation storageLocation ) {
        this.categoryRepository = categoryRepository;
        this.iconRepository = iconRepository;
        this.photoStorageService = photoStorageService;
        this.storageLocation = storageLocation;
    }

    public Page<Category> getAllCategories(int page) throws DataFormatException {
        PageRequest pageRequest = PageRequest.of(page, 5);
     Page<Category> categories= categoryRepository.findAll(pageRequest);
     for(Category category:categories){
         IconData iconData = iconRepository.findByFKId(category.getId()).orElse(null);
         if(iconData != null){
             Resource uri = photoStorageService.loadAsResource(iconData.getName());
             String url = MvcUriComponentsBuilder.fromMethodName(AppImageController.class, "serveFile",
                     uri.getFilename()).build().toUri().toString();
             category.setIcon(url);
         }
     }
     return categories;
    }

    public List<Category> getAllCategoriesAsList(){
       return categoryRepository.findAll();
    }
    public Category save(String name, String description) throws IOException {
        Optional<Category> savedCategory = categoryRepository.findByName(name);
        if(savedCategory.isPresent()){
            return null;
        }
        Category category= new Category(name,description);
      return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }
    public Category getById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category "+id +" deos not exist"));
    }


    @Transactional
    public Category delete(Long id){
        IconData iconData = iconRepository.findByFKId(id).orElse(null);
        if(iconData != null){
            String path = iconData.getName();
            photoStorageService.delete(path);
            iconRepository.delete(iconData);
        }
         categoryRepository.deleteById(id);
         return categoryRepository.findById(id).orElse(null);
    }


    public Category saveCategory(String name, Product product){
       Category category =categoryRepository.findByName(name)
                .orElseThrow(()-> new CategoryNotFoundException(name+" does not exist"));
            category.getProducts().add(product);
            Long qty = category.getQuantity();
            if(qty==null){
                category.setQuantity(product.getQuantity());
            }else {
                category.setQuantity(qty + product.getQuantity());
            }
        categoryRepository.save(category);
        return category;
    }

    public List<Category> getAll(Long id){
       return null;
    }
}
