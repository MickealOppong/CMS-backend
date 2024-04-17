package opp.mic.payroll.controller;

import opp.mic.payroll.model.Attributes;
import opp.mic.payroll.model.Category;
import opp.mic.payroll.model.ProductAttributeRequest;
import opp.mic.payroll.model.ProductSKU;
import opp.mic.payroll.service.AttributesService;
import opp.mic.payroll.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductDetailsController {

    private AttributesService attributesService;
    private CategoryService categoryService;

    public ProductDetailsController(AttributesService attributesService,
                                    CategoryService categoryService) {
        this.attributesService = attributesService;
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<Category>> allCategories(int page){

        return ResponseEntity.ok().body(categoryService.getAllCategories(page));
    }

    @GetMapping("/attributes")
    public ResponseEntity<Page<Attributes>>  allAttributes(int page){
        return ResponseEntity.ok().body(attributesService.getAllAttributes(page));
    }

    @PostMapping("/categories")
    public ResponseEntity<String> add(@RequestBody Category productCategory) throws IOException {
        System.out.println(productCategory);
      Category category = categoryService.save(productCategory);
      if(category==null){
          return ResponseEntity.badRequest().body("Category already exist");
      }
        return ResponseEntity.ok().body("Category created");
    }


    @PatchMapping("/categories/{id}")
    public ResponseEntity<String> update(@RequestParam Long id,@RequestBody Category productCategory) throws IOException {
        Category category = categoryService.getById(id);
        if(productCategory.getName() !=null){
            category.setName(productCategory.getName());
        }
        if(productCategory.getDescription() !=null){
            category.setDescription(productCategory.getDescription());
        }
        if(productCategory.getQuantity() !=null){
            category.setQuantity(productCategory.getQuantity());
        }
        if(productCategory.getSale() !=null){
            category.setSale(productCategory.getSale());
        }
        Category updatedCategory = categoryService.updateCategory(category);
        if(updatedCategory ==null){
            return ResponseEntity.badRequest().body("Update failed");
        }
        return ResponseEntity.ok().body("Category updated");
    }

    @PostMapping("/attributes")
    public ResponseEntity<String> add(@RequestBody ProductAttributeRequest productAttributeRequest){
        Attributes attribute = attributesService.save(productAttributeRequest);
        if(attribute ==null){
            return ResponseEntity.badRequest().body("Attribute already exist");
        }
        return ResponseEntity.ok().body("Attribute created");
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> productCategory(@RequestParam Long id){
        return ResponseEntity.ok().body(categoryService.getById(id));
    }


    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@RequestParam Long id) {
      Category category =  categoryService.delete(id);
      if (category ==null){
          return ResponseEntity.ok().body("Category deleted");
      }
        return ResponseEntity.ok().body("Could not perform operation");
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<Attributes> getAttribute(@RequestParam Long id){
        return ResponseEntity.ok().body(attributesService.getById(id));
    }


    @DeleteMapping("/attribute/{id}")
    public ResponseEntity<String> deleteAttribute(@PathVariable Long id) {
       boolean category =  attributesService.deleteAttribute(id);
        if (category){
            return ResponseEntity.ok().body("Attribute deleted");
        }
        return ResponseEntity.ok().body("Attribute has children and must be deleted first");
    }


    @DeleteMapping("/sku/{id}")
    public ResponseEntity<String> deleteSku(@PathVariable Long id) {
        ProductSKU category =  attributesService.deleteSKU(id);
        if (category ==null){
            return ResponseEntity.ok().body("SKU deleted");
        }
        return ResponseEntity.ok().body("Could not perform operation");
    }

}
